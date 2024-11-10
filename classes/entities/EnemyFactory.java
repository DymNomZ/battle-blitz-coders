package classes.entities;

import java.util.List;

public class EnemyFactory{
	public static Enemy createEnemy(
		Enemy.EnemySpecies species,
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
		}
		return null;
	}
}
