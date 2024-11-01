package classes.items;

import java.awt.image.BufferedImage;

import classes.sprites.ItemSprites;
import classes.sprites.Sprite.Sprite;

public class ConsumableUtility extends Consumable {

	public ConsumableUtility(String name, int quantity, int id, Sprite sprite) {
		super(name, quantity, "UTILITY", id, sprite); // item_type placeholder name
	}

	// honestly have no idea how to implement consume HAHAHAAHAHAH - Set H

	/*
	 * I have an idea since OFFENSIVE CONSUMABLES have different effects (we only
	 * have 3 at the moment but
	 * what if we would add more) and to fulfill those different effects we should
	 * just use interfaces - Set H
	 */

	// Mah g oks ra ba same id? Ang melee weapon ids kay gastart sa 300 -Ervin
	public static class EnergyDrink extends ConsumableUtility // implements statBuffer??
	{
		public EnergyDrink(int quantity) {
			super("Energy Drink", quantity, 300, ItemSprites.ConsumableUtility.ENERGY_DRINK); // add id
		}
	}

	public static class IceCream extends ConsumableUtility // implements crowdController??
	{
		public IceCream(int quantity) {
			super("Ice Cream", quantity, 301, ItemSprites.ConsumableUtility.ICE_CREAM); // add id
		}
	}

	public static class FusRoDah extends ConsumableUtility // implements attacker??
	{
		public FusRoDah(int quantity) {                         // temp lang sang energy drink wa pay fusrodah - dymes
			super("FUS RO DAH", quantity, 302, ItemSprites.ConsumableUtility.ENERGY_DRINK); // add id
		}
	}
}
