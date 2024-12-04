package src;

import classes.GUI.General;
import classes.dialogues.Dialogues;
import classes.sprites.GUISprites;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public abstract class Utils {

    public static String[] doors_closed = {
        "stone_wall_door_left_closed.png",
        "stone_wall_door_right_closed.png",
        "black_stone_door_top_left.png",
        "black_stone_door_top_right.png",
        "black_stone_door_bottom_left.png",
        "black_stone_door_bottom_right.png",
    };

    public static String[] doors_opened = {
        "stone_wall_door_left_open.png",
        "stone_wall_door_right_open.png",
        "black_stone_door_top_left_open.png",
        "black_stone_door_top_right_open.png",
        "black_stone_door_bottom_left_open.png",
        "black_stone_door_bottom_right_open.png",
    };

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

    public static boolean doorChecker(String filename){

        for(String door : doors_closed){
            if(filename.equals(door)) return true;
        }

        return false;
    }

    public static void resetGame(){
        General.Panels.MAIN.remove(0);
        ((GamePanel)General.Panels.GAME).main_thread.interrupt();
        General.Panels.GAME = null;
        General.Panels.MAIN.add(General.Panels.HOME, 0);
        General.Buttons.START.setIcon(new ImageIcon(
                GUISprites.Buttons.START_U.getScaledInstance(
                        General.DEF_BTN_DIMENSIONS[0], General.DEF_BTN_DIMENSIONS[1], Image.SCALE_SMOOTH)
                )
        );
        System.gc();
    }
}
