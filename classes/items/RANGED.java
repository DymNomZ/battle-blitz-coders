package classes.items;

// Anotha wan for weapons -Ervin
public class RANGED extends WEAPONS {
    private int fireRate;
    private int bulletSpd;
    private double radius; // For ranged weapons that do splash dmg

    // Constructor for ranged weapons with one shooting direction
    public RANGED(String name, int quality, int id, int damage, int fireRate, int bulletSpd) {
        super(name, "RANGED", quality, id, damage);
        this.fireRate = fireRate;
        this.bulletSpd = bulletSpd;
    }

    // Constructor for ranged weapons with splash damage
    public RANGED(String name, int quality, int id, int damage, int fireRate, double radius) {
        super(name, "RANGED", quality, id, damage);
        this.fireRate = fireRate;
        this.radius = radius;
    }

    // Getters
    public int getFireRate() {
        return fireRate;
    }

    public int getBulletSpd() {
        return bulletSpd;
    }

    public double getRadius() {
        return radius;
    }

    // Setters would be nice later if there are more power-ups lol bur for now nah

    /*
     * Still referring to FV's sample implementation for weapon subclass;
     * Id starts at 400;
     * For fire rate, bullet speed, and radius, those are arbitrary values
     * System for it to be discussed maybe
     */

    // One direction ranged weapons
    public static class PenGun extends RANGED {
        public PenGun() {
            super("Pen Gun", 1, 400, 5, 10, 15);
        }
    }

    public static class AlcoholGun extends RANGED {
        public AlcoholGun() {
            super("Alcohol Gun", 1, 401, 20, 15, 25);
        }
    }

    public static class PaperPlane extends RANGED {
        public PaperPlane() {
            super("Paper Plane", 1, 402, 10, 5, 15);
        }
    }

    // Splash dmg type ranged weapons
    public static class Mouse extends RANGED {
        public Mouse() {
            super("Mouse", 1, 403, 30, 10, 15.0);
        }
    }

    public static class Calculator extends RANGED {
        public Calculator() {
            super("Calculator", 1, 404, 10, 25, 5.0);
        }
    }

    // Copyright amirite
    public static class Samsing extends RANGED {
        public Samsing() {
            super("Samsing", 1, 405, 50, 5, 25.0);
        }
    }
}
