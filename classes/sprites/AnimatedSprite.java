package classes.sprites;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import src.Panel;

public class AnimatedSprite {
    private List<BufferedImage> sprites;
    private long interval;
    private long start_nano = 0;

    public AnimatedSprite(int nanoInterval, ArrayList<BufferedImage> sprites) {
        this.interval = nanoInterval * sprites.size();
        this.sprites = sprites;
    }

    public AnimatedSprite(int nanoInterval, List<String> filePaths) {
        sprites = new ArrayList<>();

        for (String s : filePaths) {
            sprites.add(Panel.asset_engine.loadSprite(s));
        }

        interval = nanoInterval * sprites.size();
    }

    public BufferedImage getSprite() {
        if (start_nano == 0) start_nano = System.nanoTime();

        return sprites.get((int)((System.nanoTime() / interval) % sprites.size()));
    }

    public void reset() {
        start_nano = 0;
    }
}
