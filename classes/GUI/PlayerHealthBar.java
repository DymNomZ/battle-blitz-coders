package classes.GUI;

import classes.sprites.GUISprites;
import java.awt.*;

public class PlayerHealthBar{
	static int x = 30,y = 24;
	static int display_health_max = 287;
	static int current_display_health = display_health_max;

	public static void displayHealthBar(Graphics g){
		g.drawImage(GUISprites.PlayerHealthBar.player_health_bar, x, y, 357, 52, null);
		g.drawImage(GUISprites.PlayerHealthBar.player_health_bar_amount_display,54 + x,5 + y,current_display_health,25,null);
		g.drawImage(GUISprites.PlayerHealthBar.player_health_bar_heart, 16 + x, y - 4, 56, 53, null);
	}
	public static void update_display_health_bar(double percent){
		current_display_health = (int) Math.round(display_health_max * percent);
		if(current_display_health < 0)current_display_health = 0;
	}
}
