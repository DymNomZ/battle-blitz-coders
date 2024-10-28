package classes.entities;

import java.awt.Graphics;

public class CameraEntity extends PanelEntity{

	public CameraEntity(int width, int height){
		super(width, height);
	}
	
	@Override
	public void move(int offsetX, int offsetY) {
        x += offsetX;
        y += offsetY;
    }

	@Override
	public void display(Graphics g, CameraEntity cam) {
		throw new RuntimeException("Displaying camera is a major dumb2 move betch");
	}
}
