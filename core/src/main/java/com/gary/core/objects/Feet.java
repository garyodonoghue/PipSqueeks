package com.gary.core.objects;

import com.badlogic.gdx.physics.box2d.Body;

public class Feet {

	private Body body;
	private PipSqueak pip;

	public Feet(PipSqueak pipSqueak) {
		this.setPip(pipSqueak);
	}

	public Body getBody() {
		return body;
	}

	public PipSqueak getPip() {
		return pip;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public void setPip(PipSqueak pip) {
		this.pip = pip;
	}
}
