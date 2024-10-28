package classes.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
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
    BufferedImage buffer;

    public PanelEntity() {
        x = 0;
        y = 0;
        width = Panel.TILE_SIZE;
        height = Panel.TILE_SIZE;
        buffer = null;
    } // Added default kay muerror sa MapEntity -Ervin

    public PanelEntity(int x, int y, int width, int height, int key) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.key = key;
        this.buffer = null;
    }

    public PanelEntity(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buffer = null;
    }

    public PanelEntity(int x, int y, int width, int height, BufferedImage buffer) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buffer = buffer;
    }

    public PanelEntity(int width, int height) {
        this.x = 0;
        this.y = 0;
        this.width = width;
        this.height = height;
        this.buffer = null;
    }

    //for item entity
    public PanelEntity(int x, int y, int key){
        this.x = x;
        this.y = y;
        this.width = Panel.TILE_SIZE;
        this.height = Panel.TILE_SIZE;
        this.key = key;
        this.buffer = null;
    }

    //for NPCs
    public PanelEntity(int x, int y, BufferedImage buffer){
        this.x = x;
        this.y = y;
        this.width = Panel.TILE_SIZE;
        this.height = Panel.TILE_SIZE;
        this.buffer = buffer;
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

    protected boolean loadSprite(String fileName) {
        return false;
    }

    public void display(Graphics g, CameraEntity cam) {
        if (buffer != null) {
            g.drawImage(buffer, x - cam.x, y - cam.y, width, height, null);
        } else {
            g.drawRect(x - cam.x, y - cam.y, width, height);
            System.err.println("[Warning: PanelEntity] Displaying without image/sprite attached");
        }
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

}