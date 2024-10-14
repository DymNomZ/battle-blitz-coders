package classes.entities;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import src.key_handler;

public class dummy {
    final public int MAX_X;
    final public int MAX_Y;
    final public int MIN_X = 0;
    final public int MIN_Y = 0;
    final public int TILE_SIZE;
    BufferedImage sprite;
    public int x_pos, y_pos, screen_x, screen_y, map_length, map_height;
    public int xx, yy;
    public int speed;

    boolean colliding_left;
    boolean colliding_right;
    boolean colliding_top;
    boolean colliding_down;

    private boolean cameraNotTouchingEdge(){
        return (x_pos > screen_x && x_pos < ((map_length * TILE_SIZE) - (screen_x + TILE_SIZE))
                && (y_pos > screen_y && y_pos < ((map_height * TILE_SIZE) - (screen_y + TILE_SIZE))));
    }


    public int getX_pos(){
        return x_pos;
    }

    public int getY_pos(){
        return y_pos;
    }

    public int getSpeed(){
        return speed;
    }

    public void setColliding_left(boolean colliding_left){
        this.colliding_left = colliding_left;
    }

    public void setColliding_right(boolean colliding_right){
        this.colliding_right = colliding_right;
    }

    public void setColliding_top(boolean colliding_top){
        this.colliding_top = colliding_top;
    }

    public void setColliding_down(boolean colliding_down){
        this.colliding_down = colliding_down;
    }

    public dummy(int x, int y, int TILE_SIZE, int map_length, int map_height){
        this.speed = 8;
        this.map_length = map_length;
        this.map_height = map_height;
        this.TILE_SIZE = TILE_SIZE;
        //FIXED IT! Jesus Christ, swapped scren_width and screen_height on panel.java
        x_pos = (map_height * TILE_SIZE) / 2;
        y_pos = (map_length * TILE_SIZE) / 2;
        //These variables will be where our sprite will be displayed, which will be at the center
        //We get this by subtracting the corods with half the tile size to offset the sprite to give the illusion that it is perfectly at the center
        //Cause you know how pixels are printed starting at the top most corner
        MAX_X = TILE_SIZE * map_length;
        MAX_Y = TILE_SIZE * map_height;

        colliding_left = false;
        colliding_right = false;
        colliding_top  = false;
        colliding_down = false;

        screen_x = x / 2;
        screen_y = y / 2;
        xx = screen_x;
        yy = screen_y;
        try { sprite = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/fidget_spinner.png"));
        } catch (IOException e) {
            System.out.println("Error reading dummy sprite");
        }
    }

    public void display_dummy(Graphics g, int TILE_SIZE, int SCREEN_WIDTH, int SCREEN_HEIGHT){
        if (cameraNotTouchingEdge()){
                xx = screen_x;
                yy = screen_y;
                //System.out.println("xx: " + xx + " yy: " + yy + " x pos: " + x_pos + " y pos: " + y_pos);
                g.drawImage(
                    sprite, 
                    (screen_x - (TILE_SIZE/2)), (screen_y - (TILE_SIZE/2)), 
                    TILE_SIZE, TILE_SIZE,
                    null)
                ;
            }else{
                //System.out.println("xx: " + xx + " yy: " + yy + " x pos: " + x_pos + " y pos: " + y_pos);
                if(yy < 0) yy = 0;
                else if(yy > 640) yy = SCREEN_HEIGHT;
                if(xx < 0) xx = 0;
                else if(xx > 1280) xx = SCREEN_WIDTH;
                g.drawImage(
                    sprite, 
                    (xx - (TILE_SIZE/2)), (yy - (TILE_SIZE/2)),
                    TILE_SIZE, TILE_SIZE, 
                    null
                );
            }
    }


    public void update_position(key_handler inputs){
        //check which key is pressed and add/subtract the corresponding value
        int delta_x = 0, delta_y = 0;//Delta means "change in"
        System.out.print("Current pos: x: " + x_pos + " y: " + y_pos + " | Screen pos: x: " + screen_x + " y: " + screen_y + ":");
        if(inputs.up_pressed || inputs.down_pressed || inputs.left_pressed || inputs.right_pressed){

            if(inputs.up_pressed)
                delta_y = -speed;
            if(inputs.down_pressed)
                delta_y = speed;
            if(inputs.up_pressed == inputs.down_pressed)
                delta_y = 0;

            if(inputs.left_pressed)
                delta_x = -speed;
            if(inputs.right_pressed)
                delta_x = speed;
            if(inputs.right_pressed == inputs.left_pressed){
                delta_x = 0;
            }

            if(colliding_left && inputs.left_pressed) delta_x = 0;
            if(colliding_right && inputs.right_pressed) delta_x = 0;
            if(colliding_top && inputs.up_pressed) delta_y = 0;
            if(colliding_down && inputs.down_pressed) delta_y = 0;

            if(!cameraNotTouchingEdge()){//SO if camera touching edge HAHHAAHAHAH
                yy += delta_y;
                xx += delta_x;
            }
            y_pos += delta_y;
            x_pos += delta_x;

//            if (cameraNotTouchingEdge()){
//                if(inputs.up_pressed) y_pos -= 10;
//                else if(inputs.down_pressed) y_pos += 10;
//                else if(inputs.left_pressed) x_pos -= 10;
//                else if(inputs.right_pressed) x_pos += 10;
//
//            } else {
//                if(inputs.up_pressed)yy -= 10;
//                else if(inputs.down_pressed) yy += 10;
//                else if(inputs.left_pressed) xx -= 10;
//                else if(inputs.right_pressed) xx += 10;
//                if(inputs.up_pressed) y_pos -= 10;
//                else if(inputs.down_pressed) y_pos += 10;
//                else if(inputs.left_pressed) x_pos -= 10;
//                else if(inputs.right_pressed) x_pos += 10;
//            }


        }
    }
    
}