package classes.entities;

import interfaces.EntityCollidable;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

public abstract class Enemy extends MapEntity implements EntityCollidable{
    // to implement shi
    private final String enemy_type; // For melee, ranged, boss types -Ervin

    //maybe temporary timer var kay idk a better way HAHAHAHAH - Set H
    private int movement_internal_cooldown = 0;
    private Timer movement_internal_timer;
    boolean is_going_to_move = false;
    public boolean isGoingToMove(){
        return is_going_to_move;
    }
    public int getMovement_internal_cooldown(){
        return movement_internal_cooldown;
    }
    public void decrementMovementInternalTimer(int value){
        movement_internal_cooldown -= value;
    }
    public void initiateMovementInternalTimer(){
        this.movement_internal_cooldown = new Random().nextInt(50,100);
        System.out.println(movement_internal_cooldown);
        this.movement_internal_timer = new Timer(10, e -> {
            //System.out.println(movement_internal_cooldown);
            decrementMovementInternalTimer(1);
            if(movement_internal_cooldown == 0) movement_internal_timer.stop();
        });
        movement_internal_timer.start();
    }

    protected int attack_cooldown = 0;
    private Timer attack_timer;
    public void decrementAttackCooldown(int value){
        this.attack_cooldown -= value;
    }
    public void initiateAttackTimer(){
        this.attack_cooldown = 100;
        this.attack_timer = new Timer(10, e -> {
            decrementAttackCooldown(1);
            if(attack_cooldown == 0) attack_timer.stop();
        });
        attack_timer.start();
    }



    public Enemy(String name,
            String enemy_type,
            int hit_points,
            int attack_stat,
            float haste,
            int defense_stat,
            int id) {
        super(name, hit_points, attack_stat, haste, defense_stat, id); // Murag di man needed ang haste stat sa enemy
                                                                       // guro? -Ervin
        this.enemy_type = enemy_type;
    }

    //hit point test - dym
    public Enemy(String name, int hit_points, int x, int y, int side, String enemy_type, int key, String spritePath) {
        super(name, hit_points, x, y, side, key, spritePath);
        this.enemy_type = enemy_type;
        this.speed = 2;
    }

    public Enemy(String name, int x, int y, int side, String enemy_type, int key, String spritePath) {
        super(name, x, y, side, key, spritePath);
        this.enemy_type = enemy_type;
        this.speed = 2;
    }

    public String getEnemyType() {
        return enemy_type; // this might be useful like boss fights like naay minions tapos iboost specific
                           // minions -Ervin
    }

    public int getSpeed() {
        return this.speed;
    }

    // Speedster enemy soon?!?!?! -Ervin
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
    public void roamRandom(){

        double rand_angle = new Random().nextDouble(0.0, 3.14159 * 2) - 3.14159;
        System.out.println(rand_angle);
        int value = new Random().nextInt(1,4);
        is_going_to_move = value == 3;
        if(is_going_to_move){
            System.out.println("I am about to move");
            move(rand_angle);
        } else {
            deltaY = 0;
            deltaX = 0;
        }
        System.out.println("Starting timer");
        this.initiateMovementInternalTimer();
    }
    public void move(double angle){
        deltaY = (int) Math.round(Math.sin(angle) * speed/2);
        deltaX = (int) Math.round(Math.cos(angle) * speed/2);
    }

    //for enemies
    public int attack(){
        return 10;
    }

    @Override
    public void onEntityCollision(PanelEntity e){
        EntityCollidable.super.onEntityCollision(e);
        if(e instanceof ProjectileEntity && ((ProjectileEntity) e).is_player_friendly){
            setHit_points(((ProjectileEntity) e).dealDamage());
        }
    }

    @Override
    public void display(Graphics g, CameraEntity cam) {
        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        g.setColor(Color.BLACK);
        g.drawString(String.format("%d", getHit_points()), x - cam.x, (y - cam.y) - 30);
        super.display(g, cam);
    }

    // temporary enemies just reuse code - SET H
    public static class Brit extends Enemy {
        public Brit(int x, int y, int side, int key) {
            super("Brit",100,  x, y, side, "Brit Temporary Enemy", key, "sprites/enemy_sprites/bo_o_ov_wa_er.png");
        }
    }

    public static class Soviet extends Enemy {
        public Soviet(int x, int y, int side, int key) {
            super("Soviet", 100, x, y, side, "Soviet Temporary Enemy", key, "sprites/enemy_sprites/our_enemy.png");
        }
    }

    // Just change the names no idea what to name it lmao -Ervin
    public class Slime extends Enemy {
        enum slimeType{
            PYRO,
            HYDRO,
            GEO

        }
        slimeType slime_type;

        public Slime(String name,
                String enemy_type,
                int hit_points,
                int attack_stat,
                float haste,
                int defense_stat,
                int id, slimeType slime_type) {
            super("Minion Warrior", "melee", 10, 5, 3.0f, 10, 500);
            this.slime_type = slime_type;
        }
    }

    public static class Virus extends Enemy {
        List<ProjectileEntity> projectiles;
        public Virus(int x, int y, int side, int key, List<ProjectileEntity> projectiles){
            super("Virus", 100, x, y, side, "Ranged", key, "sprites/enemies/virus_left_F1.png");
            this.projectiles = projectiles;

        }


        public void shoot_projectile(PanelEntity player){
            if(attack_cooldown == 0){
                System.out.println("Virus attacks");
                ProjectileEntity projectile = new ProjectileEntity.VirusSpit(this.x,this.y,player);

                projectiles.add(projectile);
                System.out.println(projectiles);
                initiateAttackTimer();
            }
        }

        @Override
        public void executeEnemyBehavior(PanelEntity e){
            if(isWithinRange(400,100,e)){
                shoot_projectile(e);
            } else if(!isWithinRange(300,-1,e) && super.movement_internal_cooldown == 0){
                roamRandom();
            }
        }
    }

    // idk if needed ni pero I just put this just in case -Ervin
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
