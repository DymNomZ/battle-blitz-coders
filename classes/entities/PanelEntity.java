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
    BufferedImage buffer;

    public PanelEntity() {
        x = 0;
        y = 0;
        width = Panel.TILE_SIZE;
        height = Panel.TILE_SIZE;
        buffer = null;
    } // Added default kay muerror sa MapEntity -Ervin

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
    public PanelEntity(int x, int y, char ignore){
        this.x = x;
        this.y = y;
        this.width = Panel.TILE_SIZE;
        this.height = Panel.TILE_SIZE;
        this.buffer = null;
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

    // returns int, but i actually dont know why... i just feel like it must be int
    // This function is callback for entity collision to be implemented
    // - Lil Z
    public int entityCollision(PanelEntity e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented");
    }
}