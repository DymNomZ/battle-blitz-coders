package classes.entities;

import interfaces.CollisionHandler;
import interfaces.EntityCollidable;

import java.util.Random;

public abstract class ProjectileEntity extends PanelEntity implements CollisionHandler, EntityCollidable{
	int speed;
	double angle;
	public boolean is_player_friendly, is_colliding = false;
	int damage;


	public ProjectileEntity(int x, int y, int width, int height, int speed, boolean is_player_friendly, int damage, String spritePath){
		super(x, y, width, height, spritePath);
		this.speed = speed;
		this.is_player_friendly = is_player_friendly;
		this.damage = damage;
	}

	public int dealDamage(){ return damage; }

	@Override
	public void onCollision(){
		CollisionHandler.super.onCollision();
		is_colliding = true;
	}
	@Override
	public void onEntityCollision(PanelEntity e){
		onCollision();
	}
	public boolean is_colliding(){
		return is_colliding;
	}

	public abstract void executeProjectileBehavior();

	public static class TemporaryBullet extends ProjectileEntity{
		public TemporaryBullet(int x, int y, int mouse_location_x, int mouse_location_y){
			super(x,y,32,32,10,true,10, "sprites/projectile_entity/temp_bullet.png");
			this.angle = calculateAngle(x,y,mouse_location_x,mouse_location_y);
		}
		public void executeProjectileBehavior(){
			deltaY = (int) Math.round(Math.sin(angle) * speed);
			deltaX = (int) Math.round(Math.cos(angle) * speed);
		}
	}
	public static class VirusSpit extends ProjectileEntity{
		public VirusSpit(int x, int y, PanelEntity player){
			super(x, y+16, 32, 32, 10, false, 10, "sprites/projectile_entity/virus_projectile.png");
			this.angle = calculateAngle(player);
			this.angle += ((new Random().nextDouble(0,11) - 5) * 0.174533);
		}



		@Override
		public void executeProjectileBehavior(){
			deltaY = (int) Math.round(Math.sin(angle) * speed);
			deltaX = (int) Math.round(Math.cos(angle) * speed);
		}
	}

}
