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

	private final PipSqueak pipSqueak;
	private final Vector2 pipBodyCenter;

	private Body weaponBody;
	private final World world;

	public Weapon(PipSqueak pipSqueak, World world) {
		pipBodyCenter = pipSqueak.getPipBody().getWorldCenter();
		this.world = world;
		this.pipSqueak = pipSqueak;
		this.createWeaponSensor();
	}

	public void changeAim(float value) {
		if (this.pipSqueak.isFacingRight()) {
			this.getWeaponBody().setAngularVelocity(-value);
		} else {
			this.getWeaponBody().setAngularVelocity(value);
		}
	}

	public void createWeaponSensor() {
		BodyDef weaponSensorDef = new BodyDef();
		weaponSensorDef.type = BodyType.DynamicBody;

		// TODO want to position this at 'shoulder height'
		weaponSensorDef.position.set(pipBodyCenter.x + 1, pipBodyCenter.y + 2);
		weaponSensorDef.fixedRotation = false; // want to be able to 'rotate'
												// the gun when aiming
		weaponSensorDef.gravityScale = 0;

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
		float gunAngle = this.getWeaponBody().getAngle();
		float xComponent = (float) (1000 * Math.cos(gunAngle));
		float yComponent = (float) (1000 * Math.sin(gunAngle));

		if (pipSqueak.facingRight) {
			bullet.getBulletBody().setLinearVelocity(xComponent, yComponent);
		} else { // negate the x component when faclng left as the bullets would
					// come out the back of the gun otherwise
			bullet.getBulletBody().setLinearVelocity(-xComponent, -yComponent);
		}
	}

	private void createWeaponJoint() {
		RevoluteJointDef weaponJoint = new RevoluteJointDef();

		weaponJoint.initialize(this.pipSqueak.getPipBody(), this.weaponBody,
				new Vector2(pipBodyCenter.x, pipBodyCenter.y + 1));
		weaponJoint.collideConnected = true;
		weaponJoint.enableLimit = false;

		world.createJoint(weaponJoint);
	}

}
