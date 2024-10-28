package classes.entities;

import javax.imageio.ImageIO;
import java.io.IOException;

public abstract class ProjectileEntity extends PanelEntity{
	int speed;
	double angle;
	boolean is_player_friendly;

	public ProjectileEntity(int x, int y, int width, int height, int speed, boolean is_player_friendly, double angle){
		super(x, y, width, height);
		this.speed = speed;
		this.is_player_friendly = is_player_friendly;
		this.angle = angle;
	}
	public static class TemporaryBullet extends ProjectileEntity{
		public TemporaryBullet(int x, int y, int mouse_location_x, int mouse_location_y){
			super(x,y,5,5,10,true,0.0);
			try{
				this.buffer = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/enemy_sprites/bo_o_ov_wa_er.png"));
			}catch(IOException e){
				System.out.println("Bullet Sprite Error!");
			}
		}
	}
}
