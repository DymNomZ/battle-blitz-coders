package src;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.Timer;

import classes.entities.dummy;

//JPanel in a nutshell is our "canvas", that's where we put all our components like sprites, etc.
//The reasone why we let the panel dictate the size insstead of using the JFrame.setSize()
//is because it includes the border (the logo, title, and min/max/close buttns you see at the top)
//we want to specifically change the content screen itself, thats why we add our panel as a component
//rather than setting that size of our main_window via .setSize()
//More info: https://stackoverflow.com/questions/6593322/why-does-the-jframe-setsize-method-not-set-the-size-correctly

public class panel extends JPanel {
    //Dymes:
    //scale to multiply our default dimenstion because 32x32 pixels is too small for modern screens
    public final int SCALE = 2, DEF_DIMENSION = 32;
    public final int TILE_SIZE = DEF_DIMENSION * SCALE;
    //Number of tiles to display on screen by row and column. Added screen tile size as a reference to draw the tiles regardless of scale
    public final int MAX_SCREEN_ROW = 10, MAX_SCREEN_COL = 20, SCREEN_TILE_SIZE = 64;
    //Calculating these gives our screen resolution to be 1280 x 640
    public final int SCREEN_WIDTH = SCREEN_TILE_SIZE * MAX_SCREEN_COL;
    public final int SCREEN_HEIGHT = SCREEN_TILE_SIZE * MAX_SCREEN_ROW;



    //Dymes - Oct 2, 2024
    //The map's row and column length, temporary 50 for now, but will be easier to change later when we have different maps
    public int max_map_row = 50, max_map_col = 50;

    public final int NORTH_MAP_BOUNDARY = 0;
    public final int WEST_MAP_BOUNDARY = 0;
    public final int SOUTH_MAP_BOUNDARY = max_map_row * TILE_SIZE;
    public final int EAST_MAP_BOUNDARY = max_map_col * TILE_SIZE;

    map_constructor map = new map_constructor(
        "dummy_map", 
        max_map_row, 
        max_map_col
    );
    
    //Will listen to inputted keys to update to our player class
    key_handler key_input = new key_handler();
    
    public panel(){
        //does the sizing
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        //laerned the hard way that key inputs are only listened on focused components, rip
        //source: https://stackoverflow.com/questions/16530775/keylistener-not-working-for-jpanel
        this.setFocusable(true);
        //Listens to key input
        this.addKeyListener(key_input);
    }

    //temporary
    dummy d = new dummy(SCREEN_WIDTH, SCREEN_HEIGHT, TILE_SIZE, max_map_col, max_map_row);

    //COLLISION TEST
    void collisionCheck(){
        //Checks if the dummy is colliding with map border
        int halfScale = (DEF_DIMENSION * SCALE ) / 2;
        System.out.println(d.getDelta_x() + " " + d.isColliding_left());
        int d_topHitbox = d.getY_pos() - halfScale + d.getDelta_y();
        int d_downHitBox = d.getY_pos() + halfScale + d.getDelta_y();
        int d_rightHitbox = d.getX_pos() + halfScale + d.getDelta_x();
        int d_leftHitbox = d.getX_pos() - halfScale + d.getDelta_x();

        boolean isColliding_r = false;
        boolean isColliding_l = false;
        boolean isColliding_t = false;
        boolean isColliding_d = false;

	    isColliding_r = (d_rightHitbox > EAST_MAP_BOUNDARY);
	    isColliding_l = (d_leftHitbox < WEST_MAP_BOUNDARY);
	    isColliding_d = (d_downHitBox > SOUTH_MAP_BOUNDARY);
	    isColliding_t = (d_topHitbox < NORTH_MAP_BOUNDARY);

        int x_diff = 0, y_diff = 0;

	    int[][] map_tiles = map.getMap_tiles();
        for(int i = 0;i < 50;i++){
            for(int j = 0;j < 50;j++){
                if(map_tiles[i][j] == 4){
                    int tile_xpos = ((j + 1) * DEF_DIMENSION * SCALE) - halfScale;
                    int tile_ypos = ((i + 1) * DEF_DIMENSION * SCALE) - halfScale;
                    boolean isSameRow = d_topHitbox < tile_ypos + (halfScale) && d_downHitBox > tile_ypos - ((DEF_DIMENSION/2) * SCALE);
                    boolean isSameCol = d_leftHitbox < tile_xpos + (halfScale) && d_rightHitbox > tile_xpos - ((DEF_DIMENSION/2) * SCALE) ;

                    if(!isColliding_l && (d_leftHitbox <= tile_xpos + halfScale  && isSameRow && d_leftHitbox >= tile_xpos - halfScale)){
	                    isColliding_l = true;
                    }
                    if(!isColliding_r && (d_rightHitbox >= tile_xpos - halfScale  && isSameRow && d_rightHitbox <= tile_xpos + halfScale)){
	                    isColliding_r = true;

                    }
                    if(!isColliding_t &&  (d_topHitbox  <= tile_ypos + halfScale  && isSameCol && d_topHitbox >= tile_ypos - halfScale)){
	                    isColliding_t = true;
                    }
                    if(!isColliding_d && (d_downHitBox >= tile_ypos - halfScale && isSameCol && d_downHitBox <= tile_ypos + halfScale)){
                        isColliding_d = true;
                    }


                }
            }
        }


        d.setColliding_left(isColliding_l);
        d.setColliding_down(isColliding_d);
        d.setColliding_top(isColliding_t);
        d.setColliding_right(isColliding_r);

    }


    //this will listen to the timer, and I think the Timer class creates a thread?, maybe that's why we need to listen to it?
    //source: https://www.reddit.com/r/javahelp/comments/6d5rr4/threads_or_timer_for_java_game/
    private final ActionListener timer_listener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e){
            //we pass our key handler so that our dummy can check which keys are pressed


            d.calculate_next_position(key_input);
            collisionCheck();
            d.update_position();
            //repaint calls the paintComponent method again, so you can imagine, it redraws everything on the screen
            //basically updating what is displayed
            repaint();
        }
    };

    public void start_clock(){
        Timer timer = new Timer(10, timer_listener);
        timer.start();
    }
    
    //JPanel class has a paintComponent method, not overriding it only displays the panel
    //If we want to "draw" stuff on it, we override the method, call the super method and implement our own functionalities
    //Source: https://stackoverflow.com/questions/5446396/concerns-about-the-function-of-jpanel-paintcomponent
    @Override
    public void paintComponent(Graphics g){
        //info about Graphics class: https://docs.oracle.com/javase/8/docs/api/java/awt/Graphics.html#:~:text=The%20Graphics%20class%20is%20the,rendering%20operations%20that%20Java%20supports.
        super.paintComponent(g);
        map.display_tiles(g, TILE_SIZE, max_map_row, max_map_col, d, SCREEN_HEIGHT, SCREEN_WIDTH);
        d.display_dummy(g, TILE_SIZE, SCREEN_WIDTH, SCREEN_HEIGHT);
    }
}
