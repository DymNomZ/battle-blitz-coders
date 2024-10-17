package classes.items;

public abstract class Consumable extends Item 
{
    //TO implement
    /*
            why healing and not item_type variable? -Raymond 
            good question, i think i put this in the wrong constructor. - FV
    */
    
    public Consumable (String name, int quantity, String item_type, int id) //made constructor since it kept making an error in CONSUMABLE_HEALING - FV
    {
        super(name, quantity, item_type, id);   
        
    }

    
}
