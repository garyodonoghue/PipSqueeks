package com.gary.core.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.gary.core.collision.CollisionInfo;
import com.gary.core.collision.CollisionObjectType;

public class Bullet {

	private Body bulletBody;
	private final Body weaponBody;

	private final World world;

	public Bullet(Body weaponBody, World world) {
		this.weaponBody = weaponBody;
		this.world = world;

		BodyDef bulletDef = new BodyDef();
		bulletDef.type = BodyType.KinematicBody;

		bulletDef.position.set(weaponBody.getWorldCenter().x,
				weaponBody.getWorldCenter().y);

		this.bulletBody = this.world.createBody(bulletDef);

		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(0.25f, 0.25f);
		fixtureDef.shape = boxShape;
		fixtureDef.isSensor = true;

		bulletBody.createFixture(fixtureDef);

		boxShape.dispose();

		this.bulletBody.setUserData(new CollisionInfo("Bullet collision",
				CollisionObjectType.Bullet, this));
	}

	public Body getBulletBody() {
		return bulletBody;
	}

	public void setBulletBody(Body bulletBody) {
		this.bulletBody = bulletBody;
	}
}
