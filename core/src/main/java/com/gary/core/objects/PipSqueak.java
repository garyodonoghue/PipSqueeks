package com.gary.core.objects;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.gary.core.collision.CollisionInfo;
import com.gary.core.collision.CollisionObjectType;

public class PipSqueak{

	public Body getPipBody() {
		return pipBody;
	}

	public Body getFrontFoot() {
		return frontFoot;
	}

	public Body getBackFoot() {
		return backFoot;
	}

	private final World world;
	float playerSize = 3f;

	private Body pipBody;
	private Body frontFoot;
	private Body backFoot;
	
	public boolean facingRight = true;
	
	public PipSqueak(World world, Vector2 startPos) {
		this.world = world;
		createBody(startPos);
	}

	private void createBody(Vector2 position) {
		// Dynamic Body
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position
				.set(position.x, position.y);

		bodyDef.fixedRotation = false;
		this.pipBody = world.createBody(bodyDef);

		CircleShape dynamicCircle = new CircleShape();
		dynamicCircle.setRadius(playerSize);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 15.0f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.5f;
		fixtureDef.shape = dynamicCircle;

		this.pipBody.createFixture(fixtureDef);
		dynamicCircle.dispose();		
		
		this.pipBody.setUserData(new CollisionInfo("Hit pip's body!", CollisionObjectType.PipSqueakBody, this));

		createPipSqueakFeet();
	}

	private void createPipSqueakFeet() {
		Vector2 pipBodyCenterPosition = this.pipBody.getWorldCenter();

		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(pipBodyCenterPosition.x, pipBodyCenterPosition.y-2.5f);
		bodyDef.fixedRotation = false; //want feel to be able to rotate

		this.frontFoot = world.createBody(bodyDef);
		this.backFoot = world.createBody(bodyDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(3f, 1.25f);
		fixtureDef.shape = boxShape;
		fixtureDef.restitution = 0.3f;
		fixtureDef.density = 15f;
		fixtureDef.friction = 0.3f;

		frontFoot.createFixture(fixtureDef);
		backFoot.createFixture(fixtureDef);
		
		boxShape.dispose();

		//create a joint at the point where the feet and body meet
		//will need to dynamically destroy/create joints for when facing left/right and call respective left/right joint creation methods
		createFeetJointWhenFacingRight(pipBodyCenterPosition);
		
		this.frontFoot.setUserData(new CollisionInfo("Hit front foot", CollisionObjectType.PipSqueakFeet, this));
		this.backFoot.setUserData(new CollisionInfo("Hit back foot", CollisionObjectType.PipSqueakFeet, this));

	}

	public void createFeetJointWhenFacingLeft(Vector2 pipBodyCenterPosition) {
		RevoluteJointDef revoJoint = new RevoluteJointDef();
		revoJoint.initialize(this.pipBody, this.frontFoot, new Vector2(pipBodyCenterPosition.x+3, pipBodyCenterPosition.y-1f)); 
	
		revoJoint.collideConnected = false;
		revoJoint.enableLimit = true;

		world.createJoint(revoJoint);
	}
	
	public void createFeetJointWhenFacingRight(Vector2 pipBodyCenterPosition) {
		RevoluteJointDef revoFFJoint = new RevoluteJointDef();
		revoFFJoint.initialize(this.pipBody, this.frontFoot, new Vector2(pipBodyCenterPosition.x-3, pipBodyCenterPosition.y-1f)); 
		
		RevoluteJointDef revoBFJoint = new RevoluteJointDef();
		revoBFJoint.initialize(this.pipBody, this.backFoot, new Vector2(pipBodyCenterPosition.x-3, pipBodyCenterPosition.y-1f)); 
		
		//TODO work out this position - it will be 0.75 from the bottom of the body, 
		//and either on the left or right depending on which way the pip is facing
		revoFFJoint.collideConnected = false;
		revoFFJoint.enableLimit = true;

		world.createJoint(revoFFJoint);
		
		revoBFJoint.collideConnected = false;
		revoBFJoint.enableLimit = true;

		world.createJoint(revoBFJoint);
	}
	
	public void updateMovement(float delta) {

	}
}