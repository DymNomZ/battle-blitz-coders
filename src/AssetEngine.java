package src;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class AssetEngine {
    private HashMap<String, BufferedImage> loaded_assets = new HashMap<>();

    public BufferedImage loadSprite(String fileName) {
        BufferedImage ret = loaded_assets.get(fileName);

        if (ret != null) return ret;

        try{
            ret = ImageIO.read(getClass().getResourceAsStream("../assets/" + fileName));
            loaded_assets.put(fileName, ret);
		}catch(IOException e){
			System.err.println("Asset no found at assets/" + fileName);
		}

        return ret;
    }

    public void reset() {
        loaded_assets = new HashMap<>();
    }
}
