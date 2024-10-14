package classes.items;

public abstract class WEAPONS extends ITEM{
    //To implement
    int damage;
    public WEAPONS (String name, int quality, int id, int damage)  {
        super(name, "Weapon", quality, id);
        this.damage = damage;
    }

    public int getDamage() { //Did this since item superclass didn't have way to return damage - FV
        return damage;
    }

    // basic weapon - FV
    /*
        Weapon subclass sample. id 300 and so forth. - FV
     */
    public static class Stick extends WEAPONS {
        int damage;
        public Stick() {
            super("Stick", 1, 300, 5);
        } 
    }
}
