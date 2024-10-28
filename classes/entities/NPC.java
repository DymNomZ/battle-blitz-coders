package classes.entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import src.Panel;

public abstract class NPC extends MapEntity {

    public NPC(String name, int id, int x, int y, BufferedImage sprite) {
        super(name, id, x, y, sprite);
        this.x /= 2;
        this.y /= 2;
    }

    public NPC(String name, int id, int x, int y) {
        super(name, id, x, y, null); //temp for now
    }

    public void displayDialogue(Graphics g) {
        g.setFont(new Font("Consolas", Font.PLAIN, 30));
        g.setColor(Color.BLACK);
        g.drawString("SAMPLE TEXT!!!1!", Panel.SCREEN_WIDTH/2, 300);
    }

    public static class TempNPC extends NPC {

        public TempNPC(int x, int y, BufferedImage sprite) {
            super("Temp", 123, x, y, sprite);
        }
    }

    public static class MaamBolabola extends NPC {

        public MaamBolabola() {
            super("MA'AM BOLABOLA", 123, 0, 0);
        }
    }

    public static class SirGemota extends NPC {

        public SirGemota() {
            super("SIR GEMOTA", 123, 0, 0);
        }
    }

    public static class Student extends NPC {

        public Student(String name) {
            super("Student " + name, 123, 0, 0);
        }
    }

    public static class WildcatStaff extends NPC {

        public WildcatStaff(String name) {
            super("Wildcat Staff " + name, 123, 0, 0);
        }
    }

    public static class Cat extends NPC {

        public Cat(String name) {
            super(name, 123, 0, 0);
        }
    }
}
