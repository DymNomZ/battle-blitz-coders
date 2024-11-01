package classes.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import classes.sprites.Sprite.Sprite;
import interfaces.EntityCollidable;
import src.Panel;

/*  Super class for all entities
 *  This class differs from MapEntity by its JPanel specific methods and data
 *                                                                    - Lil Z
 */
public abstract class PanelEntity {
    /* Explanation for each variable:
     * - x and y are the position of the top-leftmost pixel of the panel
     *
     * - x_pos_center and y_pos_center as of now, no uses but can be used to hold the center
     *   point of any PanelEntity
     *
     *  - width is the number of pixels horizontally, height is the number of pixels vertically
     *  - deltaX and deltaY are used to change the x and y variables respectively
     *
     *  - buffer is used to store the sprite (might change in the future)
     *  - Set H
     */
    public int x, y, width, height;
    public int x_pos_center, y_pos_center;
    public int deltaX, deltaY;
    public int key;
    int speed;
    Sprite buffer;

    public PanelEntity() {
        x = 0;
        y = 0;
        width = Panel.TILE_SIZE;
        height = Panel.TILE_SIZE;
        buffer = Sprite.load("default");
    } // Added default kay muerror sa MapEntity -Ervin

    public PanelEntity(int x, int y, int width, int height, int key) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.key = key;
        this.buffer = Sprite.load("default");
    }

    public PanelEntity(int x, int y, int width, int height, int key, String spritePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.key = key;
        this.buffer = Sprite.load(spritePath);
    }

    public PanelEntity(int x, int y, int width, int height, int key, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.key = key;
        this.buffer = sprite;
    }

    public PanelEntity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buffer = Sprite.load("default");
    }

    public PanelEntity(int x, int y, int width, int height, String spritePath) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buffer = Sprite.load(spritePath);
    }

    public PanelEntity(int x, int y, int width, int height, Sprite sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buffer = sprite;
    }

    public PanelEntity(int width, int height) {
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
        this.buffer = Sprite.load("default");
    }

    //for item entity
    public PanelEntity(int x, int y, int key){
        this.x = x;
        this.y = y;
        this.width = Panel.TILE_SIZE;
        this.height = Panel.TILE_SIZE;
        this.key = key;
        this.buffer = Sprite.load("default");
    }

    //for NPCs
    public PanelEntity(int x, int y, String spritePath){
        this.x = x;
        this.y = y;
        this.width = Panel.TILE_SIZE;
        this.height = Panel.TILE_SIZE;
        this.buffer = Sprite.load(spritePath);
    }

    //for NPCs
    public PanelEntity(int x, int y, Sprite sprite){
        this.x = x;
        this.y = y;
        this.width = Panel.TILE_SIZE;
        this.height = Panel.TILE_SIZE;
        this.buffer = sprite;
    }

    public void move(int offsetX, int offsetY) {
        deltaX = offsetX;
        deltaY = offsetY;
    }
    public void updateCenterPosition(){
        x_pos_center = x + (width / 2);
        y_pos_center = y + (height / 2);
    }

    // move like a sigma male, disregarding all bondaries of this world
    public void moveAbsolute(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void display(Graphics g, CameraEntity cam) {
        g.drawImage(buffer.getSprite(), x - cam.x, y - cam.y, width, height, null);
    }

    //temp touching function, ikik this guy will be deprecated soon XD - Dymes
    //used in temporary attacking
    public boolean checkIfTouching(MapEntity d1){
        
        int dx = d1.x - Panel.TILE_SIZE;
        int dy = d1.y - Panel.TILE_SIZE;
        int dx2 = d1.x + (Panel.TILE_SIZE * 2);
        int dy2 = d1.y + (Panel.TILE_SIZE * 2);

        int x1 = x - Panel.TILE_SIZE;
        int y1 = y - Panel.TILE_SIZE;
        int x2 = x + (Panel.TILE_SIZE * 2);
        int y2 = y + (Panel.TILE_SIZE) * 2;
        
        return !(dy > y2 || y1 > dy2) && !(dx > x2 || x1 > dx2);

    }
    /*
    * Mainly handles the EntityCollidable interface with the passed panel entity
    * - Set h
    * */
    public void checkEntityCollision(PanelEntity other){
        int this_left_boundary = this.x;
        int this_top_boundary = this.y;
        int this_right_boundary = this.x + this.width;
        int this_bottom_boundary = this.y + this.height;

        int other_left_boundary = other.x;
        int other_top_boundary = other.y;
        int other_right_boundary = other.x + other.width;
        int other_bottom_boundary = other.y + other.height;

        boolean left_is_colliding =
                other_right_boundary > this_left_boundary &&
                other_right_boundary < this_right_boundary &&
                other_bottom_boundary > this_top_boundary &&
                other_top_boundary < this_bottom_boundary;

        boolean right_is_colliding =
                other_left_boundary < this_right_boundary &&
                other_left_boundary > this_left_boundary &&
                other_bottom_boundary > this_bottom_boundary &&
                other_top_boundary < this_bottom_boundary;

        boolean top_is_colliding =
                other_bottom_boundary < this_top_boundary &&
                other_bottom_boundary > this_bottom_boundary &&
                other_right_boundary > this_left_boundary &&
                other_left_boundary < this_right_boundary;

        boolean bottom_is_colliding =
                other_top_boundary < this_bottom_boundary &&
                other_top_boundary > this_top_boundary &&
                other_right_boundary > this_left_boundary &&
                other_left_boundary < this_right_boundary;

        if(this instanceof EntityCollidable){
            if(left_is_colliding){
	            ((EntityCollidable) this).onEntityLeftCollision(other);
                ((EntityCollidable) other).onEntityRightCollision(this);
            }
            if(right_is_colliding){
	            ((EntityCollidable) this).onEntityRightCollision(other);
                ((EntityCollidable) other).onEntityLeftCollision(this);
            }
            if(top_is_colliding){
	            ((EntityCollidable) this).onEntityTopCollision(other);
                ((EntityCollidable) other).onEntityBottomCollision(this);
            }
            if(bottom_is_colliding){
	            ((EntityCollidable) this).onEntityBottomCollision(other);
                ((EntityCollidable) other).onEntityTopCollision(this);
            }
            if(left_is_colliding || bottom_is_colliding || top_is_colliding || right_is_colliding){
	            ((EntityCollidable) this).onEntityCollision(other);
                ((EntityCollidable) other).onEntityCollision(this);
            }
        }



    }


    // returns int, but i actually dont know why... i just feel like it must be int
    // This function is callback for entity collision to be implemented
    // - Lil Z
    public int entityCollision(PanelEntity e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented");
    }


    /*
     * Calculates the distance of this object
     * and a given PanelEntity in pixel units - Set H
     *
     */
    public double calculateDistance(PanelEntity e){
        return Math.sqrt(Math.pow((this.x - e.x),2) + Math.pow((this.y - e.y),2));
    }


    /*
     * Calculates the angle relative to the x-axis a straight line from
     * this entity to given entity e would form.
     * The value might look wrong at first (i.e why it is negative or positive)
     * mostly because our 0,0 is at the top left - Set H
     *
     */
    public double calculateAngle(PanelEntity e) {
        double x_diff = e.x - x;
        double y_diff = e.y - y;
        double angle_radians = (Math.atan2(y_diff, x_diff));

        return angle_radians;
    }
    public double calculateAngle(int x, int y, int x2, int y2){
        double x_diff = x2 - x;
        double y_diff = y2 - y;
        double angle_radians = (Math.atan2(y_diff, x_diff));

        return angle_radians;
    }

    public void moveAtAngle(double angle_radians){

        deltaY = (int) Math.round(Math.sin(angle_radians) * speed);
        deltaX = (int) Math.round(Math.cos(angle_radians) * speed);
    }


}