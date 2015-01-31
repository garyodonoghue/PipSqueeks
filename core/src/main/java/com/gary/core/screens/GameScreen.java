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
import com.gary.core.collision.CollisionHelper;
import com.gary.core.controllers.MyControllerListener;
import com.gary.core.objects.PipSqueak;
import com.gary.core.objects.Platform;

public class GameScreen implements Screen {

	public static Vector2 center = new Vector2();
	public static List<PipSqueak> pipSqueaks;
	private static final int PIXELS_PER_METER = 10;
	public static List<Platform> platforms;

	private final Texture basePlatformTexture;

	private final Texture bodyLeftTexture;

	private final Texture bodyRightTexture;
	private final OrthographicCamera camera;
	private final Box2DDebugRenderer debugRenderer;
	private final Texture feetLeftTexture;
	private final Texture feetRightTexture;
	private final PipSqueaksGame game;

	private final MyControllerListener myControllerListener;
	private final Texture platformTexture;
	private final int screenHeight;
	private final int screenWidth;
	private final SpriteBatch spriteBatch;

	private final Texture weaponLeftTexture;
	private final Texture weaponRightTexture;

	private World world;

	private final int worldHeight;

	private final int worldWidth;

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

		bodyLeftTexture = new Texture(Gdx.files.internal("emo_left.png"));
		feetLeftTexture = new Texture(Gdx.files.internal("emo_shoes_left.png"));

		bodyRightTexture = new Texture(Gdx.files.internal("emo_right.png"));
		feetRightTexture = new Texture(
				Gdx.files.internal("emo_shoes_right.png"));

		weaponRightTexture = new Texture(
				Gdx.files.internal("bazooka_right.png"));
		weaponLeftTexture = new Texture(Gdx.files.internal("bazooka_left.png"));

		platformTexture = new Texture(Gdx.files.internal("platform.png"));
		basePlatformTexture = new Texture(
				Gdx.files.internal("platform_base.png"));

		pipSqueaks = new ArrayList<PipSqueak>();

		myControllerListener = new MyControllerListener();
		Controllers.addListener(myControllerListener);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

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
	public void render(float delta) {
		// set background colour to sky blue
		Gdx.gl.glClearColor(135 / 255f, 206 / 255f, 235 / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world, camera.combined.scale(PIXELS_PER_METER,
				PIXELS_PER_METER, PIXELS_PER_METER));

		this.spriteBatch.begin();
		camera.update();
		spriteBatch.setProjectionMatrix(camera.combined);

		updatePipSqueakSprites();
		updatePlatformSprites();

		world.step(Gdx.app.getGraphics().getDeltaTime(), 10, 10);
		world.clearForces();

		this.spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		world = new World(new Vector2(0.0f, -120.0f), true);

		LevelGenerator.generateLevel(world);

		createAndAssignPipsToControllers();

		world.setContactListener(new CollisionHelper());
	}

	private void createAndAssignPipsToControllers() {

		// PipSqueak pip1 = new PipSqueak(this.world, new Vector2(50, 50));
		// pipSqueaks.add(pip1);

		if (Controllers.getControllers() != null) {
			for (int i = 0; i < Controllers.getControllers().size; i++) {
				// move this into assignControllers() method
				PipSqueak pip = new PipSqueak(this.world, new Vector2(50, 50));
				pip.setController(Controllers.getControllers().get(i));
				pipSqueaks.add(pip);
			}
		}
	}

	private void updatePipSqueakSprites() {
		for (PipSqueak pipSqueak : pipSqueaks) { // the order needs to go back
													// foot, body, front foot,
													// so that there is the
													// correct perception of
													// depth
			if (pipSqueak.facingRight) {
				updateSprite(new Sprite(new Sprite(weaponRightTexture)),
						spriteBatch, PIXELS_PER_METER, pipSqueak.getWeapon()
								.getWeaponBody());
				updateSprite(new Sprite(bodyRightTexture), spriteBatch,
						PIXELS_PER_METER, pipSqueak.getPipBody());
				updateSprite(new Sprite(feetRightTexture), spriteBatch,
						PIXELS_PER_METER, pipSqueak.getFoot().getBody());
			} else {
				updateSprite(new Sprite(new Sprite(weaponLeftTexture)),
						spriteBatch, PIXELS_PER_METER, pipSqueak.getWeapon()
								.getWeaponBody());
				updateSprite(new Sprite(new Sprite(bodyLeftTexture)),
						spriteBatch, PIXELS_PER_METER, pipSqueak.getPipBody());
				updateSprite(new Sprite(new Sprite(feetLeftTexture)),
						spriteBatch, PIXELS_PER_METER, pipSqueak.getFoot()
								.getBody());
			}
		}
	}

	private void updatePlatformSprites() {
		for (Platform platform : GameScreen.platforms) {
			if (platform.isBasePlatform()) {
				updateSprite(new Sprite(basePlatformTexture), spriteBatch,
						PIXELS_PER_METER, platform.getBody());
			} else {
				updateSprite(new Sprite(platformTexture), spriteBatch,
						PIXELS_PER_METER, platform.getBody());
			}
		}
	}

	public static void setSpritePosition(Sprite sprite, int PIXELS_PER_METER,
			Body body) {

		sprite.setPosition(
				PIXELS_PER_METER * body.getWorldCenter().x - sprite.getWidth()
						/ 2, PIXELS_PER_METER * body.getWorldCenter().y
						- sprite.getHeight() / 2);

		sprite.setRotation(MathUtils.radiansToDegrees * body.getAngle());
	}

	public static void updateSprite(Sprite sprite, SpriteBatch spriteBatch,
			int PIXELS_PER_METER, Body body) {
		if (sprite != null && spriteBatch != null && body != null) {
			setSpritePosition(sprite, PIXELS_PER_METER, body);
			sprite.draw(spriteBatch);
		}
	}
}
