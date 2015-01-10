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
	
	private Controller controller;
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	public boolean facingRight = true;
	
	public boolean isFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

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
		fixtureDef.density = 0.0f;
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

		BodyDef feetDef = new BodyDef();
		feetDef.type = BodyType.DynamicBody;
		feetDef.position.set(pipBodyCenterPosition.x, pipBodyCenterPosition.y-2.5f);
		feetDef.fixedRotation = false; //want feet to be able to rotate

		this.frontFoot = world.createBody(feetDef);
		this.backFoot = world.createBody(feetDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(3f, 2.25f);
		fixtureDef.shape = boxShape;
		fixtureDef.restitution = 0.3f;
		fixtureDef.density = 0.1f;
		fixtureDef.friction = 0.3f;

		frontFoot.createFixture(fixtureDef);
		backFoot.createFixture(fixtureDef);
		
		boxShape.dispose();

		//create a joint at the point where the feet and body meet
		//will need to dynamically destroy/create joints for when facing left/right and call respective left/right joint creation methods
		if(facingRight){
			createFeetJointWhenFacingRight(pipBodyCenterPosition);
		}
		else{
			createFeetJointWhenFacingLeft(pipBodyCenterPosition);
		}
		
		this.frontFoot.setUserData(new CollisionInfo("Hit front foot", CollisionObjectType.PipSqueakFeet, this));
		this.backFoot.setUserData(new CollisionInfo("Hit back foot", CollisionObjectType.PipSqueakFeet, this));

	}

	public void createFeetJointWhenFacingLeft(Vector2 pipBodyCenterPosition) {
		RevoluteJointDef revoFFJoint = new RevoluteJointDef();
		RevoluteJointDef revoBFJoint = new RevoluteJointDef();

		revoFFJoint.initialize(this.pipBody, this.frontFoot, new Vector2(pipBodyCenterPosition.x+3, pipBodyCenterPosition.y-1f)); 
		revoBFJoint.initialize(this.pipBody, this.backFoot, new Vector2(pipBodyCenterPosition.x+3, pipBodyCenterPosition.y-1f)); 
			
		revoFFJoint.collideConnected = true;
		revoFFJoint.enableLimit = false;
		revoBFJoint.collideConnected = true;
		revoBFJoint.enableLimit = false;

		world.createJoint(revoFFJoint);		
		world.createJoint(revoBFJoint);

	}
	
	public void createFeetJointWhenFacingRight(Vector2 pipBodyCenterPosition) {
		RevoluteJointDef revoFFJoint = new RevoluteJointDef();
		RevoluteJointDef revoBFJoint = new RevoluteJointDef();
		
		revoFFJoint.initialize(this.pipBody, this.frontFoot, new Vector2(pipBodyCenterPosition.x-3, pipBodyCenterPosition.y-1f)); 		
		revoBFJoint.initialize(this.pipBody, this.backFoot, new Vector2(pipBodyCenterPosition.x-3, pipBodyCenterPosition.y-1f)); 
		
		//TODO work out this position - it will be 0.75 from the bottom of the body, 
		//and either on the left or right depending on which way the pip is facing
		revoFFJoint.collideConnected = false;
		revoFFJoint.enableLimit = true;
		revoBFJoint.collideConnected = false;
		revoBFJoint.enableLimit = true;

		world.createJoint(revoFFJoint);
		world.createJoint(revoBFJoint);
	}
	
	public void jump(){
		this.getPipBody().applyForce(new Vector2(0.0f, 10000.0f), this.getPipBody().getWorldCenter() , true );
	}

	public void move(float direction) {
		this.getPipBody().applyForceToCenter(new Vector2(direction * 1000f, 0), true);
	}
}