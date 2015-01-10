package com.gary.core.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.gary.core.objects.PipSqueak;

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
	private List<PipSqueak> pipSqueaks;
	private Texture bodyTexture;
	
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
		
		bodyTexture  = new Texture(Gdx.files.internal("frenchy.png"));
		
		pipSqueaks = new ArrayList<PipSqueak>();
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
		
		world.step(Gdx.app.getGraphics().getDeltaTime(), 10, 10);
		world.clearForces();
		
		this.spriteBatch.end();
	}

	private void updatePipSqueakSprites() {
		for(PipSqueak pipSqueak : pipSqueaks){
			updateSprite(new Sprite(new Sprite(bodyTexture)), spriteBatch, PIXELS_PER_METER, pipSqueak.getBackFoot());
			updateSprite(new Sprite(new Sprite(bodyTexture)), spriteBatch, PIXELS_PER_METER, pipSqueak.getPipBody());
			updateSprite(new Sprite(new Sprite(bodyTexture)), spriteBatch, PIXELS_PER_METER, pipSqueak.getFrontFoot());
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
		world = new World(new Vector2(0.0f, -80.0f), true);
		
		LevelGenerator.generateLevel(world);
		
		PipSqueak pip = new PipSqueak(this.world, center);
		pipSqueaks.add(pip);
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
