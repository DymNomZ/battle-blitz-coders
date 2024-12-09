package classes.entities;

import classes.Asset.Sprite.Sprite;
import src.CharacterHandler;

public abstract class PlayableCharacters extends MapEntity {

    Sprite sprite;

    public PlayableCharacters(
        String name, 
        int hit_points, int attack_stat, 
        float haste,
        int defense_stat, int id
    ){
        super();
        super.setName(name);
        super.setMax_hit_points(hit_points);
        super.setHit_points(hit_points);
        super.setAttack_stat(attack_stat);
        super.setHaste(haste);
        super.setDefense_stat(defense_stat);
        super.setId(id);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public static class DYMES extends PlayableCharacters{

        public DYMES(){
            super(
                "DYMES", 
                120, 120, 
                60, 80, 123);

            sprite = CharacterHandler.DYMES();
        }

    }

    public static class SETH extends PlayableCharacters{

        public SETH(){
            super(
                "SETH", 
                1, 1, 
                1, 1, 123);

            sprite = CharacterHandler.SETH();
        }

    }

    public static class ZILLION extends PlayableCharacters{

        public ZILLION(){
            super(
                "ZILLION", 
                30, 150, 
                200, 20, 123);

            sprite = CharacterHandler.ZILLION();
        }

    }

    public static class RAYMOND extends PlayableCharacters{

        public RAYMOND(){
            super(
                "RAYMOND", 
                90, 130, 
                100, 80, 123);

            sprite = CharacterHandler.RAYMOND();
        }

    }

 //   public static class TOPE extends PlayableCharacters{
//
//        public TOPE(){
//            super(
//                "TOPE",
//                65, 130,
//                75, 150, 123);
//        }
//
//    }
//
//    public static class LANCE extends PlayableCharacters{
//
//        public LANCE(){
//            super(
//                "LANCE",
//                75, 150,
//                125, 50, 123);
//        }
//
//    }
//
//    public static class KP extends PlayableCharacters{
//
//        public KP(){
//            super(
//                "KP",
//                200, 66,
//                67, 67, 123);
//        }
//
//    }
//
//    public static class JJ extends PlayableCharacters{
//
//        public JJ(){
//            super(
//                "JJ",
//                75, 75,
//                150, 100, 123);
//        }
//
//    }
//
//    public static class FATEFUL extends PlayableCharacters{
//
//        public FATEFUL(){
//            super(
//                "FATEFUL",
//                200, 100,
//                100, 0, 123);
//        }
//
//    }
//
//    public static class ERVIN extends PlayableCharacters{
//
//        public ERVIN(){
//            super(
//                "ERVIN",
//                1501, 90,
//                60, 100, 123);
//        }
//
//    }
//
//    public static class HANNAH extends PlayableCharacters{
//
//        public HANNAH(){
//            super(
//                "HANNAH",
//                1, 1,
//                1, 1, 123);
//        }
//
//    }
//
//    public static class KURTTY extends PlayableCharacters{
//
//        public KURTTY(){
//            super(
//                "KURTTY",
//                1, 1,
//                1, 1, 123);
//        }
//
//    }

}
