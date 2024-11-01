package src;

import java.awt.*;
import java.awt.event.*;

public class MouseHandler implements MouseMotionListener, MouseWheelListener, MouseListener{
	int mouse_x;
	int mouse_y;
	Point mouse_location_on_screen;

	boolean left_is_pressed  = false;

	@Override
	public void mouseClicked(MouseEvent e){

	}

	@Override
	public void mousePressed(MouseEvent e){
		int mouse_code = e.getButton();
		if(mouse_code == 1)
			left_is_pressed = true;
	}

	@Override
	public void mouseReleased(MouseEvent e){
		left_is_pressed = false;
	}

	@Override
	public void mouseEntered(MouseEvent e){

	}

	@Override
	public void mouseExited(MouseEvent e){
//		System.out.println("OOP u moved out the window, GAME OVER!!");
//		System.exit(69420);
	}

	@Override
	public void mouseDragged(MouseEvent e){

	}

	@Override
	public void mouseMoved(MouseEvent e){

	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e){

	}


}
