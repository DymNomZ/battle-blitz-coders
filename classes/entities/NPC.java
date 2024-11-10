package classes.entities;

import classes.Asset.Sprite.Sprite;
import interfaces.EntityCollidable;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import src.GamePanel;

public abstract class NPC extends MapEntity implements EntityCollidable {

    public NPC(String name, long id, int x, int y, String spritePath) {
        super(name, id, x, y, spritePath);
        this.x /= 2;
        this.y /= 2;
    }

    public NPC(String name, long id, int x, int y, int side, int allowance) {
        super(name, id, x, y, side, allowance);
    }

    public void displayDialogue(Graphics g, CameraEntity cam) {
        String sample_text = "SAMPLE TEXT!!!1!";
        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        g.setColor(Color.BLACK);
                                    //subtract length by 1/4 of Tile Size to center, bana2 ra bitaw ni AHAHAH, subject to change
        g.drawString(sample_text, (x - cam.x) - ((sample_text.length() * GamePanel.TILE_SIZE) / 16), (y - cam.y) - 30);
    }

    public static class TempNPC extends NPC {

        public TempNPC(int x, int y, int side, int allowance, Sprite sprite) {
            super("Temp", System.nanoTime(), x, y, side, side + allowance);
            buffer = sprite;
        }
    }

    public static class MaamBolabola extends NPC {

        public MaamBolabola() {
            super("MA'AM BOLABOLA", 123, 0, 0, 0, 0);
        }
    }

    public static class SirGemota extends NPC {

        public SirGemota() {
            super("SIR GEMOTA", 123, 0, 0, 0, 0);
        }
    }

    public static class Student extends NPC {

        public Student(String name) {
            super("Student " + name, 123, 0, 0, 0, 0);
        }
    }

    public static class WildcatStaff extends NPC {

        public WildcatStaff(String name) {
            super("Wildcat Staff " + name, 123, 0, 0, 0, 0);
        }
    }

    public static class Cat extends NPC {

        public Cat(String name) {
            super(name, 123, 0, 0, 0, 0);
        }
    }
}
