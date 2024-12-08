package classes.entities;

import classes.Asset.Sprite.Sprite;
import interfaces.EntityCollidable;
import src.GamePanel;

public class MeleeAttackEntity extends PanelEntity implements EntityCollidable {

    PanelEntity attacker;
    int damage;
    boolean isToDestroy;

    public void toDestroy() {
        isToDestroy = true;
    }
    public boolean isToDestroy() {
        return isToDestroy;
    }

    public MeleeAttackEntity(PanelEntity attacker) {
        this.attacker = attacker;
        isToDestroy = false;
    }
    public int dealDamage(){
        return damage;
    }

    public static class PlayerMeleeAttack extends MeleeAttackEntity{
        public PlayerMeleeAttack(PanelEntity attacker, int x, int y) {
            super(attacker);
            damage = 5;
            super.setBuffer(Sprite.load("sprites/special_effects/melee_whoosh.png"));
            super.setDimensions(GamePanel.TILE_SIZE,2 * GamePanel.TILE_SIZE);
            super.setX(x);
            super.setY(y);
        }
    }
}
