package com.gary.core.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gary.core.PipSqueaksGame;
import com.gary.core.controllers.Xbox360Pad;
import com.gary.core.objects.Player;
 
public class MenuScreen implements Screen
{
    private Stage stage = new Stage();
    private SpriteBatch spriteBatch;
    private PipSqueaksGame game;
	private int screenWidth;
	private int screenHeight;
	private final int worldHeight;
	private static final int PIXELS_PER_METER = 10;
	private static final String PRESS_START = " Press Start";
	private final int worldWidth;
	private OrthographicCamera camera;
	private Texture logoTexture;
	private float blinkDelta = 0f;
	private static int numberOfPlayers = 0;
	private List<Player> listPlayers;
	private CharacterSelectScreen characterSelectScreen;
	
	public MenuScreen(PipSqueaksGame pipSqueaksGame) {
		this.spriteBatch = new SpriteBatch();
		this.logoTexture = new Texture(Gdx.files.internal("title.png"));
		this.game = pipSqueaksGame;
		
		this.screenWidth = this.game.screenWidth;
		this.screenHeight = this.game.screenHeight;

		this.worldHeight = screenHeight / PIXELS_PER_METER;
		this.worldWidth = this.screenWidth / PIXELS_PER_METER;

		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.screenWidth, screenHeight);
	}

	@Override
	public void render(float delta) {
		
		blinkDelta = blinkDelta + delta;
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.spriteBatch.begin();
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		spriteBatch.draw(logoTexture, this.screenWidth/4, this.screenHeight/10, 1000, 1000);
		
		this.spriteBatch.end();
		
		stage.act(delta);
	    stage.draw();
	    
	    blinkText();
	    
	    pollControllers();
	    
	}

	private void pollControllers() {
		for (Controller controller : Controllers.getControllers()) {
		    Gdx.app.log("controller connected with name: ", controller.getName());
		    
		    //check if player pressed the start button, create and assign a new player
		    if(controller.getButton(Xbox360Pad.BUTTON_START)){
		    	Gdx.app.log("Start button pressed","");
		    	Gdx.app.log("Number of connected controllers: ", ""+Controllers.getControllers().size);
		    	
		    	Player player = new Player(controller);
		    	if(listPlayers.contains(player)) break;
		    	numberOfPlayers += 1;
		    	listPlayers.add(player);
		    }
		    
		    if(controller.getButton(Xbox360Pad.BUTTON_A)){
		    	Gdx.app.log("A button pressed","");
		    	Gdx.app.log("Start button pressed","");
		    	
		    	this.characterSelectScreen = new CharacterSelectScreen();
		    	this.characterSelectScreen.setNumberOfPlayers(numberOfPlayers);
		    }
		}
	}

	private void blinkText() {
		if(blinkDelta > 0.5){
	    	blinkDelta = 0;
	    	
	    	for(int i = numberOfPlayers; i < stage.getActors().size; i++){
	    		Actor actor = stage.getActors().get(i);
	    		
	    		if(actor.isVisible()){
	    			actor.setVisible(false);
	    		}
	    		else{
	    			actor.setVisible(true);
	    		}
	    	}
	    }
	}
	
	@Override
	public void resize(int width, int height) {
		Skin skin = new Skin( Gdx.files.internal( "defaultskin.json" ));
		
        Label welcomeLabel = new Label( "Press the A button to advance!", skin );
        
        welcomeLabel.setFontScale(2);
        welcomeLabel.setX(width/3);
        welcomeLabel.setY(( 80 ));
        stage.addActor( welcomeLabel );
        
        // add the player identifiers in each corner, which will blink 
        Label P1 = new Label( "P1" + PRESS_START, skin );
        
        P1.setFontScale(2);
        P1.setX(10);
        P1.setY(( height-60));
        stage.addActor( P1 );
        
        Label P2 = new Label( "P2" + PRESS_START, skin );
        
        P2.setFontScale(2);
        P2.setX(width-250);
        P2.setY(( height-60));
        stage.addActor( P2 );
        
        Label P3 = new Label( "P3" + PRESS_START, skin );
        
        P3.setFontScale(2);
        P3.setX(10);
        P3.setY((30));
        stage.addActor( P3 );
        
        Label P4 = new Label( "P4" + PRESS_START, skin );
        
        P4.setFontScale(2);
        P4.setX(width-250);
        P4.setY((30));
        stage.addActor( P4 );
	}
	@Override
	public void show() {
	}
	
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}