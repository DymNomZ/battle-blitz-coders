package classes.items;

// I made a separate melee class for melee weapons, if unnecessary u can merge it- Ervin
public class Melee extends Weapons {
    public Melee(String name, int quality, int id, int damage) {
        super(name, "MELEE", quality, id, damage);
    }

    /*
     * Copied FV's sample implementation for weapon subclass;
     * Stick class will be the basis;
     * Id starts at 300;
     */
    public static class Stick extends Melee {
        public Stick() {
            super("Stick", 1, 300, 5);
        }
    }

    public static class Keyboard extends Melee {
        public Keyboard() {
            super("Keyboard", 1, 301, 15);
        }
    }

    public static class Breadboard extends Melee {
        public Breadboard() {
            super("Breadboard", 1, 302, 30);
        }
    }

    public static class Nokia extends Melee {
        public Nokia() {
            super("Nokia", 1, 303, 69);
        }
    }

    public static class Sock extends Melee {
        public Sock() {
            super("Sock", 1, 304, 1);
        }
    }

    public static class Pickaxe extends Melee {
        public Pickaxe() {
            super("Pickaxe", 1, 305, 45);
        }
    }
}
