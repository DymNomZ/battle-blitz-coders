package classes.sprites;

import java.awt.image.BufferedImage;
import java.util.List;

public class EntitySprite {
    public static final int DEFAULT_IDLE = 0;
    public static final int DEFAULT_MOVING_LEFT = 1;
    public static final int DEFAULT_MOVING_RIGHT = 2;

    private boolean is_right;
    private List<AnimatedSprite> sprites;
    private int state = DEFAULT_IDLE;

    public EntitySprite(List<AnimatedSprite> sprites, boolean isRightDirection) {
        is_right = isRightDirection;
        this.sprites = sprites;
    }

    public void toLeft() {
        is_right = false;
    }

    public void toRight() {
        is_right = true;
    }

    public BufferedImage getSprite(boolean isMoving) {
        return sprites.get(0).getSprite(); // incomplete
    }
}
