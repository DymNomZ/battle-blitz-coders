package classes.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ItemSprites { 

    public static class Melee {

        public static BufferedImage BREADBOARD;
        public static BufferedImage KEYBOARD;
        public static BufferedImage NOKIA;
        public static BufferedImage PICKAXE;
        public static BufferedImage SOCK;
        public static BufferedImage BOOK;
        public static BufferedImage STICK;
    
        static{
    
            try {
                BREADBOARD = ImageIO.read(Melee.class.getResourceAsStream("../../assets/sprites/weapons_melee/breadboard.png"));
                KEYBOARD = ImageIO.read(Melee.class.getResourceAsStream("../../assets/sprites/weapons_melee/keyboard_1.png"));
                NOKIA = ImageIO.read(Melee.class.getResourceAsStream("../../assets/sprites/weapons_melee/nokia_1.png"));
                PICKAXE = ImageIO.read(Melee.class.getResourceAsStream("../../assets/sprites/weapons_melee/pickaxe_1.png"));
                SOCK = ImageIO.read(Melee.class.getResourceAsStream("../../assets/sprites/weapons_melee/sock_1.png"));
                BOOK = ImageIO.read(Melee.class.getResourceAsStream("../../assets/sprites/weapons_melee/SOCSCI_book_1.png"));
                STICK = ImageIO.read(Melee.class.getResourceAsStream("../../assets/sprites/weapons_melee/stick_1.png"));
            } catch (IOException e) {
                System.out.println("Error loading Melee sprites");
            }
        }
        
        
    }

    public static class Ranged {

        public static BufferedImage ALCOHOL_GUN;
        public static BufferedImage PENCIL_GUN;
        public static BufferedImage PEN_GUN;
        public static BufferedImage CALCULATOR;
        public static BufferedImage POKE_BALL;
        public static BufferedImage SAMSUNG;
        public static BufferedImage PAPER_AIRPLANE;
        public static BufferedImage EXPLOSIVE_MOUSE;

        static{

            try {
                ALCOHOL_GUN = ImageIO.read(Ranged.class.getResourceAsStream("../../assets/sprites/weapons_ranged/alcohol_gun_1.png"));
                PENCIL_GUN = ImageIO.read(Ranged.class.getResourceAsStream("../../assets/sprites/weapons_ranged/pencil_gun_1.png"));
                PEN_GUN = ImageIO.read(Ranged.class.getResourceAsStream("../../assets/sprites/weapons_ranged/pen_shotgun_1.png"));
                CALCULATOR = ImageIO.read(Ranged.class.getResourceAsStream("../../assets/sprites/weapons_ranged/calculator_1.png"));
                POKE_BALL = ImageIO.read(Ranged.class.getResourceAsStream("../../assets/sprites/weapons_ranged/poke_ball_1.png"));
                SAMSUNG = ImageIO.read(Ranged.class.getResourceAsStream("../../assets/sprites/weapons_ranged/samsung_1.png"));
                PAPER_AIRPLANE = ImageIO.read(Ranged.class.getResourceAsStream("../../assets/sprites/weapons_ranged/paper_airplane_1.png"));
                EXPLOSIVE_MOUSE = ImageIO.read(Ranged.class.getResourceAsStream("../../assets/sprites/weapons_ranged/explosive_mouse_1.png"));
            } catch (IOException e) {
                System.out.println("Error loading Weapon sprites");
            }

        }
    
    }

    public static class StatModifiers {

        public static BufferedImage COFFEE;
        public static BufferedImage FOOT_MOP;
        public static BufferedImage HEADPHONES;
        public static BufferedImage HOODIE;

        public StatModifiers(){
            super();

            try {
                COFFEE = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/stat_modifiers/coffee.png"));
                FOOT_MOP = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/stat_modifiers/foot_mop_1.png"));
                HEADPHONES = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/stat_modifiers/headphones_1.png"));
                HOODIE = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/stat_modifiers/hoodie.png"));
            } catch (IOException e) {
                System.out.println("Error loading Consumable Stat Modifiers sprites");
            }

        }
    
    }

    public static class ConsumableUtility {

        public static BufferedImage ENERGY_DRINK;
        public static BufferedImage ICE_CREAM;

        public ConsumableUtility(){
            super();

            try {
                ENERGY_DRINK = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/consumable_utility/energy_drink.png"));
                ICE_CREAM = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/consumable_utility/ice_cream.png"));
            } catch (IOException e) {
                System.out.println("Error loading Consumable Utility sprites");
            }

        }
    
    }

    public static class ConsumableHealing {

        public static BufferedImage BAND_AID;
        public static BufferedImage CHIPS;
        public static BufferedImage CUP_NOODLES;
        public static BufferedImage GRAB;
        public static BufferedImage HOTCAKE;
        public static BufferedImage PIZZA;

        public ConsumableHealing(){
            super();

            try {
                BAND_AID = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/consumable_healing/band_aid_1.png"));
                CHIPS = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/consumable_healing/chips_2.png"));
                CUP_NOODLES = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/consumable_healing/cup_noodles.png"));
                GRAB = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/consumable_healing/grab_1.png"));
                HOTCAKE = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/consumable_healing/hotcake_1.png"));
                PIZZA = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/consumable_healing/pizza_1.png"));
            } catch (IOException e) {
                System.out.println("Error loading Consumable Healing sprites");
            }

        }
    
    }
}
