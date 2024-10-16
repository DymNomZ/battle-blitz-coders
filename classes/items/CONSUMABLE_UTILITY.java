package classes.items;

public class CONSUMABLE_UTILITY extends CONSUMABLE{

	public CONSUMABLE_UTILITY(String name, int quantity, int id){
		super(name, quantity, "UTILITY", id); //item_type placeholder name
	}

	//honestly have no idea how to implement consume HAHAHAAHAHAH - Set H

	/*
	* I have an idea since OFFENSIVE CONSUMABLES have different effects (we only have 3 at the moment but
	* what if we would add more) and to fulfill those different effects we should just use interfaces - Set H
	* */


	public static class EnergyDrink extends CONSUMABLE_UTILITY //implements statBuffer??
	{
		public EnergyDrink(int quantity)
		{
			super("Energy Drink", quantity, 300); //add id
		}
	}
	public static class IceCream extends CONSUMABLE_UTILITY //implements crowdController??
	{
		public IceCream(int quantity)
		{
			super("Ice Cream", quantity, 301); //add id
		}
	}
	public static class FusRoDah extends CONSUMABLE_UTILITY //implements attacker??
	{
		public FusRoDah(int quantity)
		{
			super("FUS RO DAH", quantity, 302); //add id
		}
	}
}
