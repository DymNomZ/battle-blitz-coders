package classes.sprites;

import classes.Asset.Sprite.AnimatedSprite;
import java.util.List;

public abstract class BossEnemySprite extends EnemySprite{
    public BossEnemySprite(List<AnimatedSprite> sprites) {
        super(sprites);
    }

    public static class SirSeratoBossSprites extends BossEnemySprite{
        public static final int SIR_SERATO_SPAWN_PEASHOOTER = 0;
        public static final int SIR_SERATO_SPAWN_SQUASH = 4;
        public static int SIR_SERATO_THROW_FIREBALLS = 2;

        protected boolean spawning_peashooter = false;
        protected boolean spawning_squash = false;
        protected boolean throwing_fireballs = false;
        protected boolean is_idle = true;

        public SirSeratoBossSprites(List<AnimatedSprite> sprites) {
            super(sprites);
        }

        public void entranceCycle(){
            spawning_peashooter = false;
            spawning_squash = false;
            throwing_fireballs = false;
            is_idle = false;
        }

        public void spawnPeashooter(){
            spawning_peashooter = true;
            spawning_squash = false;
            throwing_fireballs = false;
            is_idle = false;
        }

        public void spawnSquash(){
            spawning_peashooter = false;
            spawning_squash = true;
            throwing_fireballs = false;
            is_idle = false;
        }

        public void throwFireballs(){
            spawning_peashooter = false;
            spawning_squash = false;
            throwing_fireballs = true;
            is_idle = false;
        }

        public void idle(){
            spawning_peashooter = false;
            spawning_squash = false;
            throwing_fireballs = false;
            is_idle = true;
        }


        @Override
        protected int getState() {
            if(spawning_peashooter){
                return SIR_SERATO_SPAWN_PEASHOOTER;
            } else if(spawning_squash){
                return SIR_SERATO_SPAWN_SQUASH;
            } else if(throwing_fireballs){
                return SIR_SERATO_THROW_FIREBALLS;
            } else {
                return 3;
            }
        }
    }
}
