package classes.sprites.Sprite;

import java.awt.image.BufferedImage;

class NormalSprite extends Sprite {
    private final BufferedImage sprite_buffer;
    private BufferedImage displayed_sprite;

    NormalSprite(BufferedImage buffer) {
        sprite_buffer = buffer;
        displayed_sprite = buffer;
    }

    @Override
    public Sprite cropSprite(int top, int right, int bottom, int left) {
        int spriteWidth = Math.max(1, sprite_buffer.getWidth() - right - left);
        int spriteHeight = Math.max(1, sprite_buffer.getHeight() - bottom - top);

        displayed_sprite = sprite_buffer.getSubimage(left, top, spriteWidth, spriteHeight);
        return this;
    }

    @Override
    public BufferedImage getSprite() {
        return displayed_sprite;
    }
}
