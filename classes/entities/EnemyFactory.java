package classes.entities;

import java.util.List;
import java.util.Random;

public class EnemyFactory{
	public enum EnemySpecies{
		VIRUS, PEASHOOTER,PEASHOOTER_LEFT,PEASHOOTER_RIGHT,PEASHOOTER_DOWN, SQUASH, SLIME
	}
	public static Enemy createEnemy(
		EnemySpecies species,
		int x, int y, int side,long key,
	    List<ProjectileEntity> projectiles
	){
		switch(species){
			case SLIME -> {
				return new Enemy.Slime(x,y,side,key);
			}
			case VIRUS -> {
				return new Enemy.Virus(x,y,side, key, projectiles);
			}
			//USE THIS CASE TO GENERATE PEASHOOTER LOOKING AT A RANDOM DIRECTION - Set H
			case PEASHOOTER -> {
				int rand = new Random().nextInt(1,4);
				Enemy.Peashooter.PeashooterDirection direction = Enemy.Peashooter.PeashooterDirection.DOWN;
				switch(rand){
					case 1 -> {direction = Enemy.Peashooter.PeashooterDirection.DOWN;}
					case 2 -> {direction = Enemy.Peashooter.PeashooterDirection.RIGHT;}
					case 3 -> {direction = Enemy.Peashooter.PeashooterDirection.LEFT;}
				}
				return new Enemy.Peashooter(x,y,side, key, projectiles,direction);
			}
			case PEASHOOTER_DOWN -> {
				return new Enemy.Peashooter(x,y,side, key, projectiles, Enemy.Peashooter.PeashooterDirection.DOWN);
			}
			case PEASHOOTER_LEFT -> {
				return new Enemy.Peashooter(x,y,side, key, projectiles, Enemy.Peashooter.PeashooterDirection.LEFT);
			}
			case PEASHOOTER_RIGHT -> {
				return new Enemy.Peashooter(x,y,side, key, projectiles, Enemy.Peashooter.PeashooterDirection.RIGHT);
			}
			case SQUASH ->{
				return new Enemy.Squash(x,y,side,key);
			}
		}
		return null;
	}
}
