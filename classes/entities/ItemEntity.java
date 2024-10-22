package classes.entities;

import classes.items.Item;
import classes.items.Melee;
import classes.items.Ranged;
import java.awt.image.BufferedImage;
import src.Panel;

public class ItemEntity extends PanelEntity {
    
    Item item;
    public boolean is_pickable = true;

    public ItemEntity(int key, int x, int y, int kind){

        super(x, y, key);
        checkKind(kind);
        this.buffer = item.getSprite();
    }

    public ItemEntity(int key, int x, int y, Item item, boolean is_pickable){

        super(x, y, key);
        this.item = item;
        this.is_pickable = is_pickable;
        this.buffer = item.getSprite();
    }

    public ItemEntity(int key, int x, int y, boolean is_pickable){

        super(x, y, key);
        this.is_pickable = is_pickable;
    }

    private void checkKind(int kind){
        switch(kind){
            case 1 -> item = new Melee().getRandom();
            case 2 -> item = new Ranged().getRandom();
        }
        System.out.println("Item: " + item.getName());
    }

    public Item getItem(){
        return item;
    }

    public void setSprite(BufferedImage buffer){
        this.buffer = buffer;
    }

    //temp touching function, ikik this guy will be deprecated soon XD - Dymes
    @Override
    public boolean checkIfTouching(Dummy_sus d1){
        
        int dx2 = d1.x + Panel.TILE_SIZE;
        int dy2 = d1.y + Panel.TILE_SIZE;
        int x2 = x + Panel.TILE_SIZE;
        int y2 = y + Panel.TILE_SIZE;

        // System.out.println("Item coords x: " + x + " y: " + y + " x2: " + x2 + " y2: " + y2);
        // System.out.println("D1 coords x: " + d1.x + " y: " + d1.y + " x2: " + dx2 + " y2: " + dy2);

        //Simple touching rectangle
        return !(d1.y > y2 || y > dy2) && !(d1.x > x2 || x > dx2);

    }

}

