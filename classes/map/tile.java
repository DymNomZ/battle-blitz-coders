package classes.map;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class tile {

    public BufferedImage texture;
    //kind is for souting purposes
    String kind;

    public tile(String path, String kind){
        this.kind = kind;
        try {
            //ImageIO class offers methods to read and write files
            //getClass() helps Java locate the class' relative position, this is good practice in the long run
            //Docs: https://www.javatpoint.com/java-imageio-class
            //Also more info about getResourceAsStream: https://stackoverflow.com/questions/10247161/when-to-use-getresourceasstream-method
            texture = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            System.out.println("Error reading tile texture of: " + kind);
        }
    }
}
