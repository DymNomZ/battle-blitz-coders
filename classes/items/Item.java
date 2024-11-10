package classes.items;

import classes.Asset.Sprite.Sprite;
import java.awt.Graphics;
import src.GamePanel;

public abstract class Item {
    private String name;
    private boolean stackable;
    private int quantity;
    private String item_type;
    private int id;
    private Sprite sprite;

    public Item() {

    }

    public Item(String name, String item_type, int quantity, int id, Sprite sprite)
    {
        this.name = name;
        this.stackable = true;
        this.quantity = quantity;
        this.item_type = item_type;
        this.id = id;
        this.sprite = sprite;
    }

    public Item(String name, String item_type, int quantity, int id, String sprite) // for consumables -Raymond
    {
        this.name = name;
        this.stackable = true;
        this.quantity = quantity;
        this.item_type = item_type;
        this.id = id;
        this.sprite = Sprite.load(sprite);
    }

    public Item(String name, String item_type, int id, String sprite) // for weapons and stat-modifiers -Raymond
    {
        this.name = name;
        this.stackable = false;
        this.quantity = 1;
        this.item_type = item_type;
        this.id = id;
        this.sprite = Sprite.load(sprite);
    }

    public Item(String name, String item_type, int id, Sprite sprite)
    {
        this.name = name;
        this.stackable = false;
        this.quantity = 1;
        this.item_type = item_type;
        this.id = id;
        this.sprite = sprite;
    }

    // what's the idea for this? -Raymond
    // I don't know either, I'll change it to quantity += amount - FV
    protected void setQuantity(int amount) {
        quantity += amount;
    }

    public Sprite getSprite(){
        return sprite;
    }

    public String getName() {
        return name;
    }

    public boolean isStackable() {
        return stackable;
    }

    public int getId() {
        return id;
    }

    public int getQuantity() {
        return quantity; // may be used to check if item is 0 and therefore be removed
    }

    public void consumeItem() {
        /*
         * // consume method for consumables here, since quantity is private -FV
         * 
         * // suggest to move to CONSUMABLE class, same reason as quantity variable
         * -Raymond
         * 
         * // should be quality not quantity? also prolly no need for if statement since
         * if
         * // it reaches 0 it should automatically be removed from inventory -Raymond
         * 
         * // Yeah, I typed the wrong thing for the if-case (Resolved)
         * // Also I believe it's simpler to have a consumeItem and removeItem in this
         * class, so it
         * // may be used by other items. For example, if a weapon has durability.
         * // if weaponHP == 0, consumeItem(), since 1, quantity becomes 0, then remove
         * from inventory.
         * 
         * // if case to prevent quantity from going below 0; (I'm not sure how
         * necessary it is, but I have PTSD from CodeChum testcases)
         */

        if (quantity > 0) {
            quantity -= 1;
        }
        if (quantity == 0) {
            // removeItem(); - Who's willing to implement removeItem() function? - FV
        }

    }

    public void display(Graphics g, int x, int y){
        g.drawImage(sprite.getSprite(), x, y, GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null);
    }

}
