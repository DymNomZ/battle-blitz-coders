package classes.entities;

/*  Super class for all entities
 *  This class differs from MAP_ENTITY by its JPanel specific methods and data
 *                                                                    - Lil Z
 */
public abstract class PANEL_ENTITY {
    protected int x, y;

    public void update_position() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented");
    }

    // returns int, but i actually dont know why... i just feel like it must be int
    // This function is callback for entity collision to be implemented
    //                                                                 - Lil Z
    public int entityCollision(PANEL_ENTITY e) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not implemented");
    }
}
