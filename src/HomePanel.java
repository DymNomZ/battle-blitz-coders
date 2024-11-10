package src;

import classes.GUI.General;
import classes.sprites.GUISprites;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class HomePanel extends JPanel {


    public HomePanel(){

        this.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(false);
        this.setLayout(null);

        this.add(General.Titles.GAME_TITLE);
        this.add(General.Miscellaneous.CIT_U_LOGO);
        this.add(General.Buttons.PLAY);

    }

    @Override
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);

        //display background image
        g.drawImage(GUISprites.Backgrounds.TITLE_SCREEN, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, null);
    }
}