package classes.items;

import classes.sprites.Sprite.Sprite;

public abstract class Consumable extends Item 
{
    //TO implement
    /*
            why healing and not item_type variable? -Raymond 
            good question, i think i put this in the wrong constructor. - FV
    */

    public Consumable(){
        super();
    }
    
    public Consumable (String name, int quantity, String item_type, int id, Sprite sprite) //made constructor since it kept making an error in CONSUMABLE_HEALING - FV
    {
        super(name, item_type, quantity, id, sprite);   
        
    }

    
}
