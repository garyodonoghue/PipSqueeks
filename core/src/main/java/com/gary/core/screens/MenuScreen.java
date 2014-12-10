package com.gary.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.gary.core.PipSqueaksGame;
 
public class MenuScreen implements Screen
{
    private Stage stage = new Stage();
    private SpriteBatch spriteBatch;
    private PipSqueaksGame game;
	private int screenWidth;
	private int screenHeight;
	private final int worldHeight;
	private static final int PIXELS_PER_METER = 10;
	private final int worldWidth;
	private OrthographicCamera camera;
	//private Texture logoTexture;

	public MenuScreen(PipSqueaksGame pipSqueaksGame) {
		this.spriteBatch = new SpriteBatch();
		//this.logoTexture = new Texture(Gdx.files.internal("logo.png"));
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
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		this.spriteBatch.begin();
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		//spriteBatch.draw(logoTexture, 0, 0);
		
		this.spriteBatch.end();
		
		stage.act(delta);
	    stage.draw();
	    
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
        Label P1 = new Label( "Player 1", skin );
        
        P1.setFontScale(2);
        P1.setX(10);
        P1.setY(( height-60));
        stage.addActor( P1 );
        
        Label P2 = new Label( "Player 2", skin );
        
        P2.setFontScale(2);
        P2.setX(width-150);
        P2.setY(( height-60));
        stage.addActor( P2 );
        
        Label P3 = new Label( "Player 3", skin );
        
        P3.setFontScale(2);
        P3.setX(10);
        P3.setY((30));
        stage.addActor( P3 );
        
        Label P4 = new Label( "Player 4", skin );
        
        P4.setFontScale(2);
        P4.setX(width-150);
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