package classes.sprites.Sprite;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Objects;

public abstract class Sprite {
    private static final HashMap<String, WeakReference<Sprite>> loaded_assets = new HashMap<>();

    public static Sprite load(String fileName) {
        Sprite ret;

        if (loaded_assets.containsKey(fileName)) {
            ret = loaded_assets.get(fileName).get();
            if (ret != null) return ret;
        }

        try{
            if (Objects.equals(fileName, "default")) {
                ret = new NormalSprite(ImageIO.read(Sprite.class.getResourceAsStream("../../../assets/map_tiles/void.png")));
            } else {
                InputStream fp = Sprite.class.getResourceAsStream("../../../assets/" + fileName);
                if (fp == null) throw new IOException();
                ret = new NormalSprite(ImageIO.read(fp));
            }
            loaded_assets.put(fileName, new WeakReference<Sprite>(ret));
        }catch(IOException e){
            System.err.println("Asset not found at assets/" + fileName);
            ret = load("default");
        }

        return ret;
    }

    public static void resetEngine() {
        System.gc();
    }

    public abstract Sprite cropSprite(int top, int right, int bottom, int left);
    public abstract BufferedImage getSprite();
}
