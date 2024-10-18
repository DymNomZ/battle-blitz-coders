package classes.entities;

import src.KeyHandler;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Dummy_sus extends PanelEntity{
	int speed = 8;

	public Dummy_sus(int screenWidth, int screenHeight, int side){
		super(screenWidth, screenHeight, side, side);

		try{
			this.buffer = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/fidget_spinner.png"));
		}catch(IOException e){
			System.out.println("Suck deez nuts");
		}

		this.x /= 2;
		this.y /= 2;
	}

	public void move(KeyHandler inputs){
		if(inputs.up_pressed == inputs.down_pressed) deltaY = 0;
		else if(inputs.up_pressed)
			deltaY = -speed;
		else if(inputs.down_pressed)
			deltaY = speed;

		if(inputs.right_pressed == inputs.left_pressed){
			deltaX = 0;
		}
		else if(inputs.left_pressed)
			deltaX = -speed;
		else if(inputs.right_pressed)
			deltaX = speed;

		if (inputs.lShift_pressed) {
			speed = 12;
		} else {
			speed = 8;
		}
	}
}
