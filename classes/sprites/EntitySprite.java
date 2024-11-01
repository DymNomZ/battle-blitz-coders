package classes.sprites;

import classes.sprites.Sprite.AnimatedSprite;
import classes.sprites.Sprite.Sprite;

import java.awt.image.BufferedImage;
import java.util.List;

public class EntitySprite extends Sprite {
    public static final int DEFAULT_IDLE = 0;
    public static final int DEFAULT_MOVING_LEFT = 1;
    public static final int DEFAULT_MOVING_RIGHT = 2;

    private boolean is_right = false;
    private final List<AnimatedSprite> sprites;
    private int state = DEFAULT_IDLE;
    private boolean isMoving;

    public EntitySprite(List<AnimatedSprite> sprites) {
        this.sprites = sprites;
    }

    public EntitySprite toLeft() {
        is_right = false;
        return this;
    }

    public EntitySprite toRight() {
        is_right = true;
        return this;
    }

    public EntitySprite setMoving(boolean isMoving) {
        this.isMoving = isMoving;
        return this;
    }

    @Override
    public EntitySprite cropSprite(int top, int right, int bottom, int left) {
        for (AnimatedSprite s : sprites) {
            s.cropSprite(top, right, bottom, left);
        }
        return this;
    }

    @Override
    public BufferedImage getSprite() {
        if (isMoving) {
            if (state == DEFAULT_IDLE || (is_right && state == DEFAULT_MOVING_LEFT) || (!is_right && state == DEFAULT_MOVING_RIGHT)) {
                sprites.get(state).reset();
            }
            state = is_right ? DEFAULT_MOVING_RIGHT : DEFAULT_MOVING_LEFT;
        } else if (state != DEFAULT_IDLE) {
            sprites.get(state).reset();
            state = DEFAULT_IDLE;
        }

        return sprites.get(state).getSprite();
    }
}
