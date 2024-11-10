package src;

import classes.GUI.General;
import classes.sprites.GUISprites;
import java.awt.*;
import java.awt.event.*;
import javax.swing.ImageIcon;

public class MouseHandler implements MouseMotionListener, MouseWheelListener, MouseListener{
	int mouse_x;
	int mouse_y;
	Point mouse_location_on_screen;

	boolean left_is_pressed  = false;

	@Override
	public void mouseReleased(MouseEvent e){
		left_is_pressed = false;
	}


	@Override
	public void mousePressed(MouseEvent e){
		int mouse_code = e.getButton();
		if(mouse_code == 1)
			left_is_pressed = true;

		if(e.getSource() == General.Buttons.PLAY){
			General.Panels.MAIN.remove(0);
			General.Panels.MAIN.add(General.Panels.CHARACTER_SELECT);
			General.Panels.CHARACTER_SELECT.requestFocusInWindow();
			((CharSelectionPanel) General.Panels.CHARACTER_SELECT).start_main_thread();
			General.Panels.MAIN.revalidate();
		}

		if(e.getSource() == General.Buttons.START){
			GamePanel game = new GamePanel();
			General.Panels.MAIN.remove(0);
			General.Panels.MAIN.add(game);
			game.requestFocusInWindow();
			game.start_main_thread();
			General.Panels.MAIN.revalidate();
		}

		//handle button dynamic GUI
		handleButtons(e);

	}

	@Override
	public void mouseEntered(MouseEvent e){

		if(e.getSource() == General.Buttons.PLAY) {
			General.Buttons.PLAY.setIcon(new ImageIcon(
				GUISprites.Buttons.PLAY_S.getScaledInstance(General.SCALE_VALUES[0], General.SCALE_VALUES[1], Image.SCALE_SMOOTH))
			);
		}

		if(e.getSource() == General.Buttons.START) {
			General.Buttons.START.setIcon(new ImageIcon(
				GUISprites.Buttons.START_S.getScaledInstance(
					General.DEF_BTN_DIMENSIONS[0], General.DEF_BTN_DIMENSIONS[1], Image.SCALE_SMOOTH)
				)
			);
		}
	}

	@Override
	public void mouseExited(MouseEvent e){

		if(e.getSource() == General.Buttons.PLAY) {
			General.Buttons.PLAY.setIcon(new ImageIcon(
				GUISprites.Buttons.PLAY_U.getScaledInstance(General.SCALE_VALUES[0], General.SCALE_VALUES[1], Image.SCALE_SMOOTH))
			);
		}

		if(e.getSource() == General.Buttons.START) {
			General.Buttons.START.setIcon(new ImageIcon(
				GUISprites.Buttons.START_U.getScaledInstance(
					General.DEF_BTN_DIMENSIONS[0], General.DEF_BTN_DIMENSIONS[1], Image.SCALE_SMOOTH)
				)
			);
		}
	}

	private void handleButtons(MouseEvent e){

		if(e.getSource() == General.Buttons.DYMES){
			General.Buttons.DYMES.setIcon(new ImageIcon(
				GUISprites.Buttons.DYMES_S.getScaledInstance(
					General.DEF_BTN_DIMENSIONS[0], General.DEF_BTN_DIMENSIONS[1], Image.SCALE_SMOOTH)
				)
			);
			CharacterHandler.setSelected_character("Dymes");
		}
		else {
			General.Buttons.DYMES.setIcon(new ImageIcon(
				GUISprites.Buttons.DYMES_U.getScaledInstance(
					General.DEF_BTN_DIMENSIONS[0], General.DEF_BTN_DIMENSIONS[1], Image.SCALE_SMOOTH)
				)
			);
		}

		if(e.getSource() == General.Buttons.SETH){
			General.Buttons.SETH.setIcon(new ImageIcon(
				GUISprites.Buttons.SETH_S.getScaledInstance(
					General.DEF_BTN_DIMENSIONS[0], General.DEF_BTN_DIMENSIONS[1], Image.SCALE_SMOOTH)
				)
			);
			CharacterHandler.setSelected_character("Seth");
		}
		else {
			General.Buttons.SETH.setIcon(new ImageIcon(
				GUISprites.Buttons.SETH_U.getScaledInstance(
					General.DEF_BTN_DIMENSIONS[0], General.DEF_BTN_DIMENSIONS[1], Image.SCALE_SMOOTH)
				)
			);
		}

		if(e.getSource() == General.Buttons.ZILLION){
			General.Buttons.ZILLION.setIcon(new ImageIcon(
				GUISprites.Buttons.ZILLION_S.getScaledInstance(
					General.DEF_BTN_DIMENSIONS[0], General.DEF_BTN_DIMENSIONS[1], Image.SCALE_SMOOTH)
				)
			);
			CharacterHandler.setSelected_character("Zillion");
		}
		else {
			General.Buttons.ZILLION.setIcon(new ImageIcon(
				GUISprites.Buttons.ZILLION_U.getScaledInstance(
					General.DEF_BTN_DIMENSIONS[0], General.DEF_BTN_DIMENSIONS[1], Image.SCALE_SMOOTH)
				)
			);
		}

		if(e.getSource() == General.Buttons.RAYMOND){
			General.Buttons.RAYMOND.setIcon(new ImageIcon(
				GUISprites.Buttons.RAYMOND_S.getScaledInstance(
					General.DEF_BTN_DIMENSIONS[0], General.DEF_BTN_DIMENSIONS[1], Image.SCALE_SMOOTH)
				)
			);
			CharacterHandler.setSelected_character("Raymond");
		}
		else {
			General.Buttons.RAYMOND.setIcon(new ImageIcon(
				GUISprites.Buttons.RAYMOND_U.getScaledInstance(
					General.DEF_BTN_DIMENSIONS[0], General.DEF_BTN_DIMENSIONS[1], Image.SCALE_SMOOTH)
				)
			);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e){

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
