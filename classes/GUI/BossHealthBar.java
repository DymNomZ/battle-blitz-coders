package classes.GUI;

import classes.entities.Enemy;
import classes.sprites.GUISprites;
import src.GamePanel;

import java.awt.*;

public class BossHealthBar{
    static int x = (GamePanel.SCREEN_WIDTH / 2) - 200,y = GamePanel.SCREEN_HEIGHT - 200;
    static int display_health_max = 287;
    static int current_display_health = display_health_max;

    public static void displayHealthBar(Graphics g){

        g.drawImage(GUISprites.BossHealthBar.boss_health_bar, x, y, 400, 52, null);
        g.drawImage(GUISprites.BossHealthBar.boss_health_bar_amount_display, x + 56, y + 14, current_display_health, 25, null);
        g.drawImage(GUISprites.BossHealthBar.boss_portrait, x + 20, y-2, 56, 53, null);

    }
    public static void update_display_health_bar(double percent){
        current_display_health = (int) Math.round(display_health_max * percent);
        if(current_display_health < 0)current_display_health = 0;
    }
}
