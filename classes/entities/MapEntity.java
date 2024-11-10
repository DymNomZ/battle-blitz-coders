package classes.entities;

public abstract class MapEntity extends PanelEntity {
    private String name;
    private int hit_points;
    private int max_hit_points;
    private int attack_stat = 0;
    private float haste = 0;
    private int defense_stat;
    private long id;
    private static final int INFINITE = Integer.MAX_VALUE; // Infinite health and defense for NPCs

    public MapEntity(){}
    public MapEntity(String name, int hit_points, int attack_stat, float haste, int defense_stat, long id){
        super();
        this.name = name;
        this.hit_points = hit_points;
        max_hit_points = hit_points;
        this.attack_stat = attack_stat;
        this.haste = haste;
        this.defense_stat = defense_stat;
        this.id = id;
    }

    //Temporary to display enemies - Set H
    public MapEntity(String name, int x, int y, int side, long key){
        super(x,y,side,side, key);
        this.name = name;
    }

    //Temporary to display enemies with sprite - Lil Z
    public MapEntity(String name, int x, int y, int side, long key, String spritePath){
        super(x,y,side,side, key, spritePath);
        this.name = name;
    }

    //hit point test - dym
    public MapEntity(String name, int hit_points, int x, int y, int side, long key){
        super(x,y,side,side, key);
        this.name = name;
        this.hit_points = hit_points;
        max_hit_points = hit_points;
    }

    //hit point test with sprite - Lil Z
    public MapEntity(String name, int hit_points, int x, int y, int side, long key, String spritePath){
        super(x,y,side,side, key, spritePath);
        this.name = name;
        this.hit_points = hit_points;
        max_hit_points = hit_points;
    }

    //for dummy d1
    public MapEntity(int hit_points, int x, int y, int width, int height){
        super(x, y, width, height);
        this.hit_points = hit_points;
        max_hit_points = hit_points;
    }

    public MapEntity(String name, long id, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.id = id;
        this.hit_points = INFINITE;
        max_hit_points = hit_points;
        this.defense_stat = INFINITE;
    }

    public MapEntity(String name, long id, int x, int y, String spritePath){
        super(x, y, spritePath);
        this.name = name;
        this.id = id;
        this.hit_points = INFINITE;
        max_hit_points = hit_points;
        this.defense_stat = INFINITE;
    }

    public String getName() {
        return name;
    }

    public int getHit_points() {
        return hit_points;
    }

    public int getMax_hit_points(){
        return max_hit_points;
    }

    //hit point test - dym
    public void damage(int damage) {
        hit_points -= damage;
        System.out.println(hit_points);
    }

    public long getId() {
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
