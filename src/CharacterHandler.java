package src;

import classes.Asset.Sprite.AnimatedSprite;
import classes.Asset.Sprite.Sprite;
import classes.entities.PlayableCharacters;
import classes.sprites.EntitySprite;
import java.util.ArrayList;
import java.util.Random;

public class CharacterHandler {

    private static String selected_character = "Zillion"; //Zillion deafult nato AHAHAHAHAHAHAH
    private static String selected_NPC = "Seth";

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

    public static Sprite randomizer(){

        int n = new Random().nextInt(1,4);
        Sprite sprite = null;
        String selected_character = CharacterHandler.getSelected_character();

        switch(selected_character){
            case "Dymes" -> {
                switch(n){
                    case 1 -> sprite = SETH();
                    case 2 -> sprite = ZILLION();
                    case 3 -> sprite = RAYMOND();
                }
            }
            case "Seth" -> {
                switch(n){
                    case 1 -> sprite = DYMES();
                    case 2 -> sprite = ZILLION();
                    case 3 -> sprite = RAYMOND();
                }
            }
            case "Zillion" -> {
                switch(n){
                    case 1 -> sprite = SETH();
                    case 2 -> sprite = DYMES();
                    case 3 -> sprite = RAYMOND();
                }
            }
            case "Raymond" -> {
                switch(n){
                    case 1 -> sprite = SETH();
                    case 2 -> sprite = ZILLION();
                    case 3 -> sprite = DYMES();
                }
            }
        }

        return sprite;
    }

    public static Sprite getHead(){

        Sprite sprite = null;

        switch(selected_NPC){
            case "Dymes" -> sprite = Sprite.load("sprites/character_sprites/portraits/dymier_portrait_right.png");
            case "Seth" -> sprite = Sprite.load("sprites/character_sprites/portraits/seth_portrait_right.png");
            case "Zillion" -> sprite = Sprite.load("sprites/character_sprites/portraits/zillion_portrait_right.png");
            case "Raymond" -> sprite = Sprite.load("sprites/character_sprites/portraits/raymond_portrait_right.png");
        }

        return sprite;

    }

    public static Sprite DYMES() {

        selected_NPC = "Dymes";
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

        EntitySprite e =  new EntitySprite(entitySprites).cropSprite(5, 10,0,4).setMoving(true).toRight();
        e.get(EntitySprite.DEFAULT_MOVING_RIGHT).cropSpriteRelative(0, -3, 0, 3);
        return e;

    }

    public static Sprite SETH() {

        selected_NPC = "Seth";
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

        EntitySprite e =  new EntitySprite(entitySprites).cropSprite(5, 10,0,4).setMoving(true).toRight();
        e.get(EntitySprite.DEFAULT_MOVING_RIGHT).cropSpriteRelative(0, -3, 0, 3);
        return e;

    }

    public static Sprite ZILLION() {

        selected_NPC = "Zillion";
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

        EntitySprite e =  new EntitySprite(entitySprites).cropSprite(5, 10,0,4).setMoving(true).toRight();
        e.get(EntitySprite.DEFAULT_MOVING_RIGHT).cropSpriteRelative(0, -3, 0, 3);
        return e;

    }

    public static Sprite RAYMOND() {

        selected_NPC = "Raymond";
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

        EntitySprite e =  new EntitySprite(entitySprites).cropSprite(5, 10,0,4).setMoving(true).toRight();
        e.get(EntitySprite.DEFAULT_MOVING_RIGHT).cropSpriteRelative(0, -3, 0, 3);
        return e;

    }
}
