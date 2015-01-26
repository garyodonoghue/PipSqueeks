package com.gary.core.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.gary.core.collision.CollisionInfo;
import com.gary.core.collision.CollisionObjectType;

public class Weapon {

	private final Body pipBody;
	private final Vector2 pipBodyCenter;
	private Body weaponBody;
	private final World world;

	public Weapon(Body pipBody, World world) {
		pipBodyCenter = pipBody.getWorldCenter();
		this.world = world;
		this.pipBody = pipBody;
		this.createWeaponSensor();
	}

	public void changeAim(float value) {
		// TODO Need to rotate the weapon until it gets to direction analog
		// stick is pointing
		this.getWeaponBody().setAngularVelocity(value);

	}

	private void createWeaponJoint() {
		RevoluteJointDef weaponJoint = new RevoluteJointDef();

		weaponJoint.initialize(this.pipBody, this.weaponBody, new Vector2(
				pipBodyCenter.x + 3, pipBodyCenter.y - 1f));
		weaponJoint.collideConnected = true;
		weaponJoint.enableLimit = false;

		world.createJoint(weaponJoint);
	}

	public void createWeaponSensor() {
		BodyDef weaponSensorDef = new BodyDef();
		weaponSensorDef.type = BodyType.DynamicBody;

		// TODO want to position this at 'shoulder height'
		weaponSensorDef.position.set(pipBodyCenter.x, pipBodyCenter.y);
		weaponSensorDef.fixedRotation = false; // want to be able to 'rotate'
												// the gun when aiming

		this.weaponBody = world.createBody(weaponSensorDef);

		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(6f, 2.25f);
		fixtureDef.shape = boxShape;
		fixtureDef.isSensor = true;

		weaponBody.createFixture(fixtureDef);

		createWeaponJoint();

		boxShape.dispose();

		this.weaponBody.setUserData(new CollisionInfo("Hit gun",
				CollisionObjectType.Weapon, this));
	}

	public Body getWeaponBody() {
		return weaponBody;
	}

	public void setWeaponBody(Body weaponBody) {
		this.weaponBody = weaponBody;
	}

	public void shoot() {
		Bullet bullet = new Bullet(this.getWeaponBody(), world);
		// TODO Use the gun's angle to determine the trajectory of the bullet
		bullet.getBulletBody().setLinearVelocity(new Vector2(1000f, 10000f));
	}

}
