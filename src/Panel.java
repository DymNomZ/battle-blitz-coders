package src;
import classes.GUI.Hotbar;
import classes.entities.Dummy_sus;
import classes.entities.Enemy;
import classes.entities.ItemEntity;
import classes.entities.NPC;
import classes.sprites.ItemSprites;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Panel extends JPanel implements Runnable {

    public static int FPS = 60; // delta loop purposes
    public static int SCALE = 2, DEF_DIMENSION = 32;
    public static int TILE_SIZE = DEF_DIMENSION * SCALE;
    public static int SCREEN_WIDTH = 1280, SCREEN_HEIGHT = 720;

    public int max_map_row, max_map_col;

    public static AssetEngine asset_engine;
    MapConstructor map;
    
    KeyHandler key_input;

    Thread main_thread;

    //temporary heheh - Dymes
    JTextArea hit_points_display;

    //temporary
    Dummy_sus d1;
    NPC NPC1;

    HashMap<Integer, Enemy> spawned_enemies = new HashMap<>();
    HashMap<Integer, ItemEntity> drop_items = new HashMap<>();

    List<Integer> enemy_ids = new LinkedList<>();
    List<Integer> collected_items_keys = new ArrayList<>();

    public Panel(){

        map = new MapConstructor("assets/maps/stage_1_test.zip", SCREEN_WIDTH, SCREEN_HEIGHT);

        key_input = new KeyHandler();

        max_map_row = map.getMap_height();
        max_map_col = map.getMap_length();

        d1 = new Dummy_sus(1000, max_map_row * TILE_SIZE, max_map_col * TILE_SIZE, TILE_SIZE, key_input);

        //temp NPC
        NPC1 = new NPC.TempNPC(max_map_row * TILE_SIZE, max_map_col * TILE_SIZE, ItemSprites.ConsumableHealing.GRAB);

        hit_points_display = new JTextArea(String.format("%d", d1.getHit_points()), 1, 1);
        hit_points_display.setBounds(10, 150, 50, 30);

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(key_input);
        this.add(hit_points_display);
    }

    public void start_main_thread(){
        main_thread = new Thread(this);
        main_thread.start();
    }

    @Override
    public void run() {
        long nanoInterval = 16666667; // 60 fps
        long lastEntityCheck = 0;
        long last_system_time = System.nanoTime();
        long current_system_time;
        long delta = 0;

        while (main_thread != null) {
            last_system_time = System.nanoTime() - (System.nanoTime() - last_system_time);

            update();
            repaint();

            if (lastEntityCheck + 1000000 < last_system_time) {
                lastEntityCheck = System.nanoTime();
                for(Enemy e : spawned_enemies.values()){
                    System.out.println(e.getMovement_internal_timer());
                    e.decrementMovementInternalTimer(1);

                    //enemies attack
                    if(d1.checkIfTouching(e)){
                        d1.setHit_points(e.attack());
                        hit_points_display.setText(String.format("%d", d1.getHit_points()));
                    }
                }
            }

            current_system_time = System.nanoTime();
            delta = Math.max(nanoInterval - current_system_time + last_system_time, 0);
            last_system_time = current_system_time;

            try {
                Thread.sleep(delta / 1000000, (int)(delta % 1000000));
            } catch (InterruptedException e) {throw new RuntimeException();}
        }
    }

    public void update(){
        //From here on out, place here updating methods ie. player pos, etc. - Dymes
        d1.move();
        map.verifyEntityPosition(d1);
        d1.printHotbarItems();
        spawnCheck();
        checkIfDropping();
    }

    void handleInteractions(Dummy_sus d1, Graphics g) {

        //check if player is within proximity of NPC and if F key is pressed, if so, display dialogue of NPC
        if(key_input.is_interacting && NPC1.checkIfTouching(d1)){
            NPC1.displayDialogue(g);
        }
    }

    void checkIfDropping(){
        ItemEntity dropped = d1.dropItems();
        
        if((dropped.getItem() != null)){
            drop_items.put(dropped.key, dropped);
        }
    }

    void spawnCheck(){
        for(Enemy e : spawned_enemies.values()){
            try{
                e.executeEnemyBehavior(d1);
                map.verifyEntityPosition(e);
            } catch(NullPointerException n){
                System.out.println("Null Enemy!");
            }
        }
        
        //spawning
        if(key_input.spawn_enemy){

            int x_coords = new Random().nextInt(0, (TILE_SIZE * max_map_col));
            int y_coords = new Random().nextInt(0, (TILE_SIZE * max_map_row));
            int enemy_id = new Random().nextInt(0, 100);

            //random spawning, equal chances lmao
            Enemy enemy;
            if(new Random().nextInt(1, 3) == 1){
                //d1 coords lang sa kapoy pangita HAHAHAHAHA - dymes
                enemy = new Enemy.Brit(d1.x, d1.y, TILE_SIZE, enemy_id);
                System.out.println("Spawned new Brit");
            }
            else{
                enemy = new Enemy.Soviet(d1.x, d1.y, TILE_SIZE, enemy_id);
                System.out.println("Spawned new Soviet");
            }

            spawned_enemies.put(enemy_id, enemy);
            key_input.spawn_enemy = false;
        }

    }

    void dropItems(Enemy e){
        int range = 64;
        int random_x = new Random().nextInt(0, range * 2) - range;
        int random_y = new Random().nextInt(0, range * 2) - range;
        int item_key = new Random().nextInt(0, 100);
        drop_items.put(item_key,
            new ItemEntity(
                item_key,
                e.x + random_x, 
                e.y + random_y, 
            new Random().nextInt(1, 3))
        );
    }

    void handleEnemies(Graphics g) {
        for(Enemy e : spawned_enemies.values()){
            try{
                e.display(g, map.camera);
            } catch (NullPointerException n){
                System.out.println("Trying to Render Null Enemy!!");
            }

            //killing with range
            if(key_input.kill_enemy && e.checkIfTouching(d1)){

                e.setHit_points(d1.attack());

                if(e.getHit_points() == 0){
                    enemy_ids.add(e.key);

                    System.out.println("Enemy killed at x: " + e.x + " y: " + e.y);
                    
                    dropItems(e);

                    System.out.println("Dropped Item count: " + drop_items.size());
                }
                    
                key_input.kill_enemy = false;
            }
        }
    }

    void handleItems(Graphics g) {
        for(ItemEntity item : drop_items.values()){
            try{
                item.display(g, map.camera);
            } catch (NullPointerException n){
                System.out.println("Trying to Render Null Item!!");
            }
        }

        for(ItemEntity item : drop_items.values()){
            //if collected
            if(item.checkIfTouching(d1) && d1.getSize() != 5 && item.is_pickable){
                collected_items_keys.add(item.key);
                d1.addItem(item.getItem());
            }
            //if just dropped, don't pick
            if(!item.checkIfTouching(d1) && !item.is_pickable){
                item.is_pickable = true;
            }
        }
    }

    void removeEntities() {
        //remove killed enemies
        for(Integer key : enemy_ids){
            spawned_enemies.remove(key);
        }

        //remove collected items
        for(Integer key : collected_items_keys){
            drop_items.remove(key);
        }
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        map.view(d1);
        map.displayTiles(g);
        d1.display(g, map.camera);
        NPC1.display(g, map.camera);

        handleEnemies(g);
        handleItems(g);
        removeEntities();

        handleInteractions(d1, g);

        Hotbar.displayHotbar(g);
        d1.displayHotbarItems(g);

    }

}
