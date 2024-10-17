package classes.items;

<<<<<<< HEAD
public abstract class Weapons extends Item {
    // To implement
    private int damage; // Set to private -Ervin
    private String weapon_type; // Added this to differentiate melee to ranged -Ervin

    public Weapons(String name, String weapon_type, int quality, int id, int damage) {
        super(name, "Weapon", quality, id);
        this.damage = damage;
        this.weapon_type = weapon_type;
=======
public abstract class WEAPONS extends ITEM {
    // To implement
    private int damage; // Set to private -Ervin
    private String weaponType; // Added this to differentiate melee to ranged -Ervin

    public WEAPONS(String name, String weaponType, int quality, int id, int damage) {
        super(name, "Weapon", quality, id);
        this.damage = damage;
        this.weaponType = weaponType;
>>>>>>> fac42dfe06377e896ba9cdff40761a80fd8761bf
    }

    public int getDamage() { // Did this since item superclass didn't have way to return damage - FV
        return damage;  //is the damage variable not usable? -Raymond
    }

<<<<<<< HEAD
    public String getWeapon_type() { // Added this; might be useful for later features -Ervin
        return weapon_type;
=======
    public String getWeaponType() { // Added this; might be useful for later features -Ervin
        return weaponType;
>>>>>>> fac42dfe06377e896ba9cdff40761a80fd8761bf
    }
}
