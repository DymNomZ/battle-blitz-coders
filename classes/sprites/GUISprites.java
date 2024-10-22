package classes.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GUISprites {
    
    public static class Hotbar {

        public static BufferedImage UNSELECTED;
        public static BufferedImage SELECTED;
    
        static{
            try{
                UNSELECTED = ImageIO.read(Hotbar.class.getResourceAsStream("../../assets/sprites/GUI/hotbar_slot_unselected.png"));
                SELECTED = ImageIO.read(Hotbar.class.getResourceAsStream("../../assets/sprites/GUI/hotbar_slot_selected.png"));
            }catch (IOException e){
                System.out.println("Error loading hotbar sprites");
            }
        }
    }
}
