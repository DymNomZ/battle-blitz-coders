package classes.entities;

import classes.Asset.Sprite.Sprite;
import classes.GUI.General;
import interfaces.EntityCollidable;
import java.awt.*;
import java.awt.image.BufferedImage;
import src.CharacterHandler;
import src.GamePanel;

public abstract class NPC extends MapEntity implements EntityCollidable {

    public NPC(String name, long id, int x, int y, String spritePath) {
        super();
        super.setName(name);
        super.setId(id);
        super.setBuffer(Sprite.load(spritePath));
        super.setX(x / 2);
        super.setY(y / 2);
    }

    public NPC(String name, long id, int x, int y, int side, int allowance) {
        super();
        super.setName(name);
        super.setId(id);
        super.setX(x);
        super.setY(y);
        super.setDimensions(side,allowance);
    }

    public static void drawHead(Graphics g){

        Sprite raw_head = CharacterHandler.getHead();
        BufferedImage head = raw_head.getSprite();

        g.drawImage(head.getScaledInstance(General.SCALE_VALUES[4], General.SCALE_VALUES[4], Image.SCALE_SMOOTH), 
                (GamePanel.SCREEN_WIDTH / 2) - 525, 50, null
        );
    }

    public static class TempNPC extends NPC {

        public TempNPC(int x, int y, int side, int allowance, Sprite sprite) {
            super("Temp", System.nanoTime(), x, y, side, side + allowance);
            buffer = sprite;
        }
    }
}
