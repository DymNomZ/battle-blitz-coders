package classes.entities;

import classes.Asset.Sprite.Sprite;
import classes.GUI.General;
import classes.sprites.BossEnemySprite;
import interfaces.RangedAttacker;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import src.GamePanel;

public class BossEnemy extends Enemy{

    public static boolean spawn_squash = false, squash_dialogue = false;
    public static boolean trigger_half_health = false, half_health_dialogue = false;
    public static boolean trigger_defeat = false, defeat_dialogue = false;

    public BossEnemy(String name, int speed, int attack_stat, int hit_points, int x, int y, int width, int height, String enemy_type, long key) {
        super(name, speed, attack_stat, hit_points, x, y, width, height, enemy_type, key);
    }

    public static void drawHead(Graphics g, String kind){

        Sprite raw_head = EnemyHandler.getHead(kind);
        BufferedImage head = raw_head.getSprite();

        g.drawImage(head.getScaledInstance(General.SCALE_VALUES[5], General.SCALE_VALUES[5], Image.SCALE_SMOOTH), 
                (GamePanel.SCREEN_WIDTH / 2) - 590, -10, null
        );
    }

    public static class SirSerato extends BossEnemy implements RangedAttacker {
        boolean boss_fight_started = false;
        boolean initial_cycle_start = false;
        protected GamePanel gamePanelpointer;
        protected List<ProjectileEntity> projectiles_pointer;
        private Timer actionTimer;
        boolean is_shooting_fireballs = false;
        Timer fireball_loop = new Timer();

        int tile_x = 0;
        int tile_y = 0;

        public SirSerato(int x, int y,int side,long key, GamePanel gamePanelpointer, List<ProjectileEntity> projectiles_pointer){
            super("Sir Serato",0,20,1000,x,y,side,side,"Boss Enemy",key);
            this.actionTimer = new Timer();
            buffer = EnemyHandler.SIR_SERATO();
            this.gamePanelpointer = gamePanelpointer;
            this.projectiles_pointer = projectiles_pointer;
            tile_x = x / GamePanel.TILE_SIZE;
            tile_y = y / GamePanel.TILE_SIZE;
        }
        
        public void startBossFight(){
            boss_fight_started = true;
        }

        @Override
        public void executeEnemyBehavior(PanelEntity e) {
            if(boss_fight_started && !initial_cycle_start){
                System.out.println("STARTING BOSS ATTACK CYCLE");
                initial_cycle_start = true;
                initiateAttackCycle();
            }
        }

        private void initiateAttackCycle() {

            if(super.getHit_points() <= (super.getMax_hit_points() / 2) && !trigger_half_health){
                trigger_half_health = true;
            }
            actionTimer.schedule(new TimerTask() {
                private int step = 0;
                @Override
                public void run() {
                    if (step == 0) {
                        peashooterAttackCycle();
                        step++;
                    } else if (step == 1) {
                        if(!squash_dialogue) spawn_squash = true;
                        squashAttackCycle();
                        step++;
                    } else if(step == 2){
                        is_shooting_fireballs = true;
                        throwFireballsCycle();
                        step++;
                    } else {
                        step = 0; // Reset steps to repeat the cycle if needed
                    }
                }
            }, 0, 8000); // Wait 8 seconds between each task execution
        }

        public void peashooterAttackCycle() {
            is_shooting_fireballs = false;

            removeSquashes();

            spawnPeashooters();

            ((BossEnemySprite.SirSeratoBossSprites)buffer).spawnPeashooter();
        }

        public void removePeashooters(){
            for(Enemy e : gamePanelpointer.getSpawned_enemies()){
                if(e instanceof Peashooter){
                    gamePanelpointer.getSpawned_enemies().remove(e);
                }
            }
        }

        public void spawnPeashooters(){
            for(int i = 0;i < 15;i++){
                for(int j = 0;j < 15;j++){
                    if(i==0 && j % 3 == 01){
                        gamePanelpointer.spawnEnemy(
                                tile_x - 5 + i,
                                tile_y - 3 + j,
                                0,
                                EnemyFactory.EnemySpecies.PEASHOOTER_RIGHT);
                    }
                    if(i == 14 && j % 3 == 01){
                        gamePanelpointer.spawnEnemy(
                                tile_x - 5 + i,
                                tile_y - 3 + j,
                                0,
                                EnemyFactory.EnemySpecies.PEASHOOTER_LEFT);
                    }
                    if(j == 0 && i % 3 == 01){
                        gamePanelpointer.spawnEnemy(
                                tile_x - 5 + i,
                                tile_y - 3 + j,
                                0,
                                EnemyFactory.EnemySpecies.PEASHOOTER_DOWN);
                    }
                }
            }
        }

        public void removeSquashes(){
            for(Enemy e : gamePanelpointer.getSpawned_enemies()){
                if(e instanceof Squash){
                    gamePanelpointer.getSpawned_enemies().remove(e);
                }
            }
        }

        private void squashAttackCycle() {

            for(int i = 0;i < 20;i++){
                gamePanelpointer.spawnEnemy(
                        tile_x + (new Random().nextInt(0,20)-10),
                        tile_y + (new Random().nextInt(0,20)-10),
                        0,
                        EnemyFactory.EnemySpecies.SQUASH);
            }

            ((BossEnemySprite.SirSeratoBossSprites)buffer).spawnSquash();
            System.out.println("Spawning Squashes");
        }


        private void throwFireballsCycle(){
            ((BossEnemySprite.SirSeratoBossSprites)buffer).throwFireballs();
            System.out.println("Throwing fireballs");
            fireball_loop.schedule(new TimerTask() {

                @Override
                public void run() {
                    shootProjectile(null);
                }
            }, 0, 2000);
        }

        public void stopCycle() {
            actionTimer.cancel();
            fireball_loop.cancel();
        }

        @Override
        public void shootProjectile(PanelEntity target) {
            if(!is_shooting_fireballs) return;
            for(int i = 0;i <= 5;i++) {
                for (int j = 0; j <= 5; j++) {
                    ProjectileEntity projectile = new ProjectileEntity.Fireball(x, y, calculateAngle(3, 3, i, j));
                    projectiles_pointer.add(projectile);
                }
            }
        }
    }
}
