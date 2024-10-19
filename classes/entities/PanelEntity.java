package classes.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/*  Super class for all entities
 *  This class differs from MapEntity by its JPanel specific methods and data
 *                                                                    - Lil Z
 */
public abstract class PanelEntity {
    public int x, y, width, height;
    public int deltaX, deltaY;
    BufferedImage buffer;

    public PanelEntity() {
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

    public void move(int offsetX, int offsetY) {
        deltaX = offsetX;
        deltaY = offsetY;
    }

    // move like a sigma male, disregarding all bondaries of this world
    public void moveAbsolute(int x, int y) {
        this.x = x;
        this.y = y;
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