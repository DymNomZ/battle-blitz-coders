package classes.items;

public abstract class ConsumableHealing extends Consumable
{
    private String heal_type;       //HOT, INSTA
    private int heal_amount;
    private int heal_time;       //for HOT items

    public ConsumableHealing(String name, int quantity, int id, int heal_amount, int heal_time)   //HOT
    {
        super(name, quantity, "HEALING", id);       //item type can still be specified more if necessary -Raymond
        this.heal_type = "HOT";
        this.heal_amount = heal_amount;
        this.heal_time = heal_time; 
    }
    public ConsumableHealing(String name, int quantity, int id, int heal_amount)   //INSTA
    {
        super(name, quantity, "HEALING", id);       //item type can still be specified more if necessary -Raymond
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
         // why the if statement?    - Raymond 
         /* 
         FV:
                Reply to Raymond:
                
                It's for instant healing. if healtime is 0, instant, so just return healamount. 
                for the else statement, i made a space for someone to implement 
                the "Heal over Time", and how to return it repeatedly 

                It might make more sense for consumeHeal() to manipulate the character's health
                over a period of time, rather than returning the healing the item performs.

                Suggesting an idea for Instant and Periodical Heal: 
                    1. Since character will be healed and damaged, there needs to be a manipulator method for the HP of the character.
                    2. HP manipulation method may be takeDamage() with hp -= dmg for both healing and damage;
                    3. For healing, pass a negative value to the manipulator function to increase HP.
         */

        if (heal_time == 0)     
        {
            System.out.println("You used one " + this.getName() + " to heal " + this.heal_amount + " hp!");
            return this.heal_amount;
        } else
        {
            //how to heal for HoT? - FV
            return this.heal_amount;
        }
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
            super("Hotcake", quantity, 200, 20); //add id
        }
    }
    public static class GrabDelivery extends ConsumableHealing  //HOT
    {
        public GrabDelivery(int quantity)
        {
            super("Grab Delivery", quantity, 201, 5, 30);
        }
    }
    public static class Pizza extends ConsumableHealing
    {
        public Pizza(int quantity)
        {
            super("Pizza", quantity, 202, 30);
        }
    }
    public class BandAid extends ConsumableHealing
    {
        public BandAid(int quantity)
        {
            super("Band-Aid", quantity, 203, 5, 10);
        }
    }
    public class CupNoodles extends ConsumableHealing
    {
        public CupNoodles(int quantity)
        {
            super("Cup Noodles", quantity, 204, 10, 15);
        }
    }
    public class Chips extends ConsumableHealing
    {
        public Chips(int quantity)
        {
            super("Chips", quantity, 205, 20);
        }
    }
}
