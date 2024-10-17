package classes.entities;

public abstract class NPC extends MapEntity{

    public NPC(String name, int id){
        super(name, id);
    }
    public static class MAAM_BOLABOLA extends NPC {

        public MAAM_BOLABOLA(){
            super("MA'AM BOLABOLA", 123);
        }
    }

    public static class SIR_GEMOTA extends NPC {

        public SIR_GEMOTA(){
            super("SIR GEMOTA", 123);
        }
    }

    public static class STUDENT extends NPC {

        public STUDENT(String name){
            super("Student " + name, 123);
        }
    }

    public static class WILDCAT_STAFF extends NPC {

        public WILDCAT_STAFF(String name){
            super("Wildcat Staff " + name, 123);
        }
    }
    
    public static class CAT extends NPC {

        public CAT(String name){
            super(name, 123);
        }
    }
}
