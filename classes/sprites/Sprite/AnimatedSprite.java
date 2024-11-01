package classes.sprites.Sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class AnimatedSprite extends Sprite {
    private final List<Sprite> sprites;
    private long interval;
    private long start_nano = 0;

    public AnimatedSprite(long nanoInterval, ArrayList<Sprite> sprites) {
        interval = nanoInterval;

        for (Sprite s : sprites) {
            if (!(s instanceof NormalSprite)) {
                throw new UnsupportedOperationException("Only Sprites created by Sprite.load(filename) are allowed... nigga");
            }
        }

        this.sprites = sprites;
    }

    public AnimatedSprite(long nanoInterval, String[] filePaths) {
        sprites = new ArrayList<>();

        for (String s : filePaths) {
            sprites.add(Sprite.load(s));
        }

        interval = nanoInterval;
    }

    public AnimatedSprite setInterval(long interval) {
        this.interval = Math.max(interval, 0);
        return this;
    }

    @Override
    public AnimatedSprite cropSprite(int top, int right, int bottom, int left) {
        for (Sprite s : sprites) {
            s.cropSprite(top, right, bottom, left);
        }
        return this;
    }

    @Override
    public BufferedImage getSprite() {
        if (start_nano == 0) start_nano = System.nanoTime();

        long ind = Math.max(System.nanoTime() - start_nano, 0);
        ind /= interval;

        return sprites.get((int)(ind % sprites.size())).getSprite();
    }

    public AnimatedSprite reset() {
        start_nano = 0;
        return this;
    }
}
