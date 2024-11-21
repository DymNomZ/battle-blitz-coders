package src;

import classes.GUI.General;
import classes.entities.Player;
import classes.sprites.GUISprites;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;

public class CharSelectionPanel extends JPanel implements Runnable {

    Thread main_thread;
    Player DYM, SETH, ZILLION, RAYMOND;

    public CharSelectionPanel(){
        this.setPreferredSize(new Dimension(GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(false);
        this.setLayout(null);

        this.add(General.Buttons.START);
        this.add(General.Buttons.DYMES);
        this.add(General.Buttons.SETH);
        this.add(General.Buttons.ZILLION);
        this.add(General.Buttons.RAYMOND);

        displayCharacters();
        
    }

    private void displayCharacters(){

        DYM = new Player("Dymes", 1000, 370, 550, 96, 60, null);
        SETH = new Player("Seth", 1000, 970, 550, 96, 60, null);
        ZILLION = new Player("Zillion", 1000, 1570, 550, 96, 60, null);
        RAYMOND = new Player("Raymond", 1000, 2170, 550, 96, 60, null);
    }

    public void start_main_thread(){
        main_thread = new Thread(this);
        main_thread.start();
    }

    @Override
    public void run() {
        long nanoInterval = 16666667; // 60 fps
        long lastEntityCheck = 0;
        long last_system_time = System.nanoTime();
        long current_system_time;
        long delta = 0;

        while (main_thread != null) {

            last_system_time = System.nanoTime() - (System.nanoTime() - last_system_time);
            repaint();

            if (lastEntityCheck + 1000000000 < last_system_time) {
                lastEntityCheck = System.nanoTime();
            }


            current_system_time = System.nanoTime();
            delta = Math.max(nanoInterval - current_system_time + last_system_time, 0);
            last_system_time = current_system_time;

            try {
                Thread.sleep(delta / 1000000, (int)(delta % 1000000));
            } catch (InterruptedException e) {throw new RuntimeException();}
        }
    }

    @Override
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);

        //display background image
        //g.drawImage(GUISprites.Backgrounds.SELECT_CHARACTER_1, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, null);
        g.drawImage(GUISprites.Backgrounds.SELECT_CHARACTER_2, 0, 0, GamePanel.SCREEN_WIDTH, GamePanel.SCREEN_HEIGHT, null);

        DYM.display(g);
        SETH.display(g);
        ZILLION.display(g);
        RAYMOND.display(g);
    }
    
}
