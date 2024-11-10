package classes.GUI;

import classes.sprites.GUISprites;
import java.awt.Graphics;
import src.GamePanel;

public class Hotbar {
        
    public static void displayHotbar(Graphics g){
        int draw_start = 448;
		for(int i = 0; i < 5; i++){
            g.drawImage(GUISprites.Hotbar.UNSELECTED, draw_start, GamePanel.SCREEN_HEIGHT - 100, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
			draw_start += (GamePanel.TILE_SIZE + (GamePanel.TILE_SIZE / 4));
		}
    }
}
