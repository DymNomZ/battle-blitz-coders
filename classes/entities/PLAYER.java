package classes.entities;

public abstract class Player extends MapEntity {

    public Player(
        String name, 
        int hit_points, int attack_stat, 
        float haste,
        int defense_stat, int id
    ){
        super(name, hit_points, attack_stat, haste, defense_stat, id);
    }

    public static class TOPE extends Player{

        public TOPE(){
            super(
                "TOPE", 
                65, 130, 
                75, 150, 123);
        }
            
    }

    public static class DYMES extends Player{

        public DYMES(){
            super(
                "DYMES", 
                120, 120, 
                60, 80, 123);
        }
            
    }

    public static class RAYMOND extends Player{

        public RAYMOND(){
            super(
                "RAYMOND", 
                90, 130, 
                100, 80, 123);
        }
            
    }

    public static class LANCE extends Player{

        public LANCE(){
            super(
                "LANCE", 
                75, 150, 
                125, 50, 123);
        }
            
    }

    public static class KP extends Player{

        public KP(){
            super(
                "KP", 
                200, 66, 
                67, 67, 123);
        }
            
    }

    public static class JJ extends Player{

        public JJ(){
            super(
                "JJ", 
                75, 75, 
                150, 100, 123);
        }
            
    }

    public static class FATEFUL extends Player{

        public FATEFUL(){
            super(
                "FATEFUL", 
                200, 100, 
                100, 0, 123);
        }
            
    }

    public static class ZILLION extends Player{

        public ZILLION(){
            super(
                "ZILLION", 
                30, 150, 
                200, 20, 123);
        }
            
    }

    public static class SETH extends Player{

        public SETH(){
            super(
                "SETH", 
                1, 1, 
                1, 1, 123);
        }
            
    }

    public static class HANNAH extends Player{

        public HANNAH(){
            super(
                "HANNAH", 
                1, 1, 
                1, 1, 123);
        }
            
    }

    public static class KURTTY extends Player{

        public KURTTY(){
            super(
                "KURTTY", 
                1, 1, 
                1, 1, 123);
        }
            
    }

    public static class ERVIN extends Player{

        public ERVIN(){
            super(
                "ERVIN", 
                1501, 90, 
                60, 100, 123);
        }
            
    }

}
