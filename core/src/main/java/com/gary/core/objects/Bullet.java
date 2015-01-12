package com.gary.core.objects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.gary.core.collision.CollisionInfo;
import com.gary.core.collision.CollisionObjectType;

public class Bullet {

	private Body weaponBody;
	private Body bulletBody;
	
	public Body getBulletBody() {
		return bulletBody;
	}

	public void setBulletBody(Body bulletBody) {
		this.bulletBody = bulletBody;
	}

	private World world;
	
	public Bullet(Body weaponBody, World world) {
		this.weaponBody = weaponBody;
		this.world = world;
				
		BodyDef bulletDef = new BodyDef();
		bulletDef.type = BodyType.StaticBody;
		
		//TODO want to position this at 'shoulder height'
		bulletDef.position.set(weaponBody.getWorldCenter().x - 10, weaponBody.getWorldCenter().y + 10);
		bulletDef.fixedRotation = true; //want to be able to 'rotate' the gun when aiming 
	
		this.bulletBody = this.world.createBody(bulletDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(0.25f, 0.25f);
		fixtureDef.shape = boxShape;
		fixtureDef.isSensor = false;
		
		bulletBody.createFixture(fixtureDef);
		
		boxShape.dispose();

		this.bulletBody.setUserData(new CollisionInfo("Bullet collision", CollisionObjectType.Bullet, this));
	}
}
