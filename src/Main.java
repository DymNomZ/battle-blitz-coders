package src;
import classes.GUI.General;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame main_window = new JFrame();

        main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_window.setResizable(false);
        main_window.setTitle("Battle Blitz Coders Demo");

        General.Panels.MAIN.add(General.Panels.HOME, 0);

        main_window.add(General.Panels.MAIN);

        main_window.pack();
        main_window.setLocationRelativeTo(null);
        main_window.setVisible(true);
        
    }
}
