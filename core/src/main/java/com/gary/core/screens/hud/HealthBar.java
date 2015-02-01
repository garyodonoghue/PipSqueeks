package com.gary.core.screens.hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

public class HealthBar extends Actor {

	private final NinePatchDrawable healthBarBackground;

	private final NinePatchDrawable healthBar;

	float health;

	private final int playerNumber;

	Label playerLabel;

	public HealthBar(int playerNumber, int screenWidth) {
		TextureAtlas skinAtlas = new TextureAtlas(
				Gdx.files.internal("uiskin.atlas"));
		NinePatch loadingBarBackgroundPatch = new NinePatch(
				skinAtlas.findRegion("default-round"), 5, 5, 4, 4);
		NinePatch loadingBarPatch = new NinePatch(
				skinAtlas.findRegion("default-round-down"), 5, 5, 4, 4);
		healthBar = new NinePatchDrawable(loadingBarPatch);
		healthBarBackground = new NinePatchDrawable(loadingBarBackgroundPatch);
		health = 1f;

		this.playerNumber = playerNumber;

		Skin skin = new Skin(Gdx.files.internal("defaultskin.json"));
		playerLabel = new Label("Player " + playerNumber, skin);
		playerLabel.setFontScale(2);
		playerLabel.setY(800);
	}

	public void damageHealth() {
		if (this.health <= 0.1) {
			this.health = 0f;

		} else {
			this.health = health - 0.01f;
		}
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {

		healthBarBackground.draw(batch, getX(), getY(), getWidth()
				* getScaleX(), getHeight() * getScaleY());

		if (this.health > 0) {
			healthBar.draw(batch, getX(), getY(), health * getWidth()
					* getScaleX(), getHeight() * getScaleY());
		}

		this.playerLabel.draw(batch, getX());
	}
}
