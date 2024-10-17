package src;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {

        JFrame main_window = new JFrame();

        main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_window.setResizable(false);
        main_window.setTitle("2D Game Title");

        Panel main_panel = new Panel();

        main_window.add(main_panel);
        main_window.pack();
        main_window.setLocationRelativeTo(null);
        main_window.setVisible(true);
        
        main_panel.start_clock();
    }
}
