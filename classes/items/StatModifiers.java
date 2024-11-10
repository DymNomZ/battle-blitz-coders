package classes.items;
import classes.sprites.ItemSprites;
import classes.Asset.Sprite.Sprite;

public abstract class StatModifiers extends Item {
    // TO IMPLEMENT

    public StatModifiers(){
        super();
    }

    public StatModifiers(String name, int quality, int id, Sprite sprite) {
        super(name, "Stat Modifier", quality, id, sprite);
    }

    // subclasses - FV
    public static class Coffee extends StatModifiers {
        public Coffee() {
            super("Coffee", 1, 800, ItemSprites.StatModifiers.COFFEE);
        }
    }

    public static class FootMop extends StatModifiers {
        public FootMop() {
            super("Foot Mop", 1, 801, ItemSprites.StatModifiers.FOOT_MOP);
        }
    }

    public static class Headphones extends StatModifiers {
        public Headphones() {
            super("Headphones", 1, 802, ItemSprites.StatModifiers.HEADPHONES);
        }
    }

    public static class Hoodie extends StatModifiers {
        public Hoodie() {
            super("Hoodie", 1, 803, ItemSprites.StatModifiers.HOODIE);
        }
    }
}
