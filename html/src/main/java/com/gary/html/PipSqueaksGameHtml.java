package com.gary.html;

import com.gary.core.PipSqueaksGame;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class PipSqueaksGameHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new PipSqueaksGame();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
