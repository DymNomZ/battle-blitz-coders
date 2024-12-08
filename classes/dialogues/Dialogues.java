package classes.dialogues;

import classes.GUI.General;
import classes.entities.BossEnemy;
import classes.entities.NPC;
import classes.entities.Player;
import classes.sprites.GUISprites;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import src.CharacterHandler;
import src.GamePanel;
import src.PlayerData;
import src.Utils;

public class Dialogues {

    public static int FONT_SIZE, TEMP_SECOND, PREV_SECOND;

    public static int[] PHASE_SECONDS;
    public static int PHASE_STATE, LIMIT;
    public static int npc_dnum, pop_up_dnum, serato_dnum;

    public static final String[][] NPC_DIALOGUES = {
        {"Hey you!"},
        {"No way you're here too?"},
        {"I just woke up here just before you did!", "I think someone imprisoned us here!"},
        {"I'm not sure but it seems like", "they're in this big castle up ahead."},
        {"Who is it? I dunno, but they must be really", "scary and cunning to do something like this."},
        {"To reach him, you need to get past three", "different stages fighting different enemies."},
        {"Here take this, it should help you fight", "the minions that will try to stop you."},
        {"The controls are simple. LMB to attack,", "RMB to eat, 1-5 to navigate your items"},
        {"and Q to drop a selected item.", "I wish you nothing but goodluck!"},
        {"Huh? Why can't I go with you? Because we", "didn't implement multiplayer yet obviously."},
        {"Anyway, get moving already!"},
        {"You've made it! That was some", "really good fighting you did back there!"},
        {"Yano naa nako diri? Don't ask."},
        {"Anyways, once you enter that door", "there's no turning back."},
        {"Goodluck bro!", "Beat whoever is behind all this!"},
        {"Huwat lang ko diris gawas lods"},
        {"WTF NGANO NAAY PEASHOOTERS", "NGA LAVA MANI DIRI"},
        {"BUNG NAA NAPUY LAIN"},
        {"Wow you really beat him! Congrats!"},
        {"This means we can finally leave!"},
        {"I can leave too even though I just", "stayed here right? ...Right?"},
    };

    public static final String[][] POP_UP_DIALOGUES = {
        {"Talk to your classmate. Press F"},
        {"Stage complete! Proceed to the next"},
        {"STAGE 1: Defeat 10 Slimes to proceed."},
        {"STAGE 2: Defeat 10 Viruses to proceed."},
        {"FINAL STAGE: Defeat 15 enemies to proceed."},
        {"Talk to your classmate at the end of the hallway."},
        {"You obtained a ranged weapon! Using it allows you to", "shoot projectiles in the direction you're facing."}
    };

    public static final String[][] SERATO_DIALOGUES = {
        {"Hmmm, so you've finally arrived."},
        {"You must be wondering", "why I trapped you here."},
        {"Well it's because I'm tired of your", "code being so inefficient and hard to read."},
        {"Can you imagine trying to check something", "like that every time we have an activity? YUCKS."},
        {"That's why I trapped you here", "so you can never code again."},
        {"If you want to leave,", "you need to defeat me first."},
        {"Oh yes, I suppose", "I'll say this for tradition's sake."},
        {"How are you feeling today?"},
        {"new Plant.Peashooter()!"},
        {"Ga tago ra lagi na siya?", "Anyways, Peashooter.attack()!"},
        {"new Plant.Squash()!"},
        {"Sabaa lagi anang isa sa.", "*Ahem, Squash.attack()!"},
        {"*sigh*", "So boring."},
        {"...You actually beat me..."},
        {"Hmph.... that was...."},
        {"Cool and normal."},
        {"You may go."},
        {"/teleport " + CharacterHandler.getSelected_character()}
    };

    public static void initializeValues(){
        FONT_SIZE = 35;
        PHASE_SECONDS = new int[]{0, 0, 0, 0, 0, 0, 0};
        PHASE_STATE = 0;
        TEMP_SECOND = 0;
        PREV_SECOND = 0;
        npc_dnum = 0;
        pop_up_dnum = 0;
        serato_dnum = 0;
        LIMIT = 99;
    }

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

    public static void handlePopUpDialogues(Graphics g, int second, Player d1){

        int font_size = 45;
        int x = (GamePanel.SCREEN_WIDTH / 2) - 425;
        int y = 185;
        
        //handle different states
        switch(PHASE_STATE){
            case 0 -> intitialSequence(g, second);
            case 2 -> pop_up_dnum = 2;
            case 3 -> pop_up_dnum = 3;
            case 4 -> {
                pop_up_dnum = 4;
                font_size = 40;
            }
            case 5 -> {
                pop_up_dnum = 5;
                font_size = 35;
                LIMIT = 16;
            }
            case 6 -> {
                if(d1.y <= 700) bossInitial(g);
                else serato_dnum = 0;
            }
            case 7 -> {
                npc_dnum = 16;
                LIMIT = 21;
            }
            case 10 -> {
                npc_dnum = 17;
                if(BossEnemy.spawn_squash) bossSquash(g, second);
                //if(BossEnemy.trigger_half_health) bossHalfHealth(g, second);
                if(BossEnemy.trigger_defeat) bossDefeat(g, second);
            }
            case 11 -> {
                if(BossEnemy.trigger_defeat) bossDefeat(g, second);
            }
            case 12 -> npc_dnum = 18;
        }

        //pick up ranged weapon for the first time
        if(PlayerData.trigger_range_info && !PlayerData.range_info_shown) firstPickRanged(g, second);

        if(PlayerData.cleared_stage) clearedStage(g, second);

        if(PHASE_STATE > 6 && PHASE_STATE < 10) bossProper(g, second);

        if(second != PREV_SECOND){
            PREV_SECOND = second;
            if(PHASE_STATE > 1  && PlayerData.trigger_spawning) PHASE_SECONDS[pop_up_dnum]++;
        }

        if(PHASE_SECONDS[pop_up_dnum] < 3 && PHASE_STATE > 1 && PlayerData.trigger_spawning){

            display(
                g, POP_UP_DIALOGUES[pop_up_dnum], 
                font_size, x, y
            );
        }
        
    }

    static void bossHalfHealth(Graphics g, int second){

        if(second != PREV_SECOND) TEMP_SECOND++;

        if(TEMP_SECOND < 6){
            display(g, SERATO_DIALOGUES[12],
            45, (GamePanel.SCREEN_WIDTH / 2) - 325, 165);
            BossEnemy.drawHead(g, "Sir Serato Smile");
        }
        else{
            TEMP_SECOND = 0;
            BossEnemy.half_health_dialogue = true;
        }
    }

    static void bossDefeat(Graphics g, int second){
        
        PHASE_STATE = 11;

        if(second != PREV_SECOND) TEMP_SECOND++;

        if(TEMP_SECOND < 3){
            display(g, SERATO_DIALOGUES[13],
            45, (GamePanel.SCREEN_WIDTH / 2) - 300, 185);
            BossEnemy.drawHead(g, "Sir Serato Blink");
        }
        else if(TEMP_SECOND < 6){
            display(g, SERATO_DIALOGUES[14],
            45, (GamePanel.SCREEN_WIDTH / 2) - 300, 185);
            BossEnemy.drawHead(g, "Sir Serato Blink");
        }
        else if(TEMP_SECOND < 9){
            display(g, SERATO_DIALOGUES[15],
            45, (GamePanel.SCREEN_WIDTH / 2) - 300, 185);
            BossEnemy.drawHead(g, "Sir Serato Blink");
        }
        else if(TEMP_SECOND < 12){
            display(g, SERATO_DIALOGUES[16],
            45, (GamePanel.SCREEN_WIDTH / 2) - 300, 185);
            BossEnemy.drawHead(g, "Sir Serato Blink");
        }
        else if(TEMP_SECOND < 15){
            display(g, SERATO_DIALOGUES[17],
            45, (GamePanel.SCREEN_WIDTH / 2) - 300, 185);
            BossEnemy.drawHead(g, "Sir Serato Blink");
        }
        else{
            TEMP_SECOND = 0;
            BossEnemy.defeat_dialogue = true;
            PHASE_STATE = 12;
        }
    }

    static void bossInitial(Graphics g){

        if(serato_dnum == 7){
            //move to next state
            PHASE_STATE = 7;
        }
        else{
            display(g, SERATO_DIALOGUES[serato_dnum]);
            BossEnemy.drawHead(g, "Sir Serato Hidden");
        }

    }

    static void bossSquash(Graphics g, int second){

        if(second != PREV_SECOND) TEMP_SECOND++;

        if(TEMP_SECOND < 3){
            display(g, SERATO_DIALOGUES[10],
            45, (GamePanel.SCREEN_WIDTH / 2) - 300, 185);
            BossEnemy.drawHead(g, "Sir Serato Normal");
        }
        else if(TEMP_SECOND < 6){
            display(g, NPC_DIALOGUES[npc_dnum],
            45, (GamePanel.SCREEN_WIDTH / 2) - 300, 185);
            NPC.drawHead(g);
        }
        else if(TEMP_SECOND < 9){
            display(g, SERATO_DIALOGUES[11],
            45, (GamePanel.SCREEN_WIDTH / 2) - 325, 165);
            BossEnemy.drawHead(g, "Sir Serato Rolleyes");
        }
        else{
            TEMP_SECOND = 0;
            BossEnemy.spawn_squash = false;
            BossEnemy.squash_dialogue = true;
        }
    }

    static void bossProper(Graphics g, int second){

        if(second != PREV_SECOND) TEMP_SECOND++;

        //System.out.println(TEMP_SECOND);

        if(TEMP_SECOND < 3){
            display(g, SERATO_DIALOGUES[7],
            45, (GamePanel.SCREEN_WIDTH / 2) - 300, 185);
            BossEnemy.drawHead(g, "Sir Serato Smile");
        }
        else if(TEMP_SECOND < 6){
            display(g, SERATO_DIALOGUES[8],
            45, (GamePanel.SCREEN_WIDTH / 2) - 300, 185);
            BossEnemy.drawHead(g, "Sir Serato Normal");
            //move to next state
            PHASE_STATE = 8;
        }
        else if(TEMP_SECOND < 9){
            display(g, NPC_DIALOGUES[npc_dnum],
            40, (GamePanel.SCREEN_WIDTH / 2) - 275, 165);
            NPC.drawHead(g);
        }
        else if(TEMP_SECOND < 12){
            display(g, SERATO_DIALOGUES[9],
            45, (GamePanel.SCREEN_WIDTH / 2) - 325, 165);
            BossEnemy.drawHead(g, "Sir Serato Eyebrow");
            //move to next state
            PHASE_STATE = 9;
        }
        else{
            TEMP_SECOND = 0;
            PHASE_STATE = 10;
        }
    }

    public static void handleNPCDialogues(Graphics g){

        switch(npc_dnum){
            case 10 -> {
                //move to next state
                PHASE_STATE = 2;
                LIMIT = 11;
            }
            case 15 -> {
                //move to next state
                PHASE_STATE = 6;
                PlayerData.boss_ready = true;
            }
            case 21 -> PHASE_STATE = 14;
        }

        if(npc_dnum < LIMIT){
            display(g, NPC_DIALOGUES[npc_dnum]);
            NPC.drawHead(g);
        }

    }

    public static void handleReset(){

        switch(PHASE_STATE){
            case 1 -> npc_dnum = 0;
            case 2 -> npc_dnum = 9;
            case 5 -> npc_dnum = 11;
            case 6 -> npc_dnum = 15;
            case 12 -> npc_dnum = 18;
        }

    }

    public static void intitialSequence(Graphics g, int second){

        //System.out.println(PREV_SECOND + " " + second); 
        
        if(second != PREV_SECOND) PHASE_SECONDS[0]++;

        //System.out.println(PHASE_SECONDS[0]);

        if(PHASE_SECONDS[0] < 3){
            display(g, NPC_DIALOGUES[npc_dnum]);
            NPC.drawHead(g);
        }
        else if(PHASE_SECONDS[0] < 6){
            display(g, POP_UP_DIALOGUES[pop_up_dnum],
            45, (GamePanel.SCREEN_WIDTH / 2) - 375, 185);
        }
    }

    public static void firstPickRanged(Graphics g, int second){

        if(second != PREV_SECOND) TEMP_SECOND++;

        if(TEMP_SECOND < 5){
            display(
                g, POP_UP_DIALOGUES[6], 
                30, (GamePanel.SCREEN_WIDTH / 2) - 400, 160
            );
        }
        else{
            PlayerData.trigger_range_info = true;
            PlayerData.range_info_shown = true;
            TEMP_SECOND = 0;
        }
    }

    public static void clearedStage(Graphics g, int second){

        if(second != PREV_SECOND) TEMP_SECOND++;

        if(TEMP_SECOND < 3){
            display(
                g, POP_UP_DIALOGUES[1], 
                45, (GamePanel.SCREEN_WIDTH / 2) - 400, 185
            );
        }
        else{
            PlayerData.cleared_stage = false;
            TEMP_SECOND = 0;
        }
    }

}
