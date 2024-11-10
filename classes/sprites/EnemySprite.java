package classes.sprites;

import classes.Asset.Sprite.AnimatedSprite;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

public class EnemySprite extends EntitySprite{

	public static final int DEFAULT_ATTACKING_LEFT = 3;
	public static final int DEFAULT_ATTACKING_RIGHT = 4;
	protected boolean isAttacking;


	public void setAttacking(boolean attacking){
		isAttacking = attacking;
	}

	public EnemySprite(List<AnimatedSprite> sprites){
		super(sprites);
	}

	public EntitySprite toAttacking(){
		isAttacking = true;
		return this;
	}

	@Override
	public EnemySprite invert() {
		super.invert();
		return this;
	}

	@Override
	protected int getState() {
		if(isAttacking){
			if(state != DEFAULT_ATTACKING_LEFT && state != DEFAULT_ATTACKING_RIGHT){
				sprites.get(state).reset();
			}
			state = is_right ? DEFAULT_ATTACKING_RIGHT : DEFAULT_ATTACKING_LEFT;
			return state;
		} else {
			return super.getState();
		}
	}

	@Override
	public BufferedImage getSprite() {
		return sprites.get(getState()).getSprite();
	}

	@Override
	public EnemySprite display(Graphics g, int x, int y, int width, int height) {
		sprites.get(getState()).display(g, x, y, width, height);
		return this;
	}
}
