package classes.map;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Tile {

    public BufferedImage image = null;
    public String path, name;
    public int tileType = 0;

    public Tile(String path, int tileType, String name){
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            System.out.println("Error loading tile image");
        }
        this.tileType = tileType;
        this.name = name;
    }

    public Tile(int tileType, String name, BufferedImage image){
        this.image = image;
        this.tileType = tileType;
        this.name = name;
    }
}
