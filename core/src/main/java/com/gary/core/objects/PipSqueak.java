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

	private final World world;
	private Body pipBody;
	float playerSize = 3f;
	private Body feet;
	
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

		bodyDef.fixedRotation = true;
		this.pipBody = world.createBody(bodyDef);

		CircleShape dynamicCircle = new CircleShape();
		dynamicCircle.setRadius(playerSize);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 10.0f;
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

		this.feet = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(3f, 1.25f);
		fixtureDef.shape = boxShape;
		fixtureDef.restitution = 0.3f;
		fixtureDef.density = 1f;
		fixtureDef.friction = 0.3f;

		feet.createFixture(fixtureDef);

		boxShape.dispose();

		//create a joint at the point where the feet and body meet
		//will need to dynamically destroy/create joints for when facing left/right and call respective left/right joint creation methods
		createFeetJointWhenFacingRight(pipBodyCenterPosition);
		
		this.feet.setUserData(new CollisionInfo("Hit feet", CollisionObjectType.PipSqueakFeet, this));

	}

	public void createFeetJointWhenFacingLeft(Vector2 pipBodyCenterPosition) {
		RevoluteJointDef revoJoint = new RevoluteJointDef();
		revoJoint.initialize(this.pipBody, this.feet, new Vector2(pipBodyCenterPosition.x+3, pipBodyCenterPosition.y-1f)); 
		
		//TODO work out this position - it will be 0.75 from the bottom of the body, 
		//and either on the left or right depending on which way the pip is facing
		revoJoint.collideConnected = false;
		revoJoint.enableLimit = true;

		world.createJoint(revoJoint);
	}
	
	public void createFeetJointWhenFacingRight(Vector2 pipBodyCenterPosition) {
		RevoluteJointDef revoJoint = new RevoluteJointDef();
		revoJoint.initialize(this.pipBody, this.feet, new Vector2(pipBodyCenterPosition.x-3, pipBodyCenterPosition.y-1f)); 
		
		//TODO work out this position - it will be 0.75 from the bottom of the body, 
		//and either on the left or right depending on which way the pip is facing
		revoJoint.collideConnected = false;
		revoJoint.enableLimit = true;

		world.createJoint(revoJoint);
	}
	
	public void updateMovement(float delta) {

	}
}