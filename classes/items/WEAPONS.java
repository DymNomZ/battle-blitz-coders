package classes.items;

public abstract class WEAPONS extends ITEM {
    // To implement
    private int damage; // Set to private -Ervin
    private String weaponType; // Added this to differentiate melee to ranged -Ervin

    public WEAPONS(String name, String weaponType, int quality, int id, int damage) {
        super(name, "Weapon", quality, id);
        this.damage = damage;
        this.weaponType = weaponType;
    }

    public int getDamage() { // Did this since item superclass didn't have way to return damage - FV
        return damage;
    }

    public String getWeaponType() { // Added this; might be useful for later features -Ervin
        return weaponType;
    }
}
