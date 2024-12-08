package classes.items;

import classes.Asset.Sprite.Sprite;
import classes.sprites.ItemSprites;
import java.util.Random;

public class ConsumableHealing extends Consumable
{
    private String heal_type;       //HOT, INSTA
    private int heal_amount;
    private int heal_time;       //for HOT items

    public ConsumableHealing(){
        super();
    }

    public ConsumableHealing(String name, int quantity, int id, int heal_amount, int heal_time, Sprite sprite)   //HOT
    {
        super(name, quantity, "HEALING", id, sprite);       //item type can still be specified more if necessary -Raymond
        this.heal_type = "HOT";
        this.heal_amount = heal_amount;
        this.heal_time = heal_time; 
    }
    public ConsumableHealing(String name, int quantity, int id, int heal_amount, Sprite sprite)   //INSTA
    {
        super(name, quantity, "HEALING", id, sprite);       //item type can still be specified more if necessary -Raymond
        this.heal_type = "INSTA";
        this.heal_amount = heal_amount;
        this.heal_time = 0;
    }

    public String getHeal_type()
    {
        return this.heal_type;
    }
    
    public int consumeHeal()
    {
        consumeItem();

        if (heal_time == 0)     
        {
            System.out.println("You used one " + this.getName() + " to heal " + this.heal_amount + " hp!");
            return this.heal_amount;
        } else
        {
            return this.heal_amount;
        }
    }

    public Item getRandom(){
        int num = new Random().nextInt(1, 7);
        Item m = null;

        switch(num){
            case 1 -> m = new ConsumableHealing.Pizza(1);
            case 2 -> m = new ConsumableHealing.Hotcake(1);
            case 3 -> m = new ConsumableHealing.GrabDelivery(1);
            case 4 -> m = new ConsumableHealing.BandAid(1);
            case 5 -> m = new ConsumableHealing.Chips(1);
            case 6 -> m = new ConsumableHealing.CupNoodles(1);
        }

        return m;
    }

    //SUBCLASSES 
    /*
        fixed the super constructors, and made them static as well
        all subclass ids are numbered from 200 and so forth
        predefined the heal and heal time of the items // change them, if you will
    */

    public static class Hotcake extends ConsumableHealing   //INSTA
    {
        public Hotcake(int quantity)
        {
            super("Hotcake", quantity, 200, 20, ItemSprites.ConsumableHealing.HOTCAKE); //add id
        }
    }
    public static class GrabDelivery extends ConsumableHealing  //HOT
    {
        public GrabDelivery(int quantity)
        {
            super("Grab Delivery", quantity, 201, 5, 30, ItemSprites.ConsumableHealing.GRAB);
        }
    }
    public static class Pizza extends ConsumableHealing
    {
        public Pizza(int quantity)
        {
            super("Pizza", quantity, 202, 30, ItemSprites.ConsumableHealing.PIZZA);
        }
    }
    public class BandAid extends ConsumableHealing
    {
        public BandAid(int quantity)
        {
            super("Band-Aid", quantity, 203, 5, 10, ItemSprites.ConsumableHealing.BAND_AID);
        }
    }
    public class CupNoodles extends ConsumableHealing
    {
        public CupNoodles(int quantity)
        {
            super("Cup Noodles", quantity, 204, 10, 15, ItemSprites.ConsumableHealing.CUP_NOODLES);
        }
    }
    public class Chips extends ConsumableHealing
    {
        public Chips(int quantity)
        {
            super("Chips", quantity, 205, 20, ItemSprites.ConsumableHealing.CHIPS);
        }
    }
}
