package classes.entities;

import java.awt.image.BufferedImage;

/*  Super class for all entities
 *  This class differs from MapEntity by its JPanel specific methods and data
 *                                                                    - Lil Z
 */
public abstract class PanelEntity {
    public int x, y, width, height;
    BufferedImage buffer;
    public PanelEntity(){}
    public PanelEntity(int x, int y, int width, int height, BufferedImage buffer){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buffer = buffer;
    }



    public void updatePosition() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented");
    }

    // returns int, but i actually dont know why... i just feel like it must be int
    // This function is callback for entity collision to be implemented
    //                                                                 - Lil Z
    public int entityCollision(PanelEntity e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
