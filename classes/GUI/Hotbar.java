package classes.GUI;

import classes.sprites.GUISprites;
import java.awt.Graphics;
import src.Panel;

public class Hotbar {
        
    public static void displayHotbar(Graphics g){
        int draw_start = 448;
		for(int i = 0; i < 5; i++){
            g.drawImage(GUISprites.Hotbar.UNSELECTED, draw_start, Panel.SCREEN_HEIGHT - 100, Panel.TILE_SIZE, Panel.TILE_SIZE, null);
			draw_start += (Panel.TILE_SIZE + (Panel.TILE_SIZE / 4));
		}
    }
}
