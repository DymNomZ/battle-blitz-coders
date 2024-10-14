package classes.entities;

public abstract class PLAYER extends MAP_ENTITY {

    public PLAYER(
        String name, 
        int hit_points, int attack_stat, 
        float haste,
        int defense_stat, int id
    ){
        super(name, hit_points, attack_stat, haste, defense_stat, id);
    }

    public static class TOPE extends PLAYER{

        public TOPE(){
            super(
                "TOPE", 
                65, 130, 
                75, 150, 123);
        }
            
    }

    public static class DYMES extends PLAYER{

        public DYMES(){
            super(
                "DYMES", 
                120, 120, 
                60, 80, 123);
        }
            
    }

    public static class RAYMOND extends PLAYER{

        public RAYMOND(){
            super(
                "RAYMOND", 
                90, 130, 
                100, 80, 123);
        }
            
    }

    public static class LANCE extends PLAYER{

        public LANCE(){
            super(
                "LANCE", 
                75, 150, 
                125, 50, 123);
        }
            
    }

    public static class KP extends PLAYER{

        public KP(){
            super(
                "KP", 
                200, 66, 
                67, 67, 123);
        }
            
    }

    public static class JJ extends PLAYER{

        public JJ(){
            super(
                "JJ", 
                75, 75, 
                150, 100, 123);
        }
            
    }

    public static class FATEFUL extends PLAYER{

        public FATEFUL(){
            super(
                "FATEFUL", 
                200, 100, 
                100, 0, 123);
        }
            
    }

    public static class ZILLION extends PLAYER{

        public ZILLION(){
            super(
                "ZILLION", 
                30, 150, 
                200, 20, 123);
        }
            
    }

    public static class SETH extends PLAYER{

        public SETH(){
            super(
                "SETH", 
                1, 1, 
                1, 1, 123);
        }
            
    }

    public static class HANNAH extends PLAYER{

        public HANNAH(){
            super(
                "HANNAH", 
                1, 1, 
                1, 1, 123);
        }
            
    }

    public static class KURTTY extends PLAYER{

        public KURTTY(){
            super(
                "KURTTY", 
                1, 1, 
                1, 1, 123);
        }
            
    }

    public static class ERVIN extends PLAYER{

        public ERVIN(){
            super(
                "ERVIN", 
                1501, 90, 
                60, 100, 123);
        }
            
    }

}
