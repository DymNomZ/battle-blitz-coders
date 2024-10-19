package classes.map;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile {

    public BufferedImage image = null;
    public String path, name;
    public int tile_type = 0;
    public boolean is_solid = false;

    public Tile(String path, String name, int tileType, boolean is_solid){
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            System.out.println("Error loading tile image");
        }
        this.tile_type = tileType;
        this.name = name;
        this.is_solid = is_solid;
    }

    //for loading
    public Tile(String name, BufferedImage image, int tileType, boolean is_solid){
        this.image = image;
        this.tile_type = tileType;
        this.name = name;
        this.is_solid = is_solid;
    }
}
