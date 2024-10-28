package src;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        JFrame main_window = new JFrame();

        main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_window.setResizable(false);
        main_window.setTitle("2D Game Title");

        Panel main_panel = new Panel();
        main_panel.setLayout(null);

        JTextArea controls_display;
        controls_display = new JTextArea(
            """
                W A S D - Movement Controls
                Shift - Increase Movement Speed
                N - Spawn Random Enemy
                H - Kill Enemy within range (+- TILE_SIZE[64])
                P - Debug Print Hotbar Items
                Q - Drop Item
                1-5 Hotbar Slots
            """
            ,3,3);
            
        controls_display.setBounds(10, 10, 300, 120);
        controls_display.setEditable(false);
        main_panel.add(controls_display);

        main_window.add(main_panel);
        main_window.pack();
        main_window.setLocationRelativeTo(null);
        main_window.setVisible(true);
        
        main_panel.start_main_thread();
    }
}
