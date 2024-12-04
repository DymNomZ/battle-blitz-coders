package classes.entities;

import classes.GUI.PlayerHealthBar;
import classes.items.Item;
import classes.sprites.GUISprites;
import interfaces.EntityCollidable;
import java.awt.Graphics;
import java.util.Random;
import src.CharacterHandler;
import src.GamePanel;
import src.KeyHandler;

public class Player extends MapEntity implements EntityCollidable{

	private final KeyHandler inputs;
	private final Item hotbar_items[] = new Item[5];

	public PlayableCharacters character;

	int speed = 8, curr_slot = 0, selected_slot = 0, size = 0;

	//This is in nanoseconds
	public int shooting_cooldown;

	public void decrementCooldowns(int value){
		if(shooting_cooldown != 0){

			// System.out.println(shooting_cooldown);
		}
		shooting_cooldown -= value;
		if(shooting_cooldown < 0) shooting_cooldown = 0;

		//Add more if necessary
	}
	@Override
	public void onEntityCollision(PanelEntity e){
		if(e instanceof ProjectileEntity && !((ProjectileEntity)e).is_player_friendly){
			damage(((ProjectileEntity) e).dealDamage());
			((ProjectileEntity) e).onCollision();
		}
		if(e instanceof Enemy){
			damage(((Enemy)e).attack());
		}
	}
	public void updateInfo(){
		double percentage = (double) this.getHit_points() / this.getMax_hit_points();
		PlayerHealthBar.update_display_health_bar(percentage);
	}

	public void initiateShootingCooldown(int cooldown_nanoseconds){
		shooting_cooldown = cooldown_nanoseconds;
	}

	public Player(String name, int hit_points, int screenWidth, int screenHeight, int side, int allowance, KeyHandler inputs){
		super();
		super.setHit_points(hit_points);
		super.setMax_hit_points(hit_points);
		super.setX(screenWidth);
		super.setY(screenHeight);
		super.setDimensions(side,side + allowance);
		character = CharacterHandler.getCharacter(name);
		buffer = character.getSprite();

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

	public Item getCurrentItem(){
		return hotbar_items[selected_slot];
	}

	public void removeItem(){
		hotbar_items[selected_slot] = null;
		size--;
		if(selected_slot <= curr_slot) curr_slot = selected_slot;
		else findNextFree();
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
					GUISprites.Hotbar.SELECTED, draw_start, GamePanel.SCREEN_HEIGHT - 100, 
					GamePanel.TILE_SIZE, GamePanel.TILE_SIZE, null
				);
			}
			if((i != null)){
				i.display(g, draw_start, GamePanel.SCREEN_HEIGHT - 100);
			}
			count++;
			draw_start += (GamePanel.TILE_SIZE + (GamePanel.TILE_SIZE / 4));
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
		checkEntitySprites();

	}


}
