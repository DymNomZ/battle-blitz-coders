package classes.entities;

import classes.Asset.Sprite.AnimatedSprite;
import classes.Asset.Sprite.Sprite;
import classes.sprites.BossEnemySprite;
import classes.sprites.EnemySprite;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemyHandler {
    
    public static Sprite VIRUS(){

        List<AnimatedSprite> entitySprites = new ArrayList<>();
        String[] filenames = new String[4];
        filenames[0] = "sprites/enemies/virus_left_F1.png";
        filenames[1] = "sprites/enemies/virus_left_F1.png";
        filenames[2] = "sprites/enemies/virus_left_F1.png";
        filenames[3] = "sprites/enemies/virus_left_F1.png";
        entitySprites.add(new AnimatedSprite(500000000, filenames));

        filenames[0] = "sprites/enemies/virus_left_F1.png";
        filenames[1] = "sprites/enemies/virus_left_F2.png";
        filenames[2] = "sprites/enemies/virus_left_F1.png";
        filenames[3] = "sprites/enemies/virus_left_F2.png";
        entitySprites.add(new AnimatedSprite(500000000, filenames));

        filenames[0] = "sprites/enemies/virus_right_F1.png";
        filenames[1] = "sprites/enemies/virus_right_F2.png";
        filenames[2] = "sprites/enemies/virus_right_F1.png";
        filenames[3] = "sprites/enemies/virus_right_F2.png";
        entitySprites.add(new AnimatedSprite(500000000, filenames));

        filenames = new String[5];
        filenames[0] = "sprites/enemies/virus_left_F1.png";
        filenames[1] = "sprites/enemies/virus_left_F2.png";
        filenames[2] = "sprites/enemies/virus_left_F3.png";
        filenames[3] = "sprites/enemies/virus_left_F4.png";
        filenames[4] = "sprites/enemies/virus_left_F5.png";
        entitySprites.add(new AnimatedSprite(100000000, filenames));

        filenames[0] = "sprites/enemies/virus_right_F1.png";
        filenames[1] = "sprites/enemies/virus_right_F2.png";
        filenames[2] = "sprites/enemies/virus_right_F3.png";
        filenames[3] = "sprites/enemies/virus_right_F4.png";
        filenames[4] = "sprites/enemies/virus_right_F5.png";
        entitySprites.add(new AnimatedSprite(100000000, filenames));

        return new EnemySprite(entitySprites);
        
    }

    public static Sprite SLIME(){

        enum slimeType{
            PYRO,
            HYDRO,
            GEO;
            public static slimeType fromInteger(int x){
                switch(x){
                    case 0: return PYRO;
                    case 1: return HYDRO;
                    case 2: return GEO;
                }
                return null;
            }
            public static int toInteger(slimeType slime_type){
                switch(slime_type){
                    case PYRO -> {
                        return 0;
                    }
                    case HYDRO -> {
                        return 1;
                    }
                    case GEO -> {
                        return 2;
                    }
                }
                return 0;
            }
        }
        
        slimeType slime_type;
        slime_type = slimeType.fromInteger(new Random().nextInt(0,3));

        List<AnimatedSprite> entitySprites = new ArrayList<>();
        final int slime_type_int = slimeType.toInteger(slime_type) + 1;
        String filename_initial = "sprites/enemies/slime_stage" + slime_type_int;
        String[] filenames = new String[4];

        filenames[0] = filename_initial + "_left_F0.png";
        filenames[1] = filename_initial + "_left_F0.png";
        filenames[2] = filename_initial + "_left_F0.png";
        filenames[3] = filename_initial + "_left_F0.png";
        entitySprites.add(new AnimatedSprite(500000000, filenames));

        filenames[0] = filename_initial + "_left_F0.png";
        filenames[1] = filename_initial + "_left_F1.png";
        filenames[2] = filename_initial + "_left_F0.png";
        filenames[3] = filename_initial + "_left_F1.png";
        entitySprites.add(new AnimatedSprite(500000000, filenames));

        filenames[0] = filename_initial + "_right_F0.png";
        filenames[1] = filename_initial + "_right_F1.png";
        filenames[2] = filename_initial + "_right_F0.png";
        filenames[3] = filename_initial + "_right_F1.png";
        entitySprites.add(new AnimatedSprite(500000000, filenames));

        return new EnemySprite(entitySprites);
        
    }
    public static Sprite PEASHOOTER(Enemy.Peashooter.PeashooterDirection direction){
        List<AnimatedSprite> entitySprites = new ArrayList<>();
        String[] filenames = new String[1];
        switch (direction){
            case LEFT -> {
                filenames[0] = "sprites/enemies/peashooter_left_F0.png";
            }
            case DOWN -> {
                filenames[0] = "sprites/enemies/peashooter_front_F0.png";
            }
            case RIGHT -> {
                filenames[0] = "sprites/enemies/peashooter_right_F0.png";
            }
        }
        entitySprites.add(new AnimatedSprite(500000000, filenames));
        entitySprites.add(new AnimatedSprite(500000000, filenames));
        entitySprites.add(new AnimatedSprite(500000000, filenames));
        switch (direction){
            case LEFT -> {
                filenames[0] = "sprites/enemies/peashooter_left_F1.png";
            }
            case DOWN -> {
                filenames[0] = "sprites/enemies/peashooter_front_F1.png";
            }
            case RIGHT -> {
                filenames[0] = "sprites/enemies/peashooter_right_F1.png";
            }
        }
        entitySprites.add(new AnimatedSprite(500000000, filenames));
        entitySprites.add(new AnimatedSprite(500000000, filenames));
        return new EnemySprite(entitySprites);
    }
    public static Sprite SQUASH(){
        List<AnimatedSprite> entitySprites = new ArrayList<>();
        String[] filenames = new String[3];
        filenames[0] = "sprites/enemies/squash_left_F0.png";
        filenames[1] = "sprites/enemies/squash_left_F0.png";
        filenames[2] = "sprites/enemies/squash_left_F0.png";
        entitySprites.add(new AnimatedSprite(500000000, filenames));
        filenames[0] = "sprites/enemies/squash_left_F0.png";
        filenames[1] = "sprites/enemies/squash_left_F0.png";
        filenames[2] = "sprites/enemies/squash_left_F0.png";
        entitySprites.add(new AnimatedSprite(500000000, filenames));
        filenames[0] = "sprites/enemies/squash_right_F0.png";
        filenames[1] = "sprites/enemies/squash_right_F0.png";
        filenames[2] = "sprites/enemies/squash_right_F0.png";
        entitySprites.add(new AnimatedSprite(500000000, filenames));
        filenames[0] = "sprites/enemies/squash_left_F1.png";
        filenames[1] = "sprites/enemies/squash_left_F2.png";
        filenames[2] = "sprites/enemies/squash_left_F2.png";
        entitySprites.add(new AnimatedSprite(500000000, filenames));
        filenames[0] = "sprites/enemies/squash_right_F1.png";
        filenames[1] = "sprites/enemies/squash_right_F2.png";
        filenames[2] = "sprites/enemies/squash_right_F2.png";
        entitySprites.add(new AnimatedSprite(500000000, filenames));


        return new EnemySprite(entitySprites);
    }

    public static Sprite getHead(String boss){

        Sprite sprite = null;

        switch(boss){
            case "Sir Serato Hidden" -> sprite = Sprite.load("sprites/character_sprites/portraits/serato_portrait_right_silhouette.png");
            case "Sir Serato Smile" -> sprite = Sprite.load("sprites/character_sprites/portraits/serato_portrait_right_smile.png");
            case "Sir Serato Normal" -> sprite = Sprite.load("sprites/character_sprites/portraits/serato_portrait_right_normal.png");
            case "Sir Serato Eyebrow" -> sprite = Sprite.load("sprites/character_sprites/portraits/serato_portrait_right_eyebrowraised.png");
            case "Sir Serato Rolleyes" -> sprite = Sprite.load("sprites/character_sprites/portraits/serato_portrait_right_rolleyes.png");
            case "Sir Serato Facepalm" -> sprite = Sprite.load("sprites/character_sprites/portraits/serato_portrait_right_facepalm.png");
            case "Sir Serato Blink" -> sprite = Sprite.load("sprites/character_sprites/portraits/serato_portrait_right_blink.png");
        }

        return sprite;

    }

    public static Sprite SIR_SERATO(){
        List<AnimatedSprite> entitySprites = new ArrayList<>();
        String filename[] = new String[1];
        filename[0] = "sprites/enemies/serato_attack_F1.png";
        entitySprites.add(new AnimatedSprite(5000000,filename));

        filename[0] = "sprites/enemies/serato_pose_3.png";
        entitySprites.add(new AnimatedSprite(5000000,filename));

        filename[0] = "sprites/enemies/serato_attack_F0.png";
        entitySprites.add(new AnimatedSprite(5000000,filename));

        filename[0] = "sprites/enemies/serato_left_F0.png";
        entitySprites.add(new AnimatedSprite(5000000,filename));

        filename[0] = "sprites/enemies/serato_pose_1.png";
        entitySprites.add(new AnimatedSprite(5000000,filename));

        return new BossEnemySprite.SirSeratoBossSprites(entitySprites);
    }
    
}
