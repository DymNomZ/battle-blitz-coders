package classes.items;

// I made a separate melee class for melee weapons, if unnecessary u can merge it- Ervin
public class MELEE extends WEAPONS {
    public MELEE(String name, int quality, int id, int damage) {
        super(name, "MELEE", quality, id, damage);
    }

    /*
     * Copied FV's sample implementation for weapon subclass;
     * Stick class will be the basis;
     * Id starts at 300;
     */
    public static class Stick extends MELEE {
        public Stick() {
            super("Stick", 1, 300, 5);
        }
    }

    public static class Keyboard extends MELEE {
        public Keyboard() {
            super("Keyboard", 1, 301, 15);
        }
    }

    public static class Breadboard extends MELEE {
        public Breadboard() {
            super("Breadboard", 1, 302, 30);
        }
    }

    public static class Nokia extends MELEE {
        public Nokia() {
            super("Nokia", 1, 303, 69);
        }
    }

    public static class Sock extends MELEE {
        public Sock() {
            super("Sock", 1, 304, 1);
        }
    }

    public static class Pickaxe extends MELEE {
        public Pickaxe() {
            super("Pickaxe", 1, 305, 45);
        }
    }
}
