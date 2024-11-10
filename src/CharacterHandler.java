package src;

import classes.Asset.Sprite.AnimatedSprite;
import classes.Asset.Sprite.Sprite;
import classes.entities.PlayableCharacters;
import classes.sprites.EntitySprite;
import java.util.ArrayList;

public class CharacterHandler {

    private static String selected_character = "Zillion"; //Zillion deafult nato AHAHAHAHAHAHAH

    static ArrayList<AnimatedSprite> entitySprites = new ArrayList<>();
    static String[] idles = new String[4];
    static String[] walking = new String[8];
    
    public static String getSelected_character(){
        return selected_character;
    }

    public static void setSelected_character(String selected_character){
        CharacterHandler.selected_character = selected_character;
        System.out.println(CharacterHandler.selected_character);
    }

    public static PlayableCharacters getCharacter(String name){

        PlayableCharacters player = null;

        switch (name) {

            case "Dymes" -> player = new PlayableCharacters.DYMES();
            case "Seth" -> player = new PlayableCharacters.SETH();
            case "Zillion" -> player = new PlayableCharacters.ZILLION();
            case "Raymond" -> player = new PlayableCharacters.RAYMOND();
        }

        return player;
    }

    public static Sprite DYMES() {

        entitySprites.clear();

        idles[0] = "sprites/character_sprites/base sprites/dymier.png";
        idles[1] = "sprites/character_sprites/dymier_left_F0.png";
        idles[2] = "sprites/character_sprites/dymier_left_F0.png";
        idles[3] = "sprites/character_sprites/dymier_left_F0_blink.png";
        entitySprites.add(new AnimatedSprite(500000000, idles));

        walking[0] = "sprites/character_sprites/dymier_left_F1.png";
        walking[1] = "sprites/character_sprites/dymier_left_F0.png";
        walking[2] = "sprites/character_sprites/dymier_left_F-1.png";
        walking[3] = "sprites/character_sprites/dymier_left_F0.png";
        walking[4] = "sprites/character_sprites/dymier_left_F1.png";
        walking[5] = "sprites/character_sprites/dymier_left_F0.png";
        walking[6] = "sprites/character_sprites/dymier_left_F-1.png";
        walking[7] = "sprites/character_sprites/dymier_left_F0_blink.png";
        entitySprites.add(new AnimatedSprite(250000000, walking));

        walking[0] = "sprites/character_sprites/dymier_right_F1.png";
        walking[1] = "sprites/character_sprites/dymier_right_F0.png";
        walking[2] = "sprites/character_sprites/dymier_right_F-1.png";
        walking[3] = "sprites/character_sprites/dymier_right_F0.png";
        walking[4] = "sprites/character_sprites/dymier_right_F1.png";
        walking[5] = "sprites/character_sprites/dymier_right_F0_blink.png";
        walking[6] = "sprites/character_sprites/dymier_right_F-1.png";
        walking[7] = "sprites/character_sprites/dymier_right_F0.png";
        entitySprites.add(new AnimatedSprite(250000000, walking));

        return new EntitySprite(entitySprites).cropSprite(5, 10,0,4).setMoving(true).toLeft();

    }

    public static Sprite SETH() {

        entitySprites.clear();

        idles[0] = "sprites/character_sprites/base sprites/seth.png";
        idles[1] = "sprites/character_sprites/seth_left_F0.png";
        idles[2] = "sprites/character_sprites/seth_left_F0.png";
        idles[3] = "sprites/character_sprites/seth_left_F0_blink.png";
        entitySprites.add(new AnimatedSprite(500000000, idles));

        walking[0] = "sprites/character_sprites/seth_left_F1.png";
        walking[1] = "sprites/character_sprites/seth_left_F0.png";
        walking[2] = "sprites/character_sprites/seth_left_F-1.png";
        walking[3] = "sprites/character_sprites/seth_left_F0.png";
        walking[4] = "sprites/character_sprites/seth_left_F1.png";
        walking[5] = "sprites/character_sprites/seth_left_F0.png";
        walking[6] = "sprites/character_sprites/seth_left_F-1.png";
        walking[7] = "sprites/character_sprites/seth_left_F0_blink.png";
        entitySprites.add(new AnimatedSprite(250000000, walking));

        walking[0] = "sprites/character_sprites/seth_right_F1.png";
        walking[1] = "sprites/character_sprites/seth_right_F0.png";
        walking[2] = "sprites/character_sprites/seth_right_F-1.png";
        walking[3] = "sprites/character_sprites/seth_right_F0.png";
        walking[4] = "sprites/character_sprites/seth_right_F1.png";
        walking[5] = "sprites/character_sprites/seth_right_F0.png";
        walking[6] = "sprites/character_sprites/seth_right_F-1.png";
        walking[7] = "sprites/character_sprites/seth_right_F0_blink.png";
        entitySprites.add(new AnimatedSprite(250000000, walking));

        return new EntitySprite(entitySprites).cropSprite(5, 10,0,4).setMoving(true).toLeft();

    }

    public static Sprite ZILLION() {

        entitySprites.clear();

        idles[0] = "sprites/character_sprites/base sprites/zillion_1.png";
        idles[1] = "sprites/character_sprites/base sprites/zillion_1.png";
        idles[2] = "sprites/character_sprites/base sprites/zillion_2.png";
        idles[3] = "sprites/character_sprites/zillion_left_F0_blink.png";
        entitySprites.add(new AnimatedSprite(500000000, idles));

        walking[0] = "sprites/character_sprites/zillion_left_F1.png";
        walking[1] = "sprites/character_sprites/zillion_left_F0.png";
        walking[2] = "sprites/character_sprites/zillion_left_F-1.png";
        walking[3] = "sprites/character_sprites/zillion_left_F0.png";
        walking[4] = "sprites/character_sprites/zillion_left_F1.png";
        walking[5] = "sprites/character_sprites/zillion_left_F0.png";
        walking[6] = "sprites/character_sprites/zillion_left_F-1.png";
        walking[7] = "sprites/character_sprites/zillion_left_F0_blink.png";
        entitySprites.add(new AnimatedSprite(250000000, walking));

        walking[0] = "sprites/character_sprites/zillion_right_F1.png";
        walking[1] = "sprites/character_sprites/zillion_right_F0.png";
        walking[2] = "sprites/character_sprites/zillion_right_F-1.png";
        walking[3] = "sprites/character_sprites/zillion_right_F0.png";
        walking[4] = "sprites/character_sprites/zillion_right_F1.png";
        walking[5] = "sprites/character_sprites/zillion_right_F0.png";
        walking[6] = "sprites/character_sprites/zillion_right_F-1.png";
        walking[7] = "sprites/character_sprites/zillion_right_F0_blink.png";
        entitySprites.add(new AnimatedSprite(250000000, walking));

        EntitySprite e =  new EntitySprite(entitySprites).cropSprite(5, 10,0,4).setMoving(true).toLeft();
        e.get(EntitySprite.DEFAULT_MOVING_RIGHT).cropSpriteRelative(0, -3, 0, 3);
        return e;

    }

    public static Sprite RAYMOND() {

        entitySprites.clear();

        idles[0] = "sprites/character_sprites/base sprites/raymond.png";
        idles[1] = "sprites/character_sprites/raymond_left_F0.png";
        idles[2] = "sprites/character_sprites/raymond_left_F0.png";
        idles[3] = "sprites/character_sprites/raymond_left_F0_blink.png";
        entitySprites.add(new AnimatedSprite(500000000, idles));

        walking[0] = "sprites/character_sprites/raymond_left_F1.png";
        walking[1] = "sprites/character_sprites/raymond_left_F0.png";
        walking[2] = "sprites/character_sprites/raymond_left_F-1.png";
        walking[3] = "sprites/character_sprites/raymond_left_F0.png";
        walking[4] = "sprites/character_sprites/raymond_left_F1.png";
        walking[5] = "sprites/character_sprites/raymond_left_F0.png";
        walking[6] = "sprites/character_sprites/raymond_left_F-1.png";
        walking[7] = "sprites/character_sprites/raymond_left_F0_blink.png";
        entitySprites.add(new AnimatedSprite(250000000, walking));

        walking[0] = "sprites/character_sprites/raymond_right_F1.png";
        walking[1] = "sprites/character_sprites/raymond_right_F0.png";
        walking[2] = "sprites/character_sprites/raymond_right_F-1.png";
        walking[3] = "sprites/character_sprites/raymond_right_F0.png";
        walking[4] = "sprites/character_sprites/raymond_right_F1.png";
        walking[5] = "sprites/character_sprites/raymond_right_F0.png";
        walking[6] = "sprites/character_sprites/raymond_right_F-1.png";
        walking[7] = "sprites/character_sprites/raymond_right_F0_blink.png";
        entitySprites.add(new AnimatedSprite(250000000, walking));

        return new EntitySprite(entitySprites).cropSprite(5, 10,2,8).setMoving(true).toLeft();

    }
}
