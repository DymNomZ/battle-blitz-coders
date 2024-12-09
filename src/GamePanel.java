package src;

import classes.Asset.Audio.Audio;
import classes.Asset.Sprite.Sprite;
import classes.GUI.*;
import classes.dialogues.Dialogues;
import classes.entities.*;
import classes.items.ConsumableHealing;
import classes.items.Item;
import classes.items.Melee;
import classes.items.Ranged;
import classes.sprites.EntitySprite;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    public static int FPS = 60; // delta loop purposes
    public static final int SCALE = 2;
    public static final int DEF_DIMENSION = 32;
    public static final int TILE_SIZE = DEF_DIMENSION * SCALE;
    public static int SCREEN_WIDTH = 1366, SCREEN_HEIGHT = 768;

    private static final Audio BACKGROUND_MUSIC = new Audio().load("background/menu_background.wav").setRepeat(true).setVolume(5).play();
    private Music_State music_last_handled = Music_State.Level1;
    private String next_song = null;
    public int max_map_row, max_map_col;
    private int second = 0;
    private int enemy_count = 0, offset_y = 10;

    MapConstructor map;
    KeyHandler key_input;
    MouseHandler mouse_handler;
    public Thread main_thread;
    Player d1;
    NPC NPC1;

    final List<Enemy> spawned_enemies = new CopyOnWriteArrayList<>();
    HashMap<Long, ItemEntity> drop_items = new HashMap<>();
    List<ProjectileEntity> projectiles = new ArrayList<>();
    List<MeleeAttackEntity> melee_attacks = new ArrayList<>();

    public List<Enemy> getSpawned_enemies() {
        return spawned_enemies;
    }

    public GamePanel(){

        Utils.loadFonts();

        map = new MapConstructor("assets/maps/trial_map_1.zip", SCREEN_WIDTH, SCREEN_HEIGHT);

        key_input = new KeyHandler();
        mouse_handler = new MouseHandler();

        max_map_row = map.getMap_height();
        max_map_col = map.getMap_length();
        System.out.println(max_map_col + " " + max_map_row);

        //10 13 real spawn
        //195 * TS * 2, 14 * TS * 2 boss spawn debug
        d1 = new Player(
            CharacterHandler.getSelected_character(), 1000,
            10 * TILE_SIZE, 13 * TILE_SIZE, 64, 40, key_input
            //315 * TILE_SIZE, 28 * TILE_SIZE, 64, 40, key_input
            //195 * TILE_SIZE * 2, 14 * TILE_SIZE * 2, 64, 40, key_input
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

    public void start_main_thread(){
        main_thread = new Thread(this);
        main_thread.start();
        Dialogues.initializeValues();
        PlayerData.initializeValues();
    }

    @Override
    public void run() {
        long nanoInterval = 16666667; // 60 fps
        long lastEntityCheck = 0;
        long last_system_time = System.nanoTime();
        long current_system_time;
        long delta = 0;

        BACKGROUND_MUSIC.load("background/Bit Shift.wav").setVolume(Audio.DEFAULT_VOLUME).play();

        while (main_thread != null) {
            
            last_system_time = System.nanoTime() - (System.nanoTime() - last_system_time);
            update();
            repaint();

            decrementCooldown();
            mouse_handler.mouse_location_on_screen = MouseInfo.getPointerInfo().getLocation();

            SwingUtilities.convertPointFromScreen(mouse_handler.mouse_location_on_screen,this);
            updateMousePosition(mouse_handler.mouse_location_on_screen.x,mouse_handler.mouse_location_on_screen.y);

            if (lastEntityCheck < last_system_time) {

                lastEntityCheck = System.nanoTime() + 1000000000;
                
                second++;
                // System.out.println("SECOND: " + second + " ENEMY COUNT: " + enemy_count);
                // System.out.println("KILLS: " + PlayerData.kill_count + " STATE: " + Dialogues.PHASE_STATE);
                // System.out.println("POS TILE: " + d1.getTileXPosition() + " " + d1.getTileYPosition() + " " + PlayerData.offsets[PlayerData.check_pointer]);

                triggers(this.getGraphics());

            }

            current_system_time = System.nanoTime();
            delta = Math.max(nanoInterval - current_system_time + last_system_time, 0);
            last_system_time = current_system_time;

            try {
                Thread.sleep(delta / 1000000, (int)(delta % 1000000));
            } catch (InterruptedException e) {
                BACKGROUND_MUSIC.load("background/menu_background.wav").setVolume(5).play();
                break;
            }
        }
    }

    public void updateMousePosition(int x, int y) {
        mouse_handler.mouse_x = x - (d1.x - map.camera.x);
        mouse_handler.mouse_y = y - (d1.y - map.camera.y);
    }

    public void decrementCooldown(){
        d1.decrementCooldowns(1);

        for(Enemy e : spawned_enemies){
            e.decrementCooldowns(1);
        }
    }

    public void update(){
        //From here on out, place here updating methods ie. player pos, etc. - Dymes
        d1.move();
        map.verifyEntityPosition(d1);
        d1.printHotbarItems();
        d1.updateInfo();
        checkPlayerAttack();
        handleConsumption();
        checkIfDropping();
        purgeCheck();
    }

    public void checkPlayerAttack(){
        //GENERAL ATTACK
        if(mouse_handler.left_is_pressed){
            if(d1.getCurrentItem() instanceof Melee){
                int melee_x_location;
                int melee_y_location = d1.y;
                if(d1.x + mouse_handler.mouse_x > d1.x){
                    melee_x_location = d1.x + d1.width;

                } else {
                    melee_x_location = d1.x - d1.width;
                }
                melee_attacks.add(new MeleeAttackEntity.PlayerMeleeAttack(d1,melee_x_location,melee_y_location));

                mouse_handler.left_is_pressed = false;
            } else if(d1.getCurrentItem() instanceof Ranged){
                if(d1.shooting_cooldown == 0){
                    projectiles.add(new ProjectileEntity.PlayerBullet(d1.x, d1.y, d1.x + mouse_handler.mouse_x, d1.y + mouse_handler.mouse_y));
                    d1.initiateShootingCooldown(40);
                }
            }
        }
    }

    void handleConsumption(){
        if(mouse_handler.right_is_pressed){
            if(d1.getCurrentItem() instanceof ConsumableHealing && d1.getHit_points() < d1.getMax_hit_points()){
                d1.setHit_points(d1.getMax_hit_points());
                d1.removeItem();
            }
        }
    }

    private void purgeCheck(){

        if(key_input.purge){
            //purge for demo purposes
            for(Enemy e : spawned_enemies){
                dropItems(e);
            }
            spawned_enemies.clear();

            key_input.purge = false;
        }
    }

    void handleInteractions(Player d1, Graphics g) {

        //Boss sequence
        if(key_input.skip_dialogue && Dialogues.PHASE_STATE == 6){
            Dialogues.serato_dnum++;
            key_input.skip_dialogue = false;
        }

        //check if player is within proximity of NPC and if F key is pressed, if so, display dialogue of NPC
        if(key_input.is_interacting && NPC1.checkIfTouching(d1)){

            //System.out.println("IS TOUCHING");

            if(Dialogues.PHASE_STATE == 0){
                //move to next state and next npc dialogue
                Dialogues.npc_dnum = 1;
                Dialogues.PHASE_STATE = 1;
            }

            //handle next
            if(key_input.skip_dialogue){
                if(Dialogues.npc_dnum < Dialogues.LIMIT) Dialogues.npc_dnum++;
                key_input.skip_dialogue = false;
            }

            //give initial item to player
            if(Dialogues.npc_dnum == 6 && !PlayerData.received_initial_weapon){

                Item item = new Melee().getRandom();

                d1.addItem(item);
                PlayerData.received_initial_weapon = true;
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

    void mobSpawnerHandler(int type){

        if(enemy_count < 30) mobSpawner(
            PlayerData.offsets[PlayerData.check_pointer], 
            offset_y, type
        );

        offset_y++;
    }

    void mobSpawner(int offset_x, int offset_y, int type){

        boolean successfullySpawned = false;
        int error_count = 0;
        do{
            error_count++;
            if(error_count >= 10){
                //System.out.println("Spawning unsuccessful");
                break;
            }
            int tile_x = offset_x + (new Random().nextInt(0,11) - 5);
            int tile_y = offset_y + (new Random().nextInt(0,11) - 5);

            if(type == -1) type = new Random().nextInt(0,2);

            successfullySpawned = spawnEnemy(tile_x, tile_y,2,
                    type == 0 ? EnemyFactory.EnemySpecies.SLIME : EnemyFactory.EnemySpecies.VIRUS);


        }while(!successfullySpawned);
        key_input.spawn_enemy = false;
    }

    /*
    * To use this, tile_x and tile_y would be to check the specific tile the entity will be spawned on
    * area_check_size is there in case if entitiy is bigger than tile size and would need to check a bigger
    * area for any solid blocks - SET H
    * */
    public boolean spawnEnemy(int tile_x, int tile_y, int area_check_size, EnemyFactory.EnemySpecies enemySpecies){

        boolean isAreaFree = true;

        for (int i = tile_x - area_check_size; i <= tile_x + area_check_size; i++) {
            for (int j = tile_y - area_check_size; j <= tile_y + area_check_size; j++) {
                try {
                    //System.out.println(i+ " " + j);
                    if (map.tiles[j][i].is_solid) {
                        return false;
                    }
                } catch (ArrayIndexOutOfBoundsException exc) {
                    // Tile is out of bounds, consider it not free.
                    //System.out.println("Trying to spawn outside of map bounds!!");
                    isAreaFree = false;
                }
            }
        }

        if (!isAreaFree) {
            return false;
        }
            long enemy_id = System.nanoTime();
            Enemy enemy = EnemyFactory.createEnemy
                    (enemySpecies,
                            tile_x * TILE_SIZE,
                            tile_y * TILE_SIZE,
                            TILE_SIZE, enemy_id,
                            projectiles);

            spawned_enemies.add(enemy);
            enemy_count++;
            return true;

    }

    void dropItems(Enemy e){
        int range = 20;
        int random_x = new Random().nextInt(0, range * 2) - range;
        int random_y = new Random().nextInt(0, range * 2) - range;
        long item_key = System.nanoTime();
        drop_items.put(item_key,
            new ItemEntity(
                item_key,
                e.x + random_x, 
                e.y + random_y,
            new Random().nextInt(1, 4))
        );
    }

    void handleEnemies(Graphics g) {
            for(Enemy e : spawned_enemies) {
                try {
                    if(e.getHit_points() <= 0){

                        dropItems(e);
                        PlayerData.kill_count++;

                        if(e instanceof BossEnemy.SirSerato sirSerato){
                            sirSerato.removePeashooters();
                            sirSerato.removeSquashes();
                            BossEnemy.trigger_defeat = true;
                            sirSerato.stopCycle();
                        }

                        spawned_enemies.remove(e);
                        continue;
                    }

                    if(Dialogues.PHASE_STATE != 8) e. executeEnemyBehavior(d1);

                    map.verifyEntityPosition(e);
                    e.checkEntityCollision(d1);

                    for(ProjectileEntity projectile : projectiles){
                        if(projectile.is_player_friendly){
                            e.checkEntityCollision(projectile);
                        }
                    }

                    for(MeleeAttackEntity melee_attack_entity : melee_attacks){
                        if(melee_attack_entity instanceof MeleeAttackEntity.PlayerMeleeAttack){
                            e.checkEntityCollision(melee_attack_entity);
                        }
                    }


                    e.display(g, map.camera);
                } catch (NullPointerException n) {
                    System.out.println("Trying to Render Null Enemy!!");
                }
            }

            for(Enemy e : spawned_enemies){
                if(!(e instanceof BossEnemy)) {
                    EnemyHealthBar.update_display_health_bar((double) e.getHit_points() / e.getMax_hit_points());
                    EnemyHealthBar.displayHealthBar(g, e.x - map.camera.x, e.y - map.camera.y, e.width);
                } else {
                    BossHealthBar.update_display_health_bar((double)e.getHit_points()/e.getMax_hit_points());
                    if(e.getHit_points() > 0) BossHealthBar.displayHealthBar(g);
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
                current_projectile.executeProjectileBehavior();
                map.verifyEntityPosition(current_projectile);
                if(!current_projectile.is_player_friendly) {
                    current_projectile.checkEntityCollision(d1);
                }
                if(current_projectile.is_colliding()){
                    it.remove();
                    continue;
                }
                current_projectile.display(g, map.camera);
            } catch (NullPointerException n){
                System.out.println("Trying to Render Null Bullet!!");
            }
        }
    }

    void handleMeleeAttacks(Graphics g){
        Iterator<MeleeAttackEntity> it = melee_attacks.iterator();
        while(it.hasNext()){
            try{
                MeleeAttackEntity current_melee_entity = it.next();
                if(current_melee_entity.isToDestroy()){
                    it.remove();
                    continue;
                }
                current_melee_entity.toDestroy();
                current_melee_entity.display(g, map.camera);

            } catch (NullPointerException n){
                System.out.println("Trying to Render Null Melee Attack Entity!!");
            }
        }
    }

    void triggers(Graphics g){

        int enemy_type = 0;

        switch(Dialogues.PHASE_STATE){
            case 3 -> enemy_type = 1;
            case 4 -> {
                PlayerData.required_kills = 15;
                enemy_type = -1;
            }
            case 5 -> {
                NPC1.x = 198 * TILE_SIZE;
                NPC1.y = 32 * TILE_SIZE;
            }
            case 7 -> {
                if(!boss_spawned) spawnBoss();
            }
            case 8 -> {
                for(Enemy e : spawned_enemies){
                    if(e instanceof BossEnemy.SirSerato sirSerato){
                        sirSerato.spawnPeashooters();
                        break;
                    }
                }
            }
            case 9-> {
                for(Enemy e : spawned_enemies){
                    if(e instanceof BossEnemy.SirSerato sirSerato){
                        sirSerato.startBossFight();
                        break;
                    }
                }
            }
            case 12 -> spawned_enemies.clear();
            case 13 -> {
                if(!PlayerData.defeated_boss){
                    d1.x = 10 * TILE_SIZE;
                    d1.y = 10 * TILE_SIZE;
                    NPC1.x = 17 * TILE_SIZE;
                    NPC1.y = 10 * TILE_SIZE;
                    PlayerData.defeated_boss = true;
                }
            }
            case 14 -> handleVictory(g);
        }

        //check if player is within trigger range of enemy spawning
        if(d1.x >= PlayerData.position_checks[PlayerData.check_pointer] 
        && !PlayerData.stage_spawns[PlayerData.check_pointer] && Dialogues.PHASE_STATE > 1) {
            PlayerData.trigger_spawning = true;
            PlayerData.stage_spawns[PlayerData.check_pointer] = true;
        }

        if(PlayerData.trigger_spawning && PlayerData.stage_spawns[PlayerData.check_pointer]) {
            mobSpawnerHandler(enemy_type);
        }
        else {
            enemy_count = 0;
            offset_y = 10;
        }

    }

    void handleGameOver(Graphics g){
        if(d1.getHit_points() <= 0){
            PlayerData.is_alive = false;
            Utils.endOverlay(g, SCREEN_WIDTH, SCREEN_HEIGHT);
            add(General.Titles.DEFEAT);
        }
    }

    void handleVictory(Graphics g){
        add(General.Titles.VICTORY);
        Utils.endOverlay(g, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
    
    int opacity = 100;
    void handleFade(Graphics g, int add){
        opacity += add;
        g.setColor(new Color(0, 0, 0, opacity));
        g.drawRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        if(opacity == 255) Dialogues.PHASE_STATE = 13;
    }

    private enum Music_State {
        Level1,
        Level2,
        Level3,
        Hallway,
        Boss
    }

    private void handleBackgroundMusic() {
        if (next_song != null) {
            float volume = BACKGROUND_MUSIC.getVolume();
            if (volume > -50f) {
                volume -= 0.1f;
            } else {
                volume = Audio.DEFAULT_VOLUME;
                BACKGROUND_MUSIC.load(next_song).play();
                next_song = null;
            }
            BACKGROUND_MUSIC.setVolume(volume);
        }

        Point p = d1.getTilePosition();

        if ((p.x == 47 || p.x == 48) && p.y == 18) {
            if (music_last_handled == Music_State.Level1) return;
            music_last_handled = Music_State.Level1;
            next_song = "background/Bit Shift.wav";
        } else if (((p.x == 47 || p.x == 48) && p.y == 16) || (p.x == 99 && p.y == 12)) {
            if (music_last_handled == Music_State.Level2) return;
            music_last_handled = Music_State.Level2;
            next_song = "background/Lightless Dawn.wav";
        } else if ((p.x == 107 && p.y == 2) || (p.x == 153 && p.y == 14)) {
            if (music_last_handled == Music_State.Level3) return;
            music_last_handled = Music_State.Level3;
            next_song = "background/Gregorian Chant.wav";
        } else if ((p.x == 160 && p.y == 14) || ((p.x == 196 || p.x == 195) && p.y == 31)) {
            if (music_last_handled == Music_State.Hallway) return;
            music_last_handled = Music_State.Hallway;
            next_song = "background/Anxiety.wav";
        } else if ((p.x == 196 || p.x == 195) && p.y == 26) {
            if (music_last_handled == Music_State.Boss) return;
            music_last_handled = Music_State.Boss;
            next_song = "background/Dragon Castle.wav";
        }
    }

    boolean boss_spawned = false;
    public void spawnBoss(){
        boss_spawned = true;
        spawned_enemies.add(new BossEnemy.SirSerato(12450,354, TILE_SIZE * 3, 1,this,projectiles));
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);

        map.view(d1);
        map.displayTiles(g);

        handleItems(g);
        handleEnemies(g);
        handleProjectiles(g);
        handleMeleeAttacks(g);
        handleGameOver(g);
        handleBackgroundMusic();

        NPC1.display(g, map.camera);
        if(PlayerData.is_alive) d1.display(g, map.camera);

        handleInteractions(d1, g);
        Dialogues.handlePopUpDialogues(g, second, d1);
        PlayerHealthBar.displayHealthBar(g);
        Hotbar.displayHotbar(g);
        d1.displayHotbarItems(g);

        if(Dialogues.PHASE_STATE == 12) handleFade(g, 1);

        //debug check coords for spawning purposes
        // System.out.println("POS: " + d1.x + " " + d1.y);
        //System.out.println();
        //System.out.println("POS TILE: " + d1.getTileXPosition() + " " + d1.getTileYPosition());
    }

}
