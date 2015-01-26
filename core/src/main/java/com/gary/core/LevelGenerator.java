package com.gary.core;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gary.core.objects.Platform;
import com.gary.core.screens.GameScreen;

public class LevelGenerator {

	private static List<Platform> platforms = new ArrayList<Platform>();

	public static void generateLevel(World world) {

		// generate platforms
		for (int i = 1; i < 6; i++) {
			for (int j = 1; j < 6; j++) {
				platforms.add(new Platform(world, new Vector2(30 * j, i * 30),
						6f, false));
			}
		}

		// generate ground platform with gap in the middle
		platforms.add(new Platform(world, new Vector2(45, 10), 40f, true));
		platforms.add(new Platform(world, new Vector2(145, 10), 40f, true));

		GameScreen.platforms = platforms;
	}
}
