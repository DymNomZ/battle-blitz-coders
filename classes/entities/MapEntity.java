package classes.entities;

<<<<<<< HEAD:classes/entities/MapEntity.java
public abstract class MapEntity extends PanelEntity {
=======
public abstract class MAP_ENTITY extends PANEL_ENTITY {
>>>>>>> fac42dfe06377e896ba9cdff40761a80fd8761bf:classes/entities/MAP_ENTITY.java
    private String name;
    private int hit_points;
    private int attack_stat = 0;
    private float haste = 0;
    private int defense_stat;
    private int id;
    private static final int INFINITE = Integer.MAX_VALUE; // Infinite health and defense for NPCs

    public MapEntity(){}
    public MapEntity(String name, int hit_points, int attack_stat, float haste, int defense_stat, int id){
        this.name = name;
        this.hit_points = hit_points;
        this.attack_stat = attack_stat;
        this.haste = haste;
        this.defense_stat = defense_stat;
        this.id = id;
    }

    public MapEntity(String name, int id){
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
