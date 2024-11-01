package classes.items;

// I made a separate melee class for melee weapons, if unnecessary u can merge it- Ervin
import classes.sprites.ItemSprites;
import classes.sprites.Sprite.Sprite;

import java.awt.image.BufferedImage;
import java.util.Random;

public class Melee extends Weapons {

    public Melee(){
        super();
    }

    public Melee(String name, int quality, int id, int damage, Sprite sprite) {
        super(name, "MELEE", quality, id, damage, sprite);
    }

    /*
     * Copied FV's sample implementation for weapon subclass;
     * Stick class will be the basis;
     * Id starts at 300;
     */

    public Item getRandom(){
        int num = new Random().nextInt(1, 8);
        Item m = null;

        switch(num){
            case 1 -> m = new Stick();
            case 2 -> m = new Keyboard();
            case 3 -> m = new Book();
            case 4 -> m = new Breadboard();
            case 5 -> m = new Nokia();
            case 6 -> m = new Pickaxe();
            case 7 -> m = new Sock();
        }

        return m;
    }
    public static class Stick extends Melee {
        public Stick() {
            super("Stick", 1, 300, 5, ItemSprites.Melee.STICK);
        }
    }

    public static class Keyboard extends Melee {
        public Keyboard() {
            super("Keyboard", 1, 301, 15, ItemSprites.Melee.KEYBOARD);
        }
    }

    public static class Breadboard extends Melee {
        public Breadboard() {
            super("Breadboard", 1, 302, 30, ItemSprites.Melee.BREADBOARD);
        }
    }

    public static class Nokia extends Melee {
        public Nokia() {
            super("Nokia", 1, 303, 69, ItemSprites.Melee.NOKIA);
        }
    }

    public static class Sock extends Melee {
        public Sock() {
            super("Sock", 1, 304, 1, ItemSprites.Melee.SOCK);
        }
    }

    public static class Pickaxe extends Melee {
        public Pickaxe() {
            super("Pickaxe", 1, 305, 45, ItemSprites.Melee.PICKAXE);
        }
    }

    public static class Book extends Melee {
        public Book() {
            super("Book", 1, 306, 15, ItemSprites.Melee.BOOK);
        }
    }
}
