package com.gary.core.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gary.core.collision.CollisionInfo;
import com.gary.core.collision.CollisionObjectType;

public class Platform {

	private final boolean basePlatform;
	private Body body;
	private final float length;

	private final World world;

	public Platform(World world, Vector2 startPos, float length,
			boolean basePlatform) {
		this.world = world;
		this.length = length;
		this.basePlatform = basePlatform;
		createBody(startPos);
	}

	private void createBody(Vector2 position) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(position);
		bodyDef.fixedRotation = true;

		this.body = world.createBody(bodyDef);

		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(length, 1.25f);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = boxShape;
		fixtureDef.restitution = 0.3f;
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.3f;

		body.createFixture(fixtureDef);

		boxShape.dispose();

		this.body.setUserData(new CollisionInfo("Hit platform",
				CollisionObjectType.Platform, this));
	}

	public Body getBody() {
		return body;
	}

	public boolean isBasePlatform() {
		return basePlatform;
	}

	public void setBody(Body body) {
		this.body = body;
	}
}
