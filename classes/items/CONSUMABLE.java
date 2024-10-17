package classes.items;

public abstract class Consumable extends Item 
{
    //TO implement
    /*
            why healing and not item_type variable? -Raymond 
            good question, i think i put this in the wrong constructor. - FV
    */
    
<<<<<<< HEAD
    public Consumable (String name, int quantity, String item_type, int id) //made constructor since it kept making an error in CONSUMABLE_HEALING - FV
=======
    public CONSUMABLE (String name, int quantity, String item_type, int id) //made constructor since it kept making an error in CONSUMABLE_HEALING - FV
>>>>>>> fac42dfe06377e896ba9cdff40761a80fd8761bf
    {
        super(name, quantity, item_type, id);   
        
    }

    
}
