package classes.entities;

import classes.items.Item;
import classes.items.Melee;
import java.awt.Graphics;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import src.KeyHandler;
import src.Panel;

public class Dummy_sus extends PanelEntity{

	private KeyHandler inputs;
	private Item hotbar_items[] = new Item[5];
	int speed = 8, curr_slot = 0, selected_slot = 0, size = 0;

	public Dummy_sus(int screenWidth, int screenHeight, int side, KeyHandler inputs){
		super(screenWidth, screenHeight, side - 30, side - 30);

		//full inv with stick temporarily
		while(curr_slot != 5){
			hotbar_items[curr_slot] = new Melee.Stick();
			curr_slot++;
		}
		curr_slot = 0;

		try{
			this.buffer = ImageIO.read(getClass().getResourceAsStream("../../assets/sprites/fidget_spinner.png"));
		}catch(IOException e){
			System.out.println("Suck deez nuts");
		}

		this.x /= 2;
		this.y /= 2;
		this.inputs = inputs;
	}

	public void printHotbarItems(){
		if(inputs.debug_print){
			for(int i = 0; i < 5; i++){
				if(hotbar_items != null) System.out.print(hotbar_items[i].getName() + " ");
				else System.out.print("null ");
			}
			System.out.println();
			System.out.println("Current slot free: " + curr_slot);
			System.out.println("Hotbar item size: " + size);
			inputs.debug_print = false;
		}
	}

	public ItemEntity dropItems(){
		Item dropped = new Melee.Stick();

		if(inputs.drop_item){

			if(!(hotbar_items[selected_slot] instanceof Melee.Stick)){
				dropped = hotbar_items[selected_slot];
				hotbar_items[selected_slot] = new Melee.Stick();
				if(size != 0) size--;
			}
				
			System.out.println(selected_slot);

			System.out.println(dropped.getName());
		} 

		inputs.drop_item = false;

		return new ItemEntity(
					new Random().nextInt(0, 100),
					x, y, dropped, false
				);
	}

	public int getSize(){
		return size;
	}

	public void addItem(Item item){
		//traverse until stick
		
		while(!(hotbar_items[curr_slot] instanceof Melee.Stick)){
			curr_slot++;
			if(curr_slot >= 5) curr_slot = 0;
		}

		hotbar_items[curr_slot++] = item;
		if(curr_slot >= 5) curr_slot = 0;

		size++;
	}

	private void hotbarSlotHandler(){

		if(inputs.one_pressed) selected_slot = 0;
		if(inputs.two_pressed) selected_slot = 1;
		if(inputs.three_pressed) selected_slot = 2;
		if(inputs.four_pressed) selected_slot = 3;
		if(inputs.five_pressed) selected_slot = 4;

		inputs.refreshHotbarKeys();
	}

	public void displayHotbarItems(Graphics g){
		int draw_start = 400;
		for(Item i : hotbar_items){
			if(!(i instanceof Melee.Stick)){
				i.display(g, draw_start, Panel.SCREEN_HEIGHT - 100);
			}
			draw_start += 100;
		}
	}

	public void move(){
		//heheh - dymes
		hotbarSlotHandler();

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
			speed = 96;
		} else {
			speed = 8;
		}
	}
}
