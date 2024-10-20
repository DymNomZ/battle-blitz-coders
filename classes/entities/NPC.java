package classes.entities;

public abstract class NPC extends MapEntity {

    public NPC(String name, int id) {
        super(name, id);
    }

    public static class MaamBolabola extends NPC {

        public MaamBolabola() {
            super("MA'AM BOLABOLA", 123);
        }
    }

    public static class SirGemota extends NPC {

        public SirGemota() {
            super("SIR GEMOTA", 123);
        }
    }

    public static class Student extends NPC {

        public Student(String name) {
            super("Student " + name, 123);
        }
    }

    public static class WildcatStaff extends NPC {

        public WildcatStaff(String name) {
            super("Wildcat Staff " + name, 123);
        }
    }

    public static class Cat extends NPC {

        public Cat(String name) {
            super(name, 123);
        }
    }
}
