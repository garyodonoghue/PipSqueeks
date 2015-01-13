package com.gary.core.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.gary.core.LevelGenerator;
import com.gary.core.PipSqueaksGame;
import com.gary.core.controllers.MyControllerListener;
import com.gary.core.objects.PipSqueak;
import com.gary.core.objects.Platform;

public class GameScreen implements Screen {

	private static final int PIXELS_PER_METER = 10;
	private PipSqueaksGame game;
	private int screenWidth;
	private int screenHeight;
	private final int worldHeight;
	private final int worldWidth;
	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Box2DDebugRenderer debugRenderer;
	private World world;
	public static Vector2 center = new Vector2();
	public static List<PipSqueak> pipSqueaks;
	private Texture bodyLeftTexture;
	private Texture feetLeftTexture;
	
	private Texture bodyRightTexture;
	private Texture feetRightTexture;
	private Texture weaponTexture;
	private Texture platformTexture;
	
	public  static List<Platform> platforms;

	private MyControllerListener myControllerListener;

	public GameScreen(PipSqueaksGame game) {
		this.game = game;

		this.screenWidth = this.game.screenWidth;
		this.screenHeight = this.game.screenHeight;

		this.worldHeight = screenHeight / PIXELS_PER_METER;
		this.worldWidth = this.screenWidth / PIXELS_PER_METER;

		center = new Vector2(worldWidth / 2, worldHeight / 2);

		this.camera = new OrthographicCamera();
		this.camera.setToOrtho(false, this.screenWidth, screenHeight);

		this.spriteBatch = new SpriteBatch();

		this.debugRenderer = new Box2DDebugRenderer();
		
		bodyLeftTexture  = new Texture(Gdx.files.internal("emo_left.png"));
		feetLeftTexture  = new Texture(Gdx.files.internal("emo_shoes_left.png"));
		
		bodyRightTexture  = new Texture(Gdx.files.internal("emo_right.png"));
		feetRightTexture  = new Texture(Gdx.files.internal("emo_shoes_right.png"));
		
		weaponTexture  = new Texture(Gdx.files.internal("emo_shoes_right.png"));

		platformTexture = new Texture(Gdx.files.internal("platform.png"));
		pipSqueaks = new ArrayList<PipSqueak>();
		
		myControllerListener = new MyControllerListener();
		Controllers.addListener(myControllerListener);
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		debugRenderer.render(world, camera.combined.scale(PIXELS_PER_METER,	PIXELS_PER_METER, PIXELS_PER_METER));
				
		this.spriteBatch.begin();
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		updatePipSqueakSprites();
		//updatePlatformSprites();
		
		world.step(Gdx.app.getGraphics().getDeltaTime(), 10, 10);
		world.clearForces();
		
		this.spriteBatch.end();
	}

	private void updatePlatformSprites() {
		for(Platform platform : GameScreen.platforms){
				updateSprite(new Sprite(platformTexture), spriteBatch, PIXELS_PER_METER, platform.getBody());
		}
	}

	private void updatePipSqueakSprites() {
		for(PipSqueak pipSqueak : pipSqueaks){ //the order needs to go back foot, body, front foot, so that there is the correct perception  of depth
			if(pipSqueak.facingRight){
				updateSprite(new Sprite(new Sprite(weaponTexture)), spriteBatch, PIXELS_PER_METER, pipSqueak.getWeapon().getWeaponBody());			

				updateSprite(new Sprite(feetRightTexture), spriteBatch, PIXELS_PER_METER, pipSqueak.getBackFoot());
				updateSprite(new Sprite(bodyRightTexture), spriteBatch, PIXELS_PER_METER, pipSqueak.getPipBody());
				updateSprite(new Sprite(feetRightTexture), spriteBatch, PIXELS_PER_METER, pipSqueak.getFrontFoot());		
			}
			else{
				updateSprite(new Sprite(new Sprite(weaponTexture)), spriteBatch, PIXELS_PER_METER, pipSqueak.getWeapon().getWeaponBody());			
				
				updateSprite(new Sprite(new Sprite(feetLeftTexture)), spriteBatch, PIXELS_PER_METER, pipSqueak.getBackFoot());
				updateSprite(new Sprite(new Sprite(bodyLeftTexture)), spriteBatch, PIXELS_PER_METER, pipSqueak.getPipBody());
				updateSprite(new Sprite(new Sprite(feetLeftTexture)), spriteBatch, PIXELS_PER_METER, pipSqueak.getFrontFoot());				
			}
		}
	}
	public static void updateSprite(Sprite sprite, SpriteBatch spriteBatch,
			int PIXELS_PER_METER, Body body) {
		if (sprite != null && spriteBatch != null && body != null) {
			setSpritePosition(sprite, PIXELS_PER_METER, body);
			sprite.draw(spriteBatch);
		}
	}
	
	public static void setSpritePosition(Sprite sprite, int PIXELS_PER_METER,
			Body body) {

		sprite.setPosition(
				PIXELS_PER_METER * body.getWorldCenter().x - sprite.getWidth()
						/ 2, PIXELS_PER_METER * body.getWorldCenter().y
						- sprite.getHeight() / 2);

		sprite.setRotation((MathUtils.radiansToDegrees * body.getAngle()));
	}	
	
	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void show() {
		world = new World(new Vector2(0.0f, -50.0f), true);
		
		LevelGenerator.generateLevel(world);
		
		createAndAssignPipsToControllers();
	}

	private void createAndAssignPipsToControllers() {
		
//		PipSqueak pip1 = new PipSqueak(this.world, new Vector2(50, 50));
//		pipSqueaks.add(pip1);		
		
		if(Controllers.getControllers() != null)
		{
			for(int i = 0; i<Controllers.getControllers().size; i++)
			{
				//move this into assignControllers() method
				PipSqueak pip = new PipSqueak(this.world, new Vector2(50, 50));
				pip.setController(Controllers.getControllers().get(i));
				pipSqueaks.add(pip);			
			}
		}
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
