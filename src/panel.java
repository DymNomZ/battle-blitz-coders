package src;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

import classes.entities.Dummy;
import classes.entities.Dummy_sus;


public class Panel extends JPanel {

    public final int SCALE = 2, DEF_DIMENSION = 32;
    public final int TILE_SIZE = DEF_DIMENSION * SCALE;
    public final int MAX_SCREEN_ROW = 10, MAX_SCREEN_COL = 20, SCREEN_TILE_SIZE = 64;
    public final int SCREEN_WIDTH = SCREEN_TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = SCREEN_TILE_SIZE * MAX_SCREEN_ROW;

    //kani sila ba ang max_map row and col siguro e public ang BOUNDARIES pud kay mag lahi2 per map
    public int max_map_row, max_map_col;

    public final int NORTH_MAP_BOUNDARY = 0;
    public final int WEST_MAP_BOUNDARY = 0;
    public final int SOUTH_MAP_BOUNDARY;
    public final int EAST_MAP_BOUNDARY;

    MapConstructor map;
    
    KeyHandler key_input = new KeyHandler();

    //temporary
    Dummy d;
    Dummy_sus d1;
    
    public Panel(){

        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
        this.addKeyListener(key_input);

        map = new MapConstructor("assets/maps/field_test.zip", SCREEN_WIDTH, SCREEN_HEIGHT, TILE_SIZE);

        max_map_row = map.getMap_height();
        max_map_col = map.getMap_length();

        SOUTH_MAP_BOUNDARY = max_map_row * TILE_SIZE;
        EAST_MAP_BOUNDARY = max_map_col * TILE_SIZE;

        d = new Dummy(SCREEN_WIDTH, SCREEN_HEIGHT, TILE_SIZE, max_map_col, max_map_row);
        d1 = new Dummy_sus(max_map_row * TILE_SIZE, max_map_col * TILE_SIZE, TILE_SIZE);
    }

    //COLLISION TEST
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

    Panel p = this;

    //this will listen to the timer, and I think the Timer class creates a thread?, maybe that's why we need to listen to it?
    //source: https://www.reddit.com/r/javahelp/comments/6d5rr4/threads_or_timer_for_java_game/
    private final ActionListener timer_listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            //we pass our key handler so that our dummy can check which keys are pressed

            //d.calculateNextPosition(key_input, p);
            d1.move(key_input);
            map.verifyEntityPosition(d1);
            
            //repaint calls the paintComponent method again, so you can imagine, it redraws everything on the screen
            //basically updating what is displayed
            repaint();
        }
    };

    public void start_clock(){
        Timer timer = new Timer(10, timer_listener);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g){

        super.paintComponent(g);
        map.view(d1);
        //map.displayTiles(g, TILE_SIZE, d, SCREEN_HEIGHT, SCREEN_WIDTH);
        //d.displayDummy(g, TILE_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT);
        map.displayTiles(g);
        d1.display(g, map.camera);
    }
}
