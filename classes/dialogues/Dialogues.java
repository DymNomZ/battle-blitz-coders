package classes.dialogues;

import classes.GUI.General;
import classes.entities.NPC;
import classes.sprites.GUISprites;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import src.GamePanel;
import src.PlayerData;
import src.Utils;

public class Dialogues {

    public static int FONT_SIZE = 35;

    public static int[] DIALOGUE_NUMBERS = {0, 0};
    public static int PHASE_STATE = 0;

    public static final String[][] NPC_DIALOGUES = {
        {"Hey you!"},
        {"No way you're here too?"},
        {"I just woke up here just before you did!", "I think someone imprisoned us here!"},
        {"I'm not sure but it seems like", "they're in this big castle up ahead."},
        {"Who is it? I dunno, but they must be really", "scary and cunning to do something like this."},
        {"To reach him, you need to get past three", "different stages fighting different enemies."},
        {"Here take this, it should help you fight", "the minions that will try to stop you."},
        {"The controls are simple. LMB to attack,", "1-5 to navigate your items, and Q to drop them."},
        {"I wish you nothing but goodluck!"},
        {"Huh? Why can't I go with you? Because we", "didn't implement multiplayer yet obviously."},
        {"Anyway, get moving already!"}
    };

    public static final String[][] POP_UP_DIALOGUES = {
        {"Talk to your classmate. Press F"},
        {"STAGE 1: Defeat 10 Slimes to proceed."},
        {"STAGE 2: Defeat 10 Viruses to proceed."},
        {"FINAL STAGE: Defeat 20 enemies to proceed."},
        {"You obtained a ranged weapon! Using it allows you to", "shoot projectiles in the direction you're facing."}
    };

    public static void display(Graphics g, String[] dialogues, int font_size, int x, int y){

        g.drawImage(
            GUISprites.Miscellaneous.DOSBOX_DIALOGUE_BOX.getScaledInstance(
            General.SCALE_VALUES[2], General.SCALE_VALUES[3], Image.SCALE_SMOOTH),
            (GamePanel.SCREEN_WIDTH / 2) - 500, 75, null
        );

        Font font = Utils.DOSBOX_font.deriveFont(Font.PLAIN, font_size);
        g.setFont(font);
        g.setColor(Color.WHITE);

        for(String dialogue : dialogues){
            g.drawString(dialogue, x, y);
            y += 50;
        }

    }

    private static int calculateLength(String[] dialogues){
        //dynamic adjust text heheheh wink wink ;)
        int len_total = 0;
        for(String dialogue : dialogues){
            len_total += dialogue.length();
        }

        return len_total;
    }

    public static void display(Graphics g, String[] dialogues){

        int x = (GamePanel.SCREEN_WIDTH / 2) - 300;
        int y = 160;

        g.drawImage(
            GUISprites.Miscellaneous.DOSBOX_DIALOGUE_BOX.getScaledInstance(
            General.SCALE_VALUES[2], General.SCALE_VALUES[3], Image.SCALE_SMOOTH),
            (GamePanel.SCREEN_WIDTH / 2) - 500, 75, null
        );

        FONT_SIZE = (calculateLength(dialogues) > 75) ? 30 : 35;

        Font font = Utils.DOSBOX_font.deriveFont(Font.PLAIN, FONT_SIZE);
        g.setFont(font);
        g.setColor(Color.WHITE);

        for(String dialogue : dialogues){
            g.drawString(dialogue, x, y);
            y += 50;
        }

    }

    public static void handlePopUpDialogues(Graphics g, int seconds){
        
        //handle different states
        if(PHASE_STATE == 0) intitialSequence(g, seconds);
        else{

            switch(PHASE_STATE){
                case 2 -> {
                    DIALOGUE_NUMBERS[1] = 1;
                    if(PlayerData.trigger_range_info && !PlayerData.range_info_shown) {
                        DIALOGUE_NUMBERS[1] = 4;
                    }
                }
                case 3 -> DIALOGUE_NUMBERS[1] = 2;
            }

            if(seconds > 0 && seconds < 3){
                int size = (calculateLength(Dialogues.POP_UP_DIALOGUES[Dialogues.DIALOGUE_NUMBERS[1]]) > 75) ? 30 : 45;
                Dialogues.display(
                    g, Dialogues.POP_UP_DIALOGUES[Dialogues.DIALOGUE_NUMBERS[1]], 
                    size, (GamePanel.SCREEN_WIDTH / 2) - 425, (size == 45) ? 185 : 150
                );
            }
        }
        
    }

    public static void handleNPCDialogues(Graphics g){

        if(Dialogues.DIALOGUE_NUMBERS[0] == 9){
            Dialogues.PHASE_STATE = 2;
        }

        if(Dialogues.DIALOGUE_NUMBERS[0] < 11) {
            Dialogues.display(g, Dialogues.NPC_DIALOGUES[Dialogues.DIALOGUE_NUMBERS[0]]);
            NPC.drawHead(g);
        }

    }

    public static void handleReset(){

        switch(PHASE_STATE){
            case 1 -> Dialogues.DIALOGUE_NUMBERS[0] = 0;
            case 2 -> Dialogues.DIALOGUE_NUMBERS[0] = 9;
        }

    }

    public static void intitialSequence(Graphics g, int seconds){
        if(seconds < 3){
            Dialogues.display(g, Dialogues.NPC_DIALOGUES[Dialogues.DIALOGUE_NUMBERS[0]]);
            NPC.drawHead(g);
        }
        else if(seconds < 6){
            Dialogues.display(
                g, Dialogues.POP_UP_DIALOGUES[Dialogues.DIALOGUE_NUMBERS[1]], 
                45, (GamePanel.SCREEN_WIDTH / 2) - 375, 185
            );
        }
    }

}
