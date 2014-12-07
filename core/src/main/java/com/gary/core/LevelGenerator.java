package com.gary.core;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gary.core.objects.Platform;

public class LevelGenerator {

	public static void generateLevel(World world){
		//generate platforms
		for(int i = 1; i<6; i++){
			for(int j = 1; j<6; j++){
				new Platform(world, new Vector2(30*j, i*30), 6f);
			}
		}
		
		//generate ground platform with gap in the middle
		new Platform(world, new Vector2(45, 10), 40f);
		new Platform(world, new Vector2(145, 10), 40f);
	}
}
