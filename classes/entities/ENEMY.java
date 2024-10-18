package classes.entities;

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

    public String getEnemyType() {
        return enemy_type; // this might be useful like boss fights like naay minions tapos iboost specific
                           // minions -Ervin
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
