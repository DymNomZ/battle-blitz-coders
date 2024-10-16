package classes.items;

public abstract class STAT_MODIFIERS extends ITEM{
    //TO IMPLEMENT
    public STAT_MODIFIERS (String name, int quality, int id) {
        super(name, "Stat Modifier", quality, id);
    }

    //subclasses - FV

    public static class AtkBoost extends STAT_MODIFIERS{
        public AtkBoost() {
            super("Atk Boost", 3, 400);
        }   //what's the idea for this? thought stat mods would be actual items -Raymond
    }
}
