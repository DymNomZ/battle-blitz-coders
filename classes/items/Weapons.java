package classes.items;

import classes.Asset.Sprite.Sprite;

public abstract class Weapons extends Item {
    // To implement
    private int damage;
    private int quality;
    private String weapon_type;

    public Weapons() {
        super();
    }

    public Weapons(String name, String weapon_type, int quality, int id, int damage, Sprite sprite) {
        super(name, "Weapon", id, sprite);
        this.quality = quality;
        this.damage = damage;
        this.weapon_type = weapon_type;
    }

    public int getDamage() { // Did this since item superclass didn't have way to return damage - FV
        return damage; // is the damage variable not usable? -Raymond
    }

    public String getWeapon_type() {
        return weapon_type;
    }

    public int getQuality() {
        return quality;
    }
}
