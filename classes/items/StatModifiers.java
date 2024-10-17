package classes.items;

public abstract class StatModifiers extends Item{
    //TO IMPLEMENT
    public StatModifiers (String name, int quality, int id) {
        super(name, "Stat Modifier", quality, id);
    }

    //subclasses - FV

    public static class AtkBoost extends StatModifiers{
        public AtkBoost() {
            super("Atk Boost", 3, 400);
        }   //what's the idea for this? thought stat mods would be actual items -Raymond
    }
}
