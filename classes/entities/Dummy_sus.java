package classes.entities;

import classes.items.Item;
import classes.sprites.EntitySprite;
import classes.sprites.GUISprites;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

import classes.sprites.Sprite.AnimatedSprite;
import interfaces.EntityCollidable;
import src.KeyHandler;
import src.Panel;

public class Dummy_sus extends MapEntity implements EntityCollidable{

	private final KeyHandler inputs;
	private Item hotbar_items[] = new Item[5];
	int speed = 8, curr_slot = 0, selected_slot = 0, size = 0;
	public int shooting_cooldown;
	Timer shooting_cooldown_timer;

	public void decrementShootingCooldown(int value){
		shooting_cooldown -= value;
		if(shooting_cooldown < 0) shooting_cooldown = 0;
	}
	public void initiateShootingCooldown(int cooldown){
		shooting_cooldown = cooldown;
		this.shooting_cooldown_timer = new Timer(10, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				System.out.println(shooting_cooldown);
				decrementShootingCooldown(1);
				if(shooting_cooldown == 0) shooting_cooldown_timer.stop();
			}
		});
		shooting_cooldown_timer.start();
	}

	public Dummy_sus(int hit_points, int screenWidth, int screenHeight, int side, KeyHandler inputs){
		super(hit_points, screenWidth, screenHeight, side + 150, side + 200);

		ArrayList<AnimatedSprite> entitySprites = new ArrayList<>();
		String[] filenames = new String[3];

		filenames[0] = "sprites/character_sprites/base sprites/zillion_1.png";
		filenames[1] = "sprites/character_sprites/base sprites/zillion_2.png";
		filenames[2] = "sprites/character_sprites/base sprites/zillion_alt_2.png";
		entitySprites.add(new AnimatedSprite(2000000000, filenames));

		filenames[0] = "sprites/character_sprites/zillion_left_F0.png";
		filenames[1] = "sprites/character_sprites/zillion_left_F1.png";
		filenames[2] = "sprites/character_sprites/zillion_left_F-1.png";
		entitySprites.add(new AnimatedSprite(500000000, filenames));

		filenames[0] = "sprites/character_sprites/zillion_right_F0.png";
		filenames[1] = "sprites/character_sprites/zillion_right_F1.png";
		filenames[2] = "sprites/character_sprites/zillion_right_F-1.png";
		entitySprites.add(new AnimatedSprite(500000000, filenames));

		buffer = new EntitySprite(entitySprites).cropSprite(5, 10,2,8);

		this.x /= 2;
		this.y /= 2;
		this.inputs = inputs;
	}

	public void printHotbarItems(){
		if(inputs.debug_print){
			for(int i = 0; i < 5; i++){
				if(hotbar_items[i] != null) System.out.print(hotbar_items[i].getName() + " ");
				else System.out.print("null ");
			}
			System.out.println();
			System.out.println("Current slot free: " + (curr_slot + 1));
			System.out.println("Selected slot: " + (selected_slot + 1));
			System.out.println("Hotbar item size: " + size);
			inputs.debug_print = false;
		}
	}

	public ItemEntity dropItems(){

		int item_entity_key = new Random().nextInt(0, 100);

		if(inputs.drop_item){
			if(size != 0){
				Item dropped;

				inputs.drop_item = false;

				if((hotbar_items[selected_slot] != null)){
					
					dropped = hotbar_items[selected_slot];
					hotbar_items[selected_slot] = null;

					System.out.println(selected_slot + " " + dropped.getName());
					
					size--;

					if(selected_slot <= curr_slot) curr_slot = selected_slot;
					else findNextFree();

					return new ItemEntity(item_entity_key, x, y, dropped, false);
				}
			}
		} 

		return null;
	}

	//temp damage
    public int attack(){
        return 10;
    }

	public int getSize(){
		return size;
	}

	public void addItem(Item item){

		
		hotbar_items[curr_slot] = item;
		if(curr_slot >= 5) curr_slot = 0;

		size++;

		if(size != 5) findNextFree();
	}

	private void findNextFree(){
		//traverse until stick
		while((hotbar_items[curr_slot] != null)){
			//System.out.println("Traversing");
			curr_slot++;
			if(curr_slot >= 5) curr_slot = 0;
		}
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
		int draw_start = 448;
		int count = 0;
		for(Item i : hotbar_items){
			if(count == selected_slot){
				g.drawImage(
					GUISprites.Hotbar.SELECTED, draw_start, Panel.SCREEN_HEIGHT - 100, 
					Panel.TILE_SIZE, Panel.TILE_SIZE, null
				);
			}
			if((i != null)){
				i.display(g, draw_start, Panel.SCREEN_HEIGHT - 100);
			}
			count++;
			draw_start += (Panel.TILE_SIZE + (Panel.TILE_SIZE / 4));
		}
	}

	public void move(){
		//heheh - dymes
		hotbarSlotHandler();

		if(inputs.up_pressed == inputs.down_pressed) {
			deltaY = 0;
		}
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
			speed = 6;
		}

		EntitySprite sprite = (EntitySprite) buffer;
		if (deltaY == 0 && deltaX == 0) {
			sprite.setMoving(false);
		} else {
			if (deltaX > 0) {
				sprite.setMoving(true).toRight();
			} else if (deltaX < 0) {
				sprite.setMoving(true).toLeft();
			}
		}
	}
}
