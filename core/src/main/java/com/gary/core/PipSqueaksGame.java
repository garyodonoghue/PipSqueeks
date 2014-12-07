package com.gary.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Game;
import com.gary.core.screens.GameScreen;

public class PipSqueaksGame extends Game {
	public static float delta;
	SpriteBatch batch;
	float elapsed;

	private GameScreen gameScreen;

	public int screenHeight = 1080;

	public int screenWidth = 1920;
	Texture texture;

	@Override
	public void create() 
	{
		createScreens();
		setScreen(gameScreen);
	}
	
	private void createScreens() 
	{
		this.gameScreen = new GameScreen(this);
	}
}