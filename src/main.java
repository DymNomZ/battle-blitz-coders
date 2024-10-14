package src;
import javax.swing.JFrame;

public class main {
    public static void main(String[] args) {
        //#Dymes:
        //We create a JFrame instance, JFrame allows us to create a window
        JFrame main_window = new JFrame();
        //Here we use the constant included in the JFRame class
        //EXIT_ON_CLOSE which closes the window when we click the close button
        //Pretty convenient if you ask me
        main_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //I set it as false for now, I don't think we discussed about resizing the window
        main_window.setResizable(false);
        //We don't have a title yet so
        main_window.setTitle("2D Game Title");
        //check panel class
        panel main_panel = new panel();
        //We add our panel to the window because if not, it will only display the border
        main_window.add(main_panel);
        //ELI5: 
        //Imagine you have a box full of toys. You want to organize them neatly inside the box. 
        //It looks at all the toys (or components) inside the JFrame and arranges them in the best way possible so that they fit snugly without overlapping.
        //It's like making sure all your toys have their own special place in the box!
        //More details: https://stackoverflow.com/questions/22982295/what-does-pack-do
        main_window.pack();
        //null centers the window
        //source: https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        main_window.setLocationRelativeTo(null);
        //Self-explanatory
        main_window.setVisible(true);
        
        //testing
        main_panel.start_clock();
    }
}
