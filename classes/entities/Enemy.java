package classes.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public abstract class Enemy extends MapEntity {
    // to implement shi
    private int speed;
    private final String enemy_type; // For melee, ranged, boss types -Ervin

    //maybe temporary timer var kay idk a better way HAHAHAHAH - Set H
    private int movement_internal_timer = 0;
    private double angle;
    private boolean is_going_to_move;

    public int getMovement_internal_timer(){
        return movement_internal_timer;
    }

    public void decrementMovementInternalTimer(int value){
        movement_internal_timer -= value;
        if(movement_internal_timer <= 0) movement_internal_timer = 0;
        if(movement_internal_timer == 0) is_going_to_move = false;
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
    public Enemy(String name, int hit_points, int x, int y, int side, String enemy_type, int key) {
        super(name, hit_points, x, y, side, key);
        this.enemy_type = enemy_type;
        this.speed = 2;
    }

    public Enemy(String name, int x, int y, int side, String enemy_type, int key) {
        super(name, x, y, side, key);
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
            moveTowardsEntity(calculateAngle(e));
        } else if(!isWithinRange(300,-1,e) && movement_internal_timer == 0){
            roamRandom();
        } else {
            move();
        }
    }
    public void moveTowardsEntity(double angle_radians){

        deltaY = (int) Math.floor(Math.sin(angle_radians) * speed);
        deltaX = (int) Math.floor(Math.cos(angle_radians) * speed);
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
        is_going_to_move = new Random().nextBoolean();
        if(is_going_to_move){
            System.out.println("I am about to move");
            this.angle = rand_angle;

        }
        movement_internal_timer = new Random().nextInt(1,5);
    }
    public void move(){
        if(is_going_to_move){
            deltaY = (int) Math.floor(Math.sin(angle) * speed);
            deltaX = (int) Math.floor(Math.cos(angle) * speed);
        }
    }

    //for enemies
    public int attack(){
        return 10;
    }

    @Override
    public void display(Graphics g, CameraEntity cam) {
        if (buffer != null) {
            g.setFont(new Font("Consolas", Font.PLAIN, 30));
            g.setColor(Color.BLACK);
            g.drawString(String.format("%d", getHit_points()), x - cam.x, (y - cam.y) - 30);
            g.drawImage(buffer, x - cam.x, y - cam.y, width, height, null);
        } else {
            g.drawRect(x - cam.x, y - cam.y, width, height);
            System.err.println("[Warning: PanelEntity] Displaying without image/sprite attached");
        }
    }

    // temporary enemies just reuse code - SET H
    public static class Brit extends Enemy {
        public Brit(int x, int y, int side, int key) {
            super("Brit",100,  x, y, side, "Brit Temporary Enemy", key);
            try {
                this.buffer = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/enemy_sprites/bo_o_ov_wa_er.png"));
            } catch (IOException e) {
                System.out.println("Suck deez british nuts");
            }

        }
    }

    public static class Soviet extends Enemy {
        public Soviet(int x, int y, int side, int key) {
            super("Soviet", 100, x, y, side, "Soviet Temporary Enemy", key);
            try {
                this.buffer = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/enemy_sprites/our_enemy.png"));
            } catch (IOException e) {
                System.out.println("Suck deez soviet nuts");
            }
        }
    }

    // Just change the names no idea what to name it lmao -Ervin
    public static class MeleeMinion extends Enemy {
        public MeleeMinion(String name,
                String enemy_type,
                int hit_points,
                int attack_stat,
                float haste,
                int defense_stat,
                int id) {
            super("Minion Warrior", "melee", 10, 5, 3.0f, 10, 500);
        }
    }

    public static class RangedMinion extends Enemy {
        public RangedMinion(String name,
                String enemy_type,
                int hit_points,
                int attack_stat,
                float haste,
                int defense_stat,
                int id) {
            super("Minion Archer", "ranged", 5, 10, 5.0f, 5, 501);
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
