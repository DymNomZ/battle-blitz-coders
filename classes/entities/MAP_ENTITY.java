package classes.entities;

public abstract class MAP_ENTITY {
    private String name;
    private int hit_points;
    private int attack_stat = 0;
    private float haste = 0;
    private int defense_stat;
    private int id;
    private static final int INFINITE = Integer.MAX_VALUE; // Infinite health and defense for NPCs

    public MAP_ENTITY(){}
    public MAP_ENTITY(String name, int hit_points, int attack_stat, float haste, int defense_stat, int id){
        this.name = name;
        this.hit_points = hit_points;
        this.attack_stat = attack_stat;
        this.haste = haste;
        this.defense_stat = defense_stat;
        this.id = id;
    }

    public MAP_ENTITY(String name, int id){
        this.name = name;
        this.hit_points = INFINITE;
        this.defense_stat = INFINITE;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getHit_points() {
        return hit_points;
    }

    public int getId() {
        return id;
    }

    public void show_stats(){
        //to check if its a player, NPCs shouldn't call these
        if(hit_points != INFINITE){
            System.out.println(
            "Showing stats of " + name + ":\n"
            + "HP    : " + hit_points + "\n"
            + "ATK   : " + attack_stat + "\n"
            + "HASTE : " + haste + "\n"
            + "DEF   : " + defense_stat
            );
        }
    }
}
