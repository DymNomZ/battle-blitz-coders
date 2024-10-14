package classes.items;

public abstract class CONSUMABLE extends ITEM 
{
    //TO implement
    public CONSUMABLE (String name, int quantity, String item_type, int id) //made constructor since it kept making an error in CONSUMABLE_HEALING - FV
    {
        super(name, quantity, "HEALING", id);
    }

    
}
