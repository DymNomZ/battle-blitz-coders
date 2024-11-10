package classes.sprites;

import classes.Asset.Sprite.Sprite;

public class ItemSprites { 

    public static class Melee {

        public static Sprite BREADBOARD;
        public static Sprite KEYBOARD;
        public static Sprite NOKIA;
        public static Sprite PICKAXE;
        public static Sprite SOCK;
        public static Sprite BOOK;
        public static Sprite STICK;
    
        static{
            BREADBOARD = Sprite.load("sprites/weapons_melee/breadboard.png");
            KEYBOARD = Sprite.load("sprites/weapons_melee/keyboard_1.png");
            NOKIA = Sprite.load("sprites/weapons_melee/nokia_1.png");
            PICKAXE = Sprite.load("sprites/weapons_melee/pickaxe_1.png");
            SOCK = Sprite.load("sprites/weapons_melee/sock_1.png");
            BOOK = Sprite.load("sprites/weapons_melee/SOCSCI_book_1.png");
            STICK = Sprite.load("sprites/weapons_melee/stick_1.png");
        }
        
        
    }

    public static class Ranged {

        public static Sprite ALCOHOL_GUN;
        public static Sprite PENCIL_GUN;
        public static Sprite PEN_GUN;
        public static Sprite CALCULATOR;
        public static Sprite POKE_BALL;
        public static Sprite SAMSUNG;
        public static Sprite PAPER_AIRPLANE;
        public static Sprite EXPLOSIVE_MOUSE;

        static{
            
            ALCOHOL_GUN = Sprite.load("sprites/weapons_ranged/alcohol_gun_1.png");
            PENCIL_GUN = Sprite.load("sprites/weapons_ranged/pencil_gun_1.png");
            PEN_GUN = Sprite.load("sprites/weapons_ranged/pen_shotgun_1.png");
            CALCULATOR = Sprite.load("sprites/weapons_ranged/calculator_1.png");
            POKE_BALL = Sprite.load("sprites/weapons_ranged/poke_ball_1.png");
            SAMSUNG = Sprite.load("sprites/weapons_ranged/samsung_1.png");
            PAPER_AIRPLANE = Sprite.load("sprites/weapons_ranged/paper_airplane_1.png");
            EXPLOSIVE_MOUSE = Sprite.load("sprites/weapons_ranged/explosive_mouse_1.png");

        }
    
    }

    public static class StatModifiers {

        public static Sprite COFFEE;
        public static Sprite FOOT_MOP;
        public static Sprite HEADPHONES;
        public static Sprite HOODIE;

        static {
            
            COFFEE = Sprite.load("sprites/stat_modifiers/coffee.png");
            FOOT_MOP = Sprite.load("sprites/stat_modifiers/foot_mop_1.png");
            HEADPHONES = Sprite.load("sprites/stat_modifiers/headphones_1.png");
            HOODIE = Sprite.load("sprites/stat_modifiers/hoodie.png");

        }
    
    }

    public static class ConsumableUtility {

        public static Sprite ENERGY_DRINK;
        public static Sprite ICE_CREAM;

        static {
            
            ENERGY_DRINK = Sprite.load("sprites/consumable_utility/energy_drink.png");
            ICE_CREAM = Sprite.load("sprites/consumable_utility/ice_cream.png");

        }
    
    }

    public static class ConsumableHealing {

        public static Sprite BAND_AID;
        public static Sprite CHIPS;
        public static Sprite CUP_NOODLES;
        public static Sprite GRAB;
        public static Sprite HOTCAKE;
        public static Sprite PIZZA;

        static {

            BAND_AID = Sprite.load("sprites/consumable_healing/band_aid_1.png");
            CHIPS = Sprite.load("sprites/consumable_healing/chips_2.png");
            CUP_NOODLES = Sprite.load("sprites/consumable_healing/cup_noodles.png");
            GRAB = Sprite.load("sprites/consumable_healing/grab_1.png");
            HOTCAKE = Sprite.load("sprites/consumable_healing/hotcake_1.png");
            PIZZA = Sprite.load("sprites/consumable_healing/pizza_1.png");

        }
    
    }
}
