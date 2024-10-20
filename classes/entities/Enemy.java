package classes.entities;

import java.io.IOException;
import javax.imageio.ImageIO;

public class Enemy extends MapEntity {
    // to implement shi
    private int speed;
    private String enemy_type; // For melee, ranged, boss types -Ervin

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

    public Enemy(String name, int x, int y, int side, String enemy_type) {
        super(name, x, y, side);
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
     * Calculates the distance of this object
     * and a given PanelEntity in pixel units - Set H
     *
     */
    public double calculateDistance(PanelEntity e){
        return Math.sqrt(Math.pow((this.x - e.x),2) + Math.pow((this.y - e.y),2));
    }

    /*
     * Calculates the angle relative to the x-axis a straight line from
     * this entity to given entity e would form.
     * The value might look wrong at first (i.e why it is negative or positive)
     * mostly because our 0,0 is at the top left - Set H
     *
     */
    public double calculateAngle(PanelEntity e) {
        double x_diff = e.x - x;
        double y_diff = e.y - y;
        double angle_radians = (Math.atan2(y_diff, x_diff));

        return angle_radians;
    }

    /*
     * Will make this entity move towards a given PanelEntity.
     * The entity will stop moving if the player is too close or far
     * As of now the movement looks weird because we are dealing with
     * movements of value of integers, not double, so it is less
     * precise - Set H
     */

    public void moveTowardsEntity(PanelEntity e) {
        double angle_radians = calculateAngle(e);

        if(this.calculateDistance(e) < 300 && this.calculateDistance(e) > 70){
            deltaY = (int) Math.floor(Math.sin(angle_radians) * speed);
            deltaX = (int) Math.floor(Math.cos(angle_radians) * speed);
        }
    }

    // temporary enemies just reuse code - SET H
    public static class Brit extends Enemy {
        public Brit(int x, int y, int side) {
            super("Brit", x, y, side, "Brit Temporary Enemy");
            try {
                this.buffer = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/bo_o_ov_wa_er.png"));
            } catch (IOException e) {
                System.out.println("Suck deez british nuts");
            }

        }
    }

    public static class Soviet extends Enemy {
        public Soviet(int x, int y, int side) {
            super("Soviet", x, y, side, "Soviet Temporary Enemy");
            try {
                this.buffer = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/our_enemy.png"));
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
