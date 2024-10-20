package src;
import classes.entities.Dummy;
import classes.entities.Dummy_sus;
import classes.entities.Enemy;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements Runnable {

    public static int FPS = 60; // delta loop purposes
    public static int SCALE = 2, DEF_DIMENSION = 32;
    public static int TILE_SIZE = DEF_DIMENSION * SCALE;
    public static int MAX_SCREEN_ROW = 10, MAX_SCREEN_COL = 20, SCREEN_TILE_SIZE = 64;
    public static int SCREEN_WIDTH = SCREEN_TILE_SIZE * MAX_SCREEN_COL;
    public static int SCREEN_HEIGHT = SCREEN_TILE_SIZE * MAX_SCREEN_ROW;

    //kani sila ba ang max_map row and col siguro e public ang BOUNDARIES pud kay mag lahi2 per map
    public int max_map_row, max_map_col;

    public final int NORTH_MAP_BOUNDARY = 0;
    public final int WEST_MAP_BOUNDARY = 0;
    public final int SOUTH_MAP_BOUNDARY;
    public final int EAST_MAP_BOUNDARY;

    MapConstructor map;
    
    KeyHandler key_input;

    Thread main_thread;

    //temporary
    Dummy d;
    Dummy_sus d1;

    //temporary, will be deprecated after attacking mechanism is implemented
    Queue<Integer> enemy_id_queue = new LinkedList<>();

    HashMap<Integer, Enemy> spawned_enemies = new HashMap<>();

    List<Enemy> enemies = new ArrayList<>();

    public Panel(){

        map = new MapConstructor("assets/maps/field_test.zip", SCREEN_WIDTH, SCREEN_HEIGHT);

        key_input = new KeyHandler();

        max_map_row = map.getMap_height();
        max_map_col = map.getMap_length();

        SOUTH_MAP_BOUNDARY = max_map_row * TILE_SIZE;
        EAST_MAP_BOUNDARY = max_map_col * TILE_SIZE;

        //d = new Dummy(SCREEN_WIDTH, SCREEN_HEIGHT, TILE_SIZE, max_map_col, max_map_row);
        d1 = new Dummy_sus(max_map_row * TILE_SIZE, max_map_col * TILE_SIZE, TILE_SIZE);

        //temporary adding enemies
        // enemies.add(new Enemy.Brit(1600,1600,TILE_SIZE));
        // enemies.add(new Enemy.Soviet(1700,65,TILE_SIZE));

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(key_input);
    }

    //COLLISION TEST
    @Deprecated
    public void collisionCheck(){
        //Checks if the dummy is colliding with map border
        int half_scale = TILE_SIZE / 2;
        //System.out.println(d.getDelta_x() + " " + d.isColliding_left());
        int d_top_hitbox = d.getY_pos() - half_scale + d.getDelta_y() + 4;
        int d_down_hitBox = d.getY_pos() + half_scale + d.getDelta_y() - 4;
        int d_right_hitbox = d.getX_pos() + half_scale + d.getDelta_x() - 4;
        int d_left_hitbox = d.getX_pos() - half_scale + d.getDelta_x() + 4;

        boolean is_colliding_r = false;
        boolean is_colliding_l = false;
        boolean is_colliding_t = false;
        boolean is_colliding_d = false;

	    is_colliding_r = (d_right_hitbox > EAST_MAP_BOUNDARY);
	    is_colliding_l = (d_left_hitbox < WEST_MAP_BOUNDARY);
	    is_colliding_d = (d_down_hitBox > SOUTH_MAP_BOUNDARY);
	    is_colliding_t = (d_top_hitbox < NORTH_MAP_BOUNDARY);

        int x_diff = 0, y_diff = 0;

	    int[][] map_tiles = map.getMap_indexes();
        for(int i = 0;i < map_tiles.length;i++){
            for(int j = 0;j < map_tiles[i].length;j++){
                if(map_tiles[i][j] == 4 || map_tiles[i][j] == 5){
                    int tile_xpos = ((j + 1) * DEF_DIMENSION * SCALE) - half_scale;
                    int tile_ypos = ((i + 1) * DEF_DIMENSION * SCALE) - half_scale;
                    boolean is_same_row = d_top_hitbox < tile_ypos + (half_scale) && d_down_hitBox > tile_ypos - ((DEF_DIMENSION/2) * SCALE);
                    boolean is_same_col = d_left_hitbox < tile_xpos + (half_scale) && d_right_hitbox > tile_xpos - ((DEF_DIMENSION/2) * SCALE) ;

                    if(!is_colliding_l && (d_left_hitbox <= tile_xpos + half_scale  && is_same_row && d_left_hitbox >= tile_xpos - half_scale)){
	                    is_colliding_l = true;
                    }
                    if(!is_colliding_r && (d_right_hitbox >= tile_xpos - half_scale  && is_same_row && d_right_hitbox <= tile_xpos + half_scale)){
	                    is_colliding_r = true;

                    }
                    if(!is_colliding_t &&  (d_top_hitbox  <= tile_ypos + half_scale  && is_same_col && d_top_hitbox >= tile_ypos - half_scale)){
	                    is_colliding_t = true;
                    }
                    if(!is_colliding_d && (d_down_hitBox >= tile_ypos - half_scale && is_same_col && d_down_hitBox <= tile_ypos + half_scale)){
                        is_colliding_d = true;
                    }


                }
            }
        }


        d.setColliding_left(is_colliding_l);
        d.setColliding_down(is_colliding_d);
        d.setColliding_top(is_colliding_t);
        d.setColliding_right(is_colliding_r);

    }

    

    public void start_main_thread(){
        main_thread = new Thread(this);
        main_thread.start();
    }

    @Override
    public void run(){
        double draw_interval = 1000000000/FPS, delta = 0;
        long last_system_time = System.nanoTime();
        long current_system_time;
        long timer = 0;
        int draw_count = 0;

        while(main_thread != null){

            current_system_time = System.nanoTime();
            delta += (current_system_time - last_system_time) / draw_interval;
            timer += (current_system_time - last_system_time);
            last_system_time = current_system_time;

            if(delta >= 1){
                update();
                repaint();
                spawn_check();
                delta--;
                draw_count++;
            }

            if(timer >= 1000000000){
                draw_count = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        //From here on out, place here updating methods ie. player pos, etc.
        d1.move(key_input);
        map.verifyEntityPosition(d1);

    }

    public void spawn_check(){

        //spawning
        if(key_input.spawn_enemy){

            int x_coords = new Random().nextInt(0, (TILE_SIZE * max_map_col));
            int y_coords = new Random().nextInt(0, (TILE_SIZE * max_map_row));
            int enemy_id = new Random().nextInt(0, 100);

            //random spawning, equal chances lmao
            Enemy enemy;
            if(new Random().nextInt(1, 2) == 1){
                enemy = new Enemy.Brit(x_coords, y_coords, TILE_SIZE);
            }
            else{
                enemy = new Enemy.Soviet(x_coords, y_coords, TILE_SIZE);
            }

            enemy_id_queue.add(enemy_id);
            spawned_enemies.put(enemy_id, enemy);
            //enemies.add(enemy);

            //System.out.println(enemy_id_queue.size() + " " + spawned_enemies.size());
            key_input.spawn_enemy = false;
        }

        //temporary killing via queue
        if(key_input.kill_enemy){
            Integer id = enemy_id_queue.poll();
            spawned_enemies.remove(id);
            key_input.kill_enemy = false;
        }


        // for(Enemy enemy : enemies){
        //     enemy.moveTowardsEntity(d1);
        //     map.verifyEntityPosition(enemy);
        // }

        for(Integer id : enemy_id_queue){
            Enemy e = spawned_enemies.get(id);
            e.moveTowardsEntity(d1);
            map.verifyEntityPosition(e);
        }
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        map.view(d1);

        //map.displayTiles(g, TILE_SIZE, d, SCREEN_HEIGHT, SCREEN_WIDTH);
        //d.displayDummy(g, TILE_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT);

        map.displayTiles(g);
        d1.display(g, map.camera);
        // for(Enemy e : enemies){
        //     e.display(g, map.camera);
        // }
        for(Integer id : enemy_id_queue){
            Enemy e = spawned_enemies.get(id);
            e.display(g, map.camera);
        }
    }

    //Thank you for your service 💪
    @Deprecated
    private final ActionListener timer_listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            
            //we pass our key handler so that our dummy can check which keys are pressed

            d1.move(key_input);
            map.verifyEntityPosition(d1);


            for(Enemy enemy : enemies){
                enemy.moveTowardsEntity(d1);
                map.verifyEntityPosition(enemy);
            }
            
            repaint();
        }
        
    };

    @Deprecated
    public void start_clock(){
        Timer timer = new Timer(10, timer_listener);
        timer.start();
    }
}
