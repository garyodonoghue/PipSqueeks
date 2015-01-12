package com.gary.core.objects;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.gary.core.collision.CollisionInfo;
import com.gary.core.collision.CollisionObjectType;

public class Weapon {

	private Body weaponBody;
	private Vector2 pipBodyCenter;
	private World world;
	
	public Weapon(Body pipBody, World world){
		pipBodyCenter = pipBody.getWorldCenter();
		this.world = world;
		this.createWeaponSensor();
	}
	
	public Body getWeaponBody() {
		return weaponBody;
	}

	public void setWeaponBody(Body weaponBody) {
		this.weaponBody = weaponBody;
	}
	
	public void createWeaponSensor() {
		BodyDef weaponSensorDef = new BodyDef();
		weaponSensorDef.type = BodyType.StaticBody;
		
		//TODO want to position this at 'shoulder height'
		weaponSensorDef.position.set(pipBodyCenter.x - 10, pipBodyCenter.y + 10);
		weaponSensorDef.fixedRotation = false; //want to be able to 'rotate' the gun when aiming 

		this.weaponBody = world.createBody(weaponSensorDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(6f, 2.25f);
		fixtureDef.shape = boxShape;
		fixtureDef.isSensor = true;
		
		weaponBody.createFixture(fixtureDef);
		
		boxShape.dispose();

		this.weaponBody.setUserData(new CollisionInfo("Hit gun", CollisionObjectType.Weapon, this));
	}
	
	public void changeAim(float value) {
		//TODO Need to rotate the weapon until it gets to direction analog stick is pointing	
		this.getWeaponBody().setAngularVelocity(value);
		
	}

	public void shoot() {
		Bullet bullet = new Bullet(this.getWeaponBody(), world);
		//TODO Use the gun's angle to determine the trajectory of the bullet
		bullet.getBulletBody().setLinearVelocity(new Vector2(10f, 10f));
	}
	
}
