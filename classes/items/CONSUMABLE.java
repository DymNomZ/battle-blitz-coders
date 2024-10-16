package classes.items;

public abstract class CONSUMABLE extends ITEM 
{
    //TO implement
    /*
            why healing and not item_type variable? -Raymond 
            good question, i think i put this in the wrong constructor. - FV
    */
    
    public CONSUMABLE (String name, int quantity, String item_type, int id) //made constructor since it kept making an error in CONSUMABLE_HEALING - FV
    {
        super(name, quantity, item_type, id);   
        
    }

    
}
