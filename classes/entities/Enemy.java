package classes.entities;

import classes.Asset.Audio.Audio;
import classes.sprites.EnemySprite;
import classes.sprites.EntitySprite;
import interfaces.CollisionHandler;
import interfaces.EntityCollidable;
import interfaces.RangedAttacker;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public abstract class Enemy extends MapEntity implements EntityCollidable, CollisionHandler{
    private final String enemy_type;
    private int movement_internal_cooldown = 0;
    protected int attack_cooldown = 0;
    protected final int attack_cooldown_max = 180;
    public boolean is_attacking = false;
    boolean is_going_to_move = false;

    public Enemy(String name, int speed, int attack_stat, int hit_points, int x, int y, int width, int height, String enemy_type, long key) {
        super();
        super.setName(name);
        super.setHit_points(hit_points);
        super.setMax_hit_points(hit_points);
        super.setX(x);
        super.setY(y);
        super.setDimensions(width,height);
        super.setKey(key);
        super.setAttack_stat(attack_stat);

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
        deltaY = (int) Math.round(Math.sin(angle) * Math.ceil((double)speed/2));
        deltaX = (int) Math.round(Math.cos(angle) * Math.ceil((double)speed/2));
    }

    @Override
    public void onEntityCollision(PanelEntity e){
        EntityCollidable.super.onEntityCollision(e);
        if(e instanceof ProjectileEntity && ((ProjectileEntity) e).is_player_friendly){
            damage(((ProjectileEntity) e).dealDamage());
        }
        if(e instanceof MeleeAttackEntity){
            damage(((MeleeAttackEntity) e).dealDamage());
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
            super("Slime",1, 5 , 50, x, y, side,side, "Melee",key);
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
        private Audio attack_audio = new Audio().load("effects/Virus Spit.wav");

        public Virus(int x, int y, int side, long key, List<ProjectileEntity> projectiles){
            super("Virus", 2, 2, 100, x, y, side,side, "Ranged", key);
            this.projectiles = projectiles;
            buffer = EnemyHandler.VIRUS();
        }

        @Override
        public void shootProjectile(PanelEntity target){

            if(attack_cooldown == attack_cooldown_max - 50){

                ProjectileEntity projectile = new ProjectileEntity.VirusSpit(this.x,this.y,target);
                projectile.setDamage(projectile.getDamage() + super.getAttack_stat());

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
            attack_audio.play();
            this.attack_cooldown = attack_cooldown_max;
        }

        @Override
        public void executeEnemyBehavior(PanelEntity e){

            if(isWithinRange(200,-1,e) || is_attacking){
                shootProjectile(e);
                deltaX = 0;
                deltaY = 0;
            } else if(isWithinRange(400,200,e)){
                moveAtAngle(calculateAngle(e));
            } else if(isWithinRange(1000,400,e) && super.movement_internal_cooldown == 0){
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

    public static class Peashooter extends Enemy implements RangedAttacker{
        List<ProjectileEntity> projectiles;
        double angle;
        PeashooterDirection direction;
        public Peashooter(int x, int y, int side, long key, List<ProjectileEntity> projectiles, PeashooterDirection direction){
            super("Peashooter", 0,2, 20, x, y, side,side, "Ranged", key);
            this.direction = direction;
            this.projectiles = projectiles;
            switch(direction){
                case DOWN:
                    angle = calculateAngle(x,y,x,y+5);
                    break;
                case LEFT:
                    angle = calculateAngle(x,y,x-5,y);
                    break;
                case RIGHT:
                    angle = calculateAngle(x,y,x+5,y);
            }
            buffer = EnemyHandler.PEASHOOTER(direction);

        }


        public enum PeashooterDirection{
            DOWN, LEFT, RIGHT
        }

        @Override
        public void shootProjectile(PanelEntity target) {
            if(attack_cooldown == attack_cooldown_max - 50){
                int x = this.x;
                int y = this.y;
                x += 16;
                y += 8;
                ProjectileEntity projectile = new ProjectileEntity.Pea(x,y,angle);
                projectiles.add(projectile);
            }
            if(attack_cooldown == 0){
                initiateAttackTimer();
            }
        }

        @Override
        public void executeEnemyBehavior(PanelEntity e) {
            shootProjectile(e);
            checkEntitySprites();
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
        public void checkEntitySprites(){
            EnemySprite sprite = (EnemySprite) buffer;
            super.checkEntitySprites();
            sprite.setAttacking(is_attacking);
        }
    }

    public static class Squash extends Enemy {
        boolean isDropping = false;
        boolean isJumping = false; // To track if jumping is in progress

        int target_x = -1;
        int target_y = -1;

        public Squash(int x, int y, int side, long key){
            super("Squash", 0,2, 30, x, y, side,side, "Melee", key);
            buffer = EnemyHandler.SQUASH();
        }

        void jump(PanelEntity e){
            if (!isJumping) return;
            moveToXandY(target_x, target_y, 8);

            if (this.x == target_x && this.y == target_y) {


                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.schedule(() -> drop(e), 800, java.util.concurrent.TimeUnit.MILLISECONDS);
            }
        }

        void drop(PanelEntity e){
            isDropping = true;
            moveToXandY(target_x, target_y + 96, 10);

            if(x == target_x && y == target_y + 96){

                this.setHit_points(0);
            }
        }

        @Override
        public void executeEnemyBehavior(PanelEntity e) {
            if ((isWithinRange(120, -1, e) && !isDropping && !isJumping)){
                is_attacking = true;
                if (e.x > this.x) {
                    ((EntitySprite)buffer).toRight();
                } else {
                    ((EntitySprite)buffer).toLeft();
                }

                EnemySprite sprite = (EnemySprite) buffer;
                super.checkEntitySprites();
                sprite.setAttacking(is_attacking);
                Timer jump_wind_timer = new Timer();
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(target_x == -1)
                            target_x = e.x;
                        if(target_y == -1)
                            target_y = e.y - 120;
                    }
                },200);
                jump_wind_timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isJumping = true;
                        jump(e);
                    }
                }, 500);
            } else if (!isDropping && isJumping) {
                jump(e);
            } else if (isDropping) {
                drop(e);
            }
        }
    }

}
