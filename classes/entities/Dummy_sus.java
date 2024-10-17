package classes.entities;

import src.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Dummy_sus extends PanelEntity{
	public Dummy_sus(int x, int y, int side, String bufferPath){
		super(x, y, side, side, null);

		try{
			this.buffer = ImageIO.read(getClass().getResourceAsStream(bufferPath));
		}catch(IOException e){
			System.out.println("Suck deez nuts");
		}

		this.x /= 2;
		this.x -= side / 2;
		this.y /= 2;
		this.y -= side / 2;
	}
	void move(KeyHandler inputs){
		if(inputs.up_pressed || inputs.down_pressed || inputs.left_pressed || inputs.right_pressed){

		}


		}
}
