package classes.GUI;

import classes.sprites.GUISprites;
import java.awt.*;

public class EnemyHealthBar{
	static int display_health_max = 92;
	static int current_display_health = display_health_max;

	public static void displayHealthBar(Graphics g,int x, int y, int width){
		x = (x + width / 2) - (98/2);
		y -= 20;
		g.drawImage(GUISprites.EnemyHealthBar.enemy_health_bar, x, y, 98, 12, null);
		g.drawImage(GUISprites.EnemyHealthBar.enemy_health_bar_amount_display, x + 3, y + 3, current_display_health, 6, null);
	}
	public static void update_display_health_bar(double percent){
		current_display_health = (int) Math.round(display_health_max * percent);
		if(current_display_health < 0)current_display_health = 0;
	}
}
