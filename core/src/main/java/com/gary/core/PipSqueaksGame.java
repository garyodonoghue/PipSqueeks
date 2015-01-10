package com.gary.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gary.core.screens.GameScreen;
import com.gary.core.screens.MenuScreen;

public class PipSqueaksGame extends Game {
	public static float delta;
	SpriteBatch batch;
	float elapsed;

	private GameScreen gameScreen;
	private MenuScreen menuScreen;
	
	public int screenHeight = 1080;

	public int screenWidth = 1920;
	Texture texture;

	@Override
	public void create() 
	{
		createScreens();
		setScreen(gameScreen);
		//setScreen(menuScreen);
	}
	
	private void createScreens() 
	{
		this.gameScreen = new GameScreen(this);
		//this.menuScreen = new MenuScreen(this);
	}
}