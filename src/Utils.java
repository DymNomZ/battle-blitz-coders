package src;

import classes.dialogues.Dialogues;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;

public abstract class Utils {

    public static Font DOSBOX_font = null;

    public static void showError(String message) {
        System.err.println(message);
    }

    public static void loadFonts(){

        try {

            DOSBOX_font = Font.createFont(Font.TRUETYPE_FONT, new File(
                "assets/fonts/video_terminal_screen_regular.ttf"
                )
            );

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(DOSBOX_font);

            DOSBOX_font = DOSBOX_font.deriveFont(Font.PLAIN, Dialogues.FONT_SIZE);

        } catch (FontFormatException | IOException e) {
            System.out.println("Error loading fonts");
        }
        
    }
}
