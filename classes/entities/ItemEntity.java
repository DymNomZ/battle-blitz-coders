package classes.entities;

import classes.items.Item;
import classes.items.Melee;
import classes.items.Ranged;

public class ItemEntity extends PanelEntity {
    
    Item item;

    public ItemEntity(int x, int y, int kind){

        super(x, y, 'o');
        checkKind(kind);
        this.buffer = item.getSprite();
    }

    private void checkKind(int kind){
        switch(kind){
            case 1 -> item = new Melee().getRandom();
            case 2 -> item = new Ranged().getRandom();
        }
        System.out.println("Item: " + item.getName());
    }
}

