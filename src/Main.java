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

        JTextArea controls_display = new JTextArea("W A S D - Movement Controls\nShift - Increase Movement Speed\nN - Spawn Random Enemy\nH - Kill Random Enemy",3,3);
        controls_display.setBounds(10, 10, 200, 70);
        controls_display.setEditable(false);
        main_panel.add(controls_display);

        main_window.add(main_panel);
        main_window.pack();
        main_window.setLocationRelativeTo(null);
        main_window.setVisible(true);
        
        // main_panel.start_clock();
        main_panel.start_main_thread();
    }
}
