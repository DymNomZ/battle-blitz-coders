package classes.entities;

import classes.sprites.EnemySprite;
import interfaces.CollisionHandler;
import interfaces.EntityCollidable;
import interfaces.RangedAttacker;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;

public abstract class Enemy extends MapEntity implements EntityCollidable, CollisionHandler{
    private final String enemy_type;
    private int movement_internal_cooldown = 0;
    protected int attack_cooldown = 0;
    protected final int attack_cooldown_max = 180;
    public boolean is_attacking = false;
    boolean is_going_to_move = false;

    public Enemy(String name,
            String enemy_type,
            int hit_points,
            int attack_stat,
            float haste,
            int defense_stat,
            long id) {
        super();
        super.setName(name);
        super.setHit_points(hit_points);
        super.setMax_hit_points(hit_points);
        super.setAttack_stat(attack_stat);
        super.setHaste(haste);
        super.setDefense_stat(defense_stat);
        super.setId(id);

        this.enemy_type = enemy_type;
    }

    public Enemy(String name, int speed, int hit_points, int x, int y, int side, String enemy_type, long key) {
        super();
        super.setName(name);
        super.setHit_points(hit_points);
        super.setMax_hit_points(hit_points);
        super.setX(x);
        super.setY(y);
        super.setDimensions(side,side);
        super.setKey(key);

        this.enemy_type = enemy_type;
        this.speed = speed;
    }


    public boolean isGoingToMove(){
        return is_going_to_move;
    }

    public int getMovement_internal_cooldown(){
        return movement_internal_cooldown;
    }

    public void decrementCooldowns(int value){
        movement_internal_cooldown -= value;
        if(movement_internal_cooldown < 0)movement_internal_cooldown = 0;

        attack_cooldown -= value;
        if(attack_cooldown < 0){
            attack_cooldown = 0;
            is_attacking = false;
        }
    }

    public enum EnemySpecies{
        VIRUS, SLIME
    }

    public void initiateMovementInternalTimer(){
        this.movement_internal_cooldown = new Random().nextInt(60,180);
    }

    public void initiateAttackTimer(){
        is_attacking = true;
        this.attack_cooldown = 100;
    }

    @Override
    public void onCollision(){
        this.is_colliding = true;
    }

    public String getEnemyType() {
        return enemy_type;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /*
     * Will make this entity move towards a given PanelEntity.
     * The entity will stop moving if the player is too close or far
     * As of now the movement looks weird because we are dealing with
     * movements of value of integers, not double, so it is less
     * precise - Set H
     */

    public void executeEnemyBehavior(PanelEntity e) {
        if(isWithinRange(300,70,e)){
            moveAtAngle(calculateAngle(e));
        } else if(!isWithinRange(300,-1,e) && movement_internal_cooldown == 0){
            roamRandom();
        }
    }

    /*
    * This function will return false if the given PanelEntity is
    * too close or too far defined by outer_range and inner_range
    * respectively - Set H
    */
    public boolean isWithinRange(int outer_range, int inner_range, PanelEntity e){
        return this.calculateDistance(e) < outer_range && this.calculateDistance(e) > inner_range;
    }


    /*
    * This function will pick a random angle and will either move or not move,
    * having a 25% chance of moving and 75% chance not to move - Set H
    * */
    public void roamRandom(){
        double rand_angle = new Random().nextDouble(0.0, 3.14159 * 2) - 3.14159;
        int value = new Random().nextInt(1,4);
        is_going_to_move = (value == 3);
        if(is_going_to_move){
            move(rand_angle);
        } else {
            deltaY = 0;
            deltaX = 0;
        }
        this.initiateMovementInternalTimer();
    }

    public void move(double angle){
        deltaY = (int) Math.round(Math.sin(angle) * speed/2);
        deltaX = (int) Math.round(Math.cos(angle) * speed/2);
    }

    //for enemies
    public int attack(){
        return 5;
    }

    @Override
    public void onEntityCollision(PanelEntity e){
        EntityCollidable.super.onEntityCollision(e);
        if(e instanceof ProjectileEntity && ((ProjectileEntity) e).is_player_friendly){
            damage(((ProjectileEntity) e).dealDamage());
        }
    }

    @Override
    public void display(Graphics g, CameraEntity cam) {
        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        g.setColor(Color.BLACK);
//        g.drawString(String.format("%d", getHit_points()), x - cam.x, (y - cam.y) - 30);
        super.display(g, cam);
    }

    public static class Slime extends Enemy {

        public Slime(int x, int y, int side, long key){
            super("Slime",1 , 50, x, y, side, "Melee",key);
            buffer = EnemyHandler.SLIME();
        }
        
        @Override
        public void executeEnemyBehavior(PanelEntity e) {
            if(isWithinRange(70,-1,e)){
                deltaX = 0;
                deltaY = 0;
            } else if(isWithinRange(300,70,e)){
                moveAtAngle(calculateAngle(e));
            } else if(isWithinRange(1000,300,e) && super.movement_internal_cooldown == 0){
                roamRandom();
            }
            checkEntitySprites();
        }

        @Override
        public void checkEntitySprites(){
            EnemySprite sprite = (EnemySprite) buffer;
            super.checkEntitySprites();
	        sprite.setAttacking(is_attacking);
        }
    }

    public static class Virus extends Enemy implements RangedAttacker{
        List<ProjectileEntity> projectiles;
        public Virus(int x, int y, int side, long key, List<ProjectileEntity> projectiles){
            super("Virus", 2, 100, x, y, side, "Ranged", key);
            this.projectiles = projectiles;
            buffer = EnemyHandler.VIRUS();
        }

        @Override
        public void shootProjectile(PanelEntity target){

            if(attack_cooldown == attack_cooldown_max - 50){

                ProjectileEntity projectile = new ProjectileEntity.VirusSpit(this.x,this.y,target);

                projectiles.add(projectile);
            }
            if(attack_cooldown == 0){

                initiateAttackTimer();
            }

        }

        @Override
        public void initiateAttackTimer(){
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    is_attacking = false; // Stops the timer after the task is executed
                }
            }, 500);

            is_attacking = true;
            this.attack_cooldown = attack_cooldown_max;
        }

        @Override
        public void executeEnemyBehavior(PanelEntity e){

            if(isWithinRange(200,100,e)){
                shootProjectile(e);
                deltaX = 0;
                deltaY = 0;
            } else if(isWithinRange(300,200,e)){
                moveAtAngle(calculateAngle(e));
            } else if(isWithinRange(1000,300,e) && super.movement_internal_cooldown == 0){
                roamRandom();
            }
            checkEntitySprites();
        }

        @Override
        public void checkEntitySprites(){
            EnemySprite sprite = (EnemySprite) buffer;
            super.checkEntitySprites();
            if(is_attacking){
                sprite.setAttacking(true);
            } else {
                sprite.setAttacking(false);
            }
        }

    }

    public static class Boss extends Enemy {
        // sample
        public Boss(String name,
                String enemy_type,
                int hit_points,
                int attack_stat,
                float haste,
                int defense_stat,
                int id) {
            super("The Shining", "boss", 100, 50, 25.0f, 50, 502);
        }
    }

}
