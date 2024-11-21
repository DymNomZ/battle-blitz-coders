package src;

import classes.Asset.Sprite.Sprite;
import classes.GUI.EnemyHealthBar;
import classes.GUI.Hotbar;
import classes.GUI.PlayerHealthBar;
import classes.dialogues.Dialogues;
import classes.entities.*;
import classes.items.Item;
import classes.items.Melee;
import classes.items.Ranged;
import classes.sprites.EntitySprite;
import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    public static int FPS = 60; // delta loop purposes
    // public static Audio backgroundAudio = new Audio().load("mainBackground.wav").play();
    public static final int SCALE = 2;
    public static final int DEF_DIMENSION = 32;
    public static final int TILE_SIZE = DEF_DIMENSION * SCALE;
    public static int SCREEN_WIDTH = 1366, SCREEN_HEIGHT = 768;

    public int max_map_row, max_map_col;
    private boolean is_timing = false;
    private int seconds = 0;
    private int enemy_count = 0, offset_x = 0, offset_y = 10;

    MapConstructor map;
    
    KeyHandler key_input;
    MouseHandler mouse_handler;

    Thread main_thread;

    //temporary
    Player d1;
    NPC NPC1;

    HashMap<Long, Enemy> spawned_enemies = new HashMap<>();
    HashMap<Long, ItemEntity> drop_items = new HashMap<>();

    List<Long> enemy_ids = new LinkedList<>();

    List<ProjectileEntity> projectiles = new ArrayList<>();

    public GamePanel(){

        Utils.loadFonts();

        map = new MapConstructor("assets/maps/trial_map_1.zip", SCREEN_WIDTH, SCREEN_HEIGHT);

        key_input = new KeyHandler();
        mouse_handler = new MouseHandler();

        max_map_row = map.getMap_height();
        max_map_col = map.getMap_length();
        System.out.println(max_map_col + " " + max_map_row);

        d1 = new Player(
            CharacterHandler.getSelected_character(), 1000, 
            10 * TILE_SIZE, 13 * TILE_SIZE, 64, 40, key_input
        );

        //temp NPC
        NPC1 = new NPC.TempNPC(
            17 * TILE_SIZE, 10 * TILE_SIZE,
            64, 40, ((Sprite) ((EntitySprite) CharacterHandler.randomizer()).toLeft())
        );

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(key_input);
        this.addMouseListener(mouse_handler);
        this.setLayout(null);

    }

    public void updateMousePosition(int x, int y) {
        mouse_handler.mouse_x = x - (d1.x - map.camera.x);
        mouse_handler.mouse_y = y - (d1.y - map.camera.y);
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

            decrementCooldown();
            mouse_handler.mouse_location_on_screen = MouseInfo.getPointerInfo().getLocation();

            SwingUtilities.convertPointFromScreen(mouse_handler.mouse_location_on_screen,this);
            updateMousePosition(mouse_handler.mouse_location_on_screen.x,mouse_handler.mouse_location_on_screen.y);

            if (lastEntityCheck + 1000000000 < last_system_time) {

                lastEntityCheck = System.nanoTime();

                if(Dialogues.PHASE_STATE == 0) is_timing = true;

                if(is_timing) seconds++;

                triggers(this.getGraphics());

            }

            current_system_time = System.nanoTime();
            delta = Math.max(nanoInterval - current_system_time + last_system_time, 0);
            last_system_time = current_system_time;

            try {
                Thread.sleep(delta / 1000000, (int)(delta % 1000000));
            } catch (InterruptedException e) {throw new RuntimeException();}
        }
    }

    public void decrementCooldown(){
        d1.decrementCooldowns(1);
        Iterator<Enemy> enemy_it = spawned_enemies.values().iterator();
        while(enemy_it.hasNext()){
            Enemy e = enemy_it.next();
            e.decrementCooldowns(1);
        }
    }

    public void update(){
        //From here on out, place here updating methods ie. player pos, etc. - Dymes

        d1.move();
        map.verifyEntityPosition(d1);
        d1.printHotbarItems();
        d1.updateInfo();
        bulletCheck();
        enemyCheck();
        removeEntities();
        checkIfDropping();
        purgeCheck();
    }

    private void purgeCheck(){

        if(key_input.purge){
            //purge for demo purposes
            for(Enemy e : spawned_enemies.values()){
                dropItems(e);
                enemy_ids.add(e.key);
            }

            key_input.purge = false;
        }
    }

    private void bulletCheck(){
        Iterator<ProjectileEntity> it = projectiles.iterator();
        while(it.hasNext()){
            ProjectileEntity current_projectile = it.next();
            try{
                current_projectile.executeProjectileBehavior();
                map.verifyEntityPosition(current_projectile);
                if(!current_projectile.is_player_friendly){
                    current_projectile.checkEntityCollision(d1);
                }
                if(current_projectile.is_colliding()){
                    it.remove();
                }

            } catch(NullPointerException n){
                System.out.println("Null Projectile!");
            }
        }
        if(mouse_handler.left_is_pressed && d1.getCurrentItem() instanceof Ranged){
            if(d1.shooting_cooldown == 0){
                projectiles.add(new ProjectileEntity.TemporaryBullet(d1.x, d1.y, d1.x + mouse_handler.mouse_x, d1.y + mouse_handler.mouse_y));
                d1.initiateShootingCooldown(60);
            }
        }
    }

    void handleInteractions(Player d1, Graphics g) {

        //check if player is within proximity of NPC and if F key is pressed, if so, display dialogue of NPC
        if(key_input.is_interacting && NPC1.checkIfTouching(d1)){

            if(Dialogues.PHASE_STATE == 0){
                is_timing = false;
                seconds = 0;
                Dialogues.DIALOGUE_NUMBERS[0] = 1;
                Dialogues.PHASE_STATE = 1;
            }

            //handle next
            if(key_input.skip_dialogue){
                Dialogues.DIALOGUE_NUMBERS[0]++;
                key_input.skip_dialogue = false;
            }

            //give initial item to player
            if(Dialogues.DIALOGUE_NUMBERS[0] == 6 && !PlayerData.received_initital_weapon){

                Item item = new Melee().getRandom();

                d1.addItem(item);
                PlayerData.received_initital_weapon = true;
            }

            Dialogues.handleNPCDialogues(g);
        }
        else if(!(NPC1.checkIfTouching(d1))){
            key_input.is_interacting = false;
            Dialogues.handleReset();
        }
    }

    void checkIfDropping(){
        ItemEntity dropped = d1.dropItems();
        
        if(dropped != null && dropped.getItem() != null){
            drop_items.put(dropped.key, dropped);
        }
    }

    void enemyCheck(){

        Iterator<Enemy> enemy_it = spawned_enemies.values().iterator();

        while(enemy_it.hasNext()){
            Enemy e = enemy_it.next();
            if(e.getHit_points() <= 0){
                enemy_ids.add(e.key);
                dropItems(e);
                PlayerData.kill_count++;
                continue;
            }

            try{
                e.executeEnemyBehavior(d1);
                map.verifyEntityPosition(e);
                for(ProjectileEntity projectile : projectiles){
                    if(projectile.is_player_friendly){
                        e.checkEntityCollision(projectile);
                    }
                }
            } catch(NullPointerException n){
                System.out.println("Null Enemy!");
            }

            //killing with range
            if(mouse_handler.left_is_pressed && e.checkIfTouching(d1) && d1.getCurrentItem() instanceof Melee){

                e.damage(d1.attack());

                if(e.getHit_points() <= 0){
                    enemy_ids.add(e.key);

                    dropItems(e);

                    PlayerData.kill_count++;
                }
                mouse_handler.left_is_pressed = false;
            }
        }
        
        //spawning
        if(key_input.spawn_enemy) mobSpawner(d1.getTileXPosition(), d1.getTileYPosition(), -1);

    }

    void mobSpawnerHandler(int type){

        if(!is_timing) is_timing = true;

        mobSpawner(offset_x, offset_y, type);
        
        enemy_count++;
        offset_y++;

        if(enemy_count == 10){
            PlayerData.trigger_spawning = false;
            enemy_count = 0;
            offset_x = 0;
            offset_y = 10;
            is_timing = false;
            seconds = 0;
        }
    }

    void mobSpawner(int offset_x, int offset_y, int type){

        boolean successfullySpawned = false;

        do{
            int tile_x = offset_x + (new Random().nextInt(0,11) - 5);
            int tile_y = offset_y + (new Random().nextInt(0,11) - 5);

            if(type == -1) type = new Random().nextInt(0, 2);

            successfullySpawned = spawnEnemy(tile_x,tile_y,2, 
            type == 0 ? Enemy.EnemySpecies.SLIME : Enemy.EnemySpecies.VIRUS);

        }while(!successfullySpawned);
        key_input.spawn_enemy = false;
    }

    /*
    * To use this, tile_x and tile_y would be to check the specific tile the entity will be spawned on
    * area_check_size is there in case if entitiy is bigger than tile size and would need to check a bigger
    * area for any solid blocks - SET H
    * */
    //TODO Area-check
    boolean spawnEnemy(int tile_x, int tile_y,int area_check_size,Enemy.EnemySpecies enemySpecies){
        for(int i = tile_y - area_check_size;i < tile_y + area_check_size + 1;i++){
            for(int j = tile_x - area_check_size;j < tile_x + area_check_size + 1;j++){
                try{
                    if(map.tiles[tile_y][tile_x].is_solid) return false;
                } catch (ArrayIndexOutOfBoundsException exc){
                    // Do Nothing, continue loop
                }

            }
        }
            long enemy_id = System.nanoTime();
            Enemy enemy = EnemyFactory.createEnemy
                    (enemySpecies,
                    tile_x * TILE_SIZE,
                    tile_y * TILE_SIZE,
                    TILE_SIZE , enemy_id,
                    projectiles);

            spawned_enemies.put(enemy_id,enemy);
            return true;

    }

    void dropItems(Enemy e){
        int range = 64;
        int random_x = new Random().nextInt(0, range * 2) - range;
        int random_y = new Random().nextInt(0, range * 2) - range;
        long item_key = System.nanoTime();
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
                EnemyHealthBar.update_display_health_bar((double)e.getHit_points() / e.getMax_hit_points());
                EnemyHealthBar.displayHealthBar(g,e.x - map.camera.x, e.y - map.camera.y, e.width);
                e.display(g, map.camera);
            } catch (NullPointerException n){
                System.out.println("Trying to Render Null Enemy!!");
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
                //trigger you picked up ranged weapon dialogue
                if(item.getItem() instanceof Ranged && !PlayerData.trigger_range_info){
                    PlayerData.trigger_range_info = true;
                }
                d1.addItem(item.getItem());
                drop_items.remove(item.key);
                break;
            }
            //if just dropped, don't pick
            if(!item.checkIfTouching(d1) && !item.is_pickable){
                item.is_pickable = true;
            }
        }
    }

    void handleProjectiles(Graphics g){
        Iterator<ProjectileEntity> it = projectiles.iterator();

        while(it.hasNext()){
            try{
                ProjectileEntity current_projectile = it.next();
                current_projectile.display(g, map.camera);
            } catch (NullPointerException n){
                System.out.println("Trying to Render Null Bullet!!");
            } catch (ConcurrentModificationException cm){
                System.out.println("LOl!");
            }
        }
    }

    void removeEntities() {
        //remove killed enemies
        for(Long key : enemy_ids){
            spawned_enemies.remove(key);
        }
    }

    //trigger first ranged weapon dialogue
    void showRangedInfo(Graphics g){

        if(!is_timing) is_timing = true;
        
        if(seconds == 3){
            is_timing = false;
            seconds = 0;
            PlayerData.range_info_shown = true;
        }
        
    }

    void triggers(Graphics g){

        int enemy_type = 1;

        switch(Dialogues.PHASE_STATE){
            case 2 -> {
                offset_x = 35;
                enemy_type = 0;
                if(PlayerData.trigger_range_info && !PlayerData.range_info_shown) {
                    showRangedInfo(g);
                }
            }
            case 3 -> offset_x = 61;

        }

        if(d1.x >= PlayerData.position_checks[PlayerData.check_pointer] 
        && !PlayerData.stage_spawns[PlayerData.check_pointer]) {
            PlayerData.trigger_spawning = true;
            PlayerData.stage_spawns[PlayerData.check_pointer] = true;
        }
        if(PlayerData.trigger_spawning && PlayerData.stage_spawns[PlayerData.check_pointer]) {
            mobSpawnerHandler(enemy_type);
        }

    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        map.view(d1);
        map.displayTiles(g);

        handleItems(g);
        handleEnemies(g);
        handleProjectiles(g);

        NPC1.display(g, map.camera);
        d1.display(g, map.camera);

        handleInteractions(d1, g);
        PlayerHealthBar.displayHealthBar(g);
        Hotbar.displayHotbar(g);
        d1.displayHotbarItems(g);

        Dialogues.handlePopUpDialogues(g, seconds);

        //debug check coords for spawning purposes
        // System.out.println("POS: " + d1.x + " " + d1.y);
        // System.out.println("POS: " + d1.getTileXPosition() + " " + d1.getTileYPosition());
    }

}
