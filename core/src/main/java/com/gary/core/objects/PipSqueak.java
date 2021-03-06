package com.gary.core.objects;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.gary.core.collision.CollisionHelper;
import com.gary.core.collision.CollisionInfo;
import com.gary.core.collision.CollisionObjectType;
import com.gary.core.screens.hud.HealthBar;

public class PipSqueak {

	public CollisionHelper colHelper;
	private Controller controller;
	public boolean facingRight = true;
	private Feet feet;
	private Boolean airborn;

	private Body pipBody;

	float playerSize = 3f;
	private Weapon weapon;
	private final World world;

	private final HealthBar healthBar;

	public PipSqueak(World world, Vector2 startPos, HealthBar healthBar) {
		this.world = world;
		this.healthBar = healthBar;
		createBody(startPos);
		createPipSqueakFeet();

		colHelper = new CollisionHelper();

		weapon = new Weapon(this, world);
	}

	public void createFeetJointWhenFacingLeft(Vector2 pipBodyCenterPosition) {
		RevoluteJointDef revoFootJoint = new RevoluteJointDef();

		revoFootJoint.initialize(this.pipBody, this.getFoot().getBody(),
				new Vector2(pipBodyCenterPosition.x + 3,
						pipBodyCenterPosition.y - 1f));
		revoFootJoint.collideConnected = true;
		revoFootJoint.enableLimit = false;

		world.createJoint(revoFootJoint);
	}

	public void createFeetJointWhenFacingRight(Vector2 pipBodyCenterPosition) {
		RevoluteJointDef revoFootJoint = new RevoluteJointDef();

		revoFootJoint.initialize(this.pipBody, this.getFeet().getBody(),
				new Vector2(pipBodyCenterPosition.x - 3,
						pipBodyCenterPosition.y - 1f));

		revoFootJoint.collideConnected = false;
		revoFootJoint.enableLimit = true;

		world.createJoint(revoFootJoint);
	}

	public Controller getController() {
		return controller;
	}

	public Feet getFeet() {
		return feet;
	}

	public Feet getFoot() {
		return feet;
	}

	public HealthBar getHealthBar() {
		return healthBar;
	}

	public Body getPipBody() {
		return pipBody;
	}

	public Weapon getWeapon() {
		return weapon;
	}

	// handles null case for airborne Boolean object - defaults to false
	public boolean isAirborn() {
		if (this.airborn != null && this.airborn != Boolean.FALSE) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public void jump() {
		this.getPipBody().applyForce(new Vector2(0.0f, 330000.0f),
				this.getPipBody().getWorldCenter(), true);
	}

	public void move(float direction) {
		if (!isAirborn()) { // disable movement whilst in the air
			this.getPipBody().applyForce(new Vector2(direction * 34000f, 0),
					this.getFeet().getBody().getWorldCenter(), true);
		} else {
			this.getPipBody().applyForce(new Vector2(direction * 10000f, 0),
					this.getPipBody().getWorldCenter(), true);
		}
	}

	public void setAirborn(boolean isAirborn) {
		this.airborn = isAirborn;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

	public void setFeet(Feet feet) {
		this.feet = feet;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	private void createBody(Vector2 position) {
		// Dynamic Body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(position.x, position.y);

		bodyDef.fixedRotation = true;
		this.pipBody = world.createBody(bodyDef);

		CircleShape dynamicCircle = new CircleShape();
		dynamicCircle.setRadius(playerSize);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 2.00f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.5f;
		fixtureDef.shape = dynamicCircle;

		this.pipBody.createFixture(fixtureDef);
		dynamicCircle.dispose();

		this.pipBody.setUserData(new CollisionInfo("Hit pip's body!",
				CollisionObjectType.PipSqueakBody, this));
	}

	private void createPipSqueakFeet() {
		Vector2 pipBodyCenterPosition = this.pipBody.getWorldCenter();

		BodyDef feetDef = new BodyDef();
		feetDef.type = BodyType.DynamicBody;
		feetDef.position.set(pipBodyCenterPosition.x,
				pipBodyCenterPosition.y - 2.5f);
		feetDef.fixedRotation = false; // want feet to be able to rotate

		this.feet = new Feet(this);
		feet.setBody(world.createBody(feetDef));

		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(3f, 2.25f);
		fixtureDef.shape = boxShape;
		fixtureDef.restitution = 0.3f;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.3f;

		feet.getBody().createFixture(fixtureDef);

		boxShape.dispose();

		// create a joint at the point where the feet and body meet
		// will need to dynamically destroy/create joints for when facing
		// left/right and call respective left/right joint creation methods
		if (facingRight) {
			createFeetJointWhenFacingRight(pipBodyCenterPosition);
		} else {
			createFeetJointWhenFacingLeft(pipBodyCenterPosition);
		}

		this.feet.getBody().setUserData(
				new CollisionInfo("Hit foot",
						CollisionObjectType.PipSqueakFeet, feet));
	}

}