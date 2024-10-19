package classes.entities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Enemy extends MapEntity {
    // to implement shi
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
    public Enemy(String name, int x, int y, int side, String enemy_type){
        super(name,x,y,side);
        this.enemy_type = enemy_type;
    }


    public String getEnemyType() {
        return enemy_type; // this might be useful like boss fights like naay minions tapos iboost specific
                           // minions -Ervin
    }
    //temporary enemies just reuse code - SET H
    public static class Brit extends Enemy{
        public Brit(int x, int y, int side){
            super("Brit", x, y, side,"Temporary Enemy");
            try{
                this.buffer = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/bo_o_ov_wa_er.png"));
            }catch(IOException e){
                System.out.println("Suck deez british nuts");
            }

        }
    }
    public static class Soviet extends Enemy{
        public Soviet(int x, int y, int side){
            super("Soviet",x,y,side,"Temporary Enemy");
            try{
                this.buffer = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/our_enemy.png"));
            }catch(IOException e){
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
