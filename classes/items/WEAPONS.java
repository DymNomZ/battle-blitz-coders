package classes.items;

public abstract class Weapons extends Item {
    // To implement
    private int damage; // Set to private -Ervin
    private String weapon_type; // Added this to differentiate melee to ranged -Ervin

    public Weapons(String name, String weapon_type, int quality, int id, int damage) {
        super(name, "Weapon", quality, id);
        this.damage = damage;
        this.weapon_type = weapon_type;
    }

    public int getDamage() { // Did this since item superclass didn't have way to return damage - FV
        return damage;  //is the damage variable not usable? -Raymond
    }

    public String getWeapon_type() { // Added this; might be useful for later features -Ervin
        return weapon_type;
    }
}
