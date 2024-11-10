package classes.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class GUISprites {
    
    public static class Hotbar {

        public static BufferedImage UNSELECTED;
        public static BufferedImage SELECTED;
    
        static{
            try{
                UNSELECTED = ImageIO.read(Hotbar.class.getResourceAsStream("../../assets/sprites/player_GUI/hotbar_slot_unselected.png"));
                SELECTED = ImageIO.read(Hotbar.class.getResourceAsStream("../../assets/sprites/player_GUI/hotbar_slot_selected.png"));
            }catch (IOException e){
                System.out.println("Error loading hotbar sprites");
            }
        }
    }

    public static class PlayerHealthBar{
        public static BufferedImage player_health_bar;
        public static BufferedImage player_health_bar_heart;
        public static BufferedImage player_health_bar_amount_display;
        static{
            try{
                player_health_bar = ImageIO.read(Hotbar.class.getResourceAsStream("../../assets/sprites/player_GUI/player_health_bar.png"));
            }catch(IOException e){
                System.out.println("Error loading player health bar");
            }
            try{
                player_health_bar_heart = ImageIO.read(Hotbar.class.getResourceAsStream("../../assets/sprites/player_GUI/player_health_bar_heart.png"));
            }catch(IOException e){
                System.out.println("Error loading player health bar heart");
            }
            try{
                player_health_bar_amount_display = ImageIO.read(Hotbar.class.getResourceAsStream("../../assets/sprites/player_GUI/player_health_bar_amount_display.png"));
            }catch(IOException e){
                System.out.println("Error loading player health bar amount display");
            }
        }
    }
    public static class EnemyHealthBar{
        public static BufferedImage enemy_health_bar;
        public static BufferedImage enemy_health_bar_amount_display;
        static{
            try{
                enemy_health_bar = ImageIO.read(Hotbar.class.getResourceAsStream("../../assets/sprites/enemy_GUI/enemy_health_bar.png"));
            }catch(IOException e){
                System.out.println("Error loading enemy health bar");
            }
            try{
                enemy_health_bar_amount_display = ImageIO.read(Hotbar.class.getResourceAsStream("../../assets/sprites/enemy_GUI/enemy_health_bar_amount_display.png"));
            }catch(IOException e){
                System.out.println("Error loading enemy health bar amount display");
            }

        }
    }
    public static class Backgrounds {

        public static BufferedImage TITLE_SCREEN;
        public static BufferedImage SELECT_CHARACTER;
        public static BufferedImage CREDITS;

        static {
            try {
                
                TITLE_SCREEN = ImageIO.read(Backgrounds.class.getResourceAsStream("../../assets/GUI/backgrounds/GLE_sunset.png"));
                SELECT_CHARACTER = ImageIO.read(Backgrounds.class.getResourceAsStream("../../assets/GUI/backgrounds/temp_title_screen_background.jpg"));
                CREDITS = ImageIO.read(Backgrounds.class.getResourceAsStream("../../assets/GUI/backgrounds/temp_title_screen_background.jpg"));

            } catch (IOException e) {
                System.out.println("Error loading wallpapers");
            }
        }
    }
    public static class Titles {

        public static BufferedImage GAME_TITLE, SELECT_CHARACTER, GAME_OVER;

        static{
            try {

                GAME_TITLE = ImageIO.read(Titles.class.getResourceAsStream("../../assets/GUI/titles/battle_blitz_coders.png"));
                SELECT_CHARACTER = ImageIO.read(Titles.class.getResourceAsStream("../../assets/GUI/titles/temp_game_title.png"));
                GAME_OVER = ImageIO.read(Titles.class.getResourceAsStream("../../assets/GUI/titles/temp_game_title.png"));
                
            } catch (IOException e) {
                System.out.println("Error loading game titles");
            }
        }
    }
    public static class Buttons {

        public static BufferedImage PLAY_U, PLAY_S, START_U, START_S; //PAUSE and other future butons
        public static BufferedImage DYMES_U, DYMES_S, SETH_U, SETH_S, ZILLION_U, ZILLION_S, RAYMOND_U, RAYMOND_S;

        static{
            try {
                
                PLAY_U = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/play_unselected.png"));
                PLAY_S = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/play_selected.png"));
                START_U = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/start_unselected.png"));
                START_S = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/start_selected.png"));

                DYMES_U = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/dymes_unselected.png"));
                DYMES_S = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/dymes_selected.png"));
                SETH_U = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/seth_unselected.png"));
                SETH_S = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/seth_selected.png"));
                ZILLION_U = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/zillion_unselected.png"));
                ZILLION_S = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/zillion_selected.png"));
                RAYMOND_U = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/raymond_unselected.png"));
                RAYMOND_S = ImageIO.read(Buttons.class.getResourceAsStream("../../assets/GUI/buttons/raymond_selected.png"));

            } catch (IOException e) {
                System.out.println("Error loading button GUI sprites");
            }
        }
    }

    public static class Miscellaneous {

        public static BufferedImage CIT_U_LOGO;

        static{
            try {
                CIT_U_LOGO = ImageIO.read(Miscellaneous.class.getResourceAsStream("../../assets/GUI/miscellaneous/CIT-U_LOGO.png"));
            } catch (IOException e) {
            }
        }
    }
}
