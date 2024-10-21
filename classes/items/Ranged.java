package classes.items;

// Anotha wan for weapons -Ervin

import classes.sprites.ItemSprites;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Ranged extends Weapons {
    private int fire_rate;
    private int bullet_spd;
    private double radius; // For ranged weapons that do splash dmg

    public Ranged() {
        super();
    }

    // Constructor for ranged weapons with one shooting direction
    public Ranged(String name, int quality, int id, int damage, int fire_rate, int bullet_spd, BufferedImage sprite) {
        super(name, "RANGED", quality, id, damage, sprite);
        this.fire_rate = fire_rate;
        this.bullet_spd = bullet_spd;
    }

    // Constructor for ranged weapons with splash damage
    public Ranged(String name, int quality, int id, int damage, int fire_rate, double radius, BufferedImage sprite) {
        super(name, "RANGED", quality, id, damage, sprite);
        this.fire_rate = fire_rate;
        this.radius = radius;
    }

    public Item getRandom(){
        int num = new Random().nextInt(1, 8);
        Item m = null;

        switch(num){
            case 1 -> m = new AlcoholGun();
            case 2 -> m = new PencilGun();
            case 3 -> m = new PenGun();
            case 4 -> m = new PokeBall();
            case 5 -> m = new Mouse();
            case 6 -> m = new Calculator();
            case 7 -> m = new PaperPlane();
            case 8 -> m = new Samsung();
        }

        return m;
    }

    // Getters
    public int getFire_rate() {
        return fire_rate;
    }

    public int getBullet_spd() {
        return bullet_spd;
    }

    public double getRadius() {
        return radius;
    }

    // Setters would be nice later if there are more power-ups lol but for now nah

    /*
     * Still referring to FV's sample implementation for weapon subclass;
     * Id starts at 400;
     * For fire rate, bullet speed, and radius, those are arbitrary values
     * System for it to be discussed maybe
     */

    // One direction ranged weapons
    public static class PencilGun extends Ranged {
        public PencilGun() {
            super("Pencil Gun", 1, 406, 5, 10, 15, ItemSprites.Ranged.PENCIL_GUN);
        }
    }

    public static class PenGun extends Ranged {
        public PenGun() {
            super("Pen Gun", 1, 400, 5, 10, 15, ItemSprites.Ranged.PEN_GUN);
        }
    }

    public static class AlcoholGun extends Ranged {
        public AlcoholGun() {
            super("Alcohol Gun", 1, 401, 20, 15, 25, ItemSprites.Ranged.ALCOHOL_GUN);
        }
    }

    public static class PaperPlane extends Ranged {
        public PaperPlane() {
            super("Paper Plane", 1, 402, 10, 5, 15, ItemSprites.Ranged.PAPER_AIRPLANE);
        }
    }

    // Splash dmg type ranged weapons
    public static class Mouse extends Ranged {
        public Mouse() {
            super("Mouse", 1, 403, 30, 10, 15.0, ItemSprites.Ranged.EXPLOSIVE_MOUSE);
        }
    }

    public static class Calculator extends Ranged {
        public Calculator() {
            super("Calculator", 1, 404, 10, 25, 5.0, ItemSprites.Ranged.CALCULATOR);
        }
    }

    // Copyright amirite
    public static class Samsung extends Ranged {
        public Samsung() {
            super("Samsung", 1, 405, 50, 5, 25.0, ItemSprites.Ranged.SAMSUNG);
        }
    }

    public static class PokeBall extends Ranged {
        public PokeBall() {
            super("Poke Ball", 1, 407, 50, 5, 5.0, ItemSprites.Ranged.POKE_BALL);
        }
    }
}
