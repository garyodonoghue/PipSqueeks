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

public class PipSqueak{

	private final World world;
	float playerSize = 3f;
	
	private Body pipBody;
	private Body foot;
	
	public CollisionHelper colHelper;
	
	private boolean isAirborn = false;
	
	public boolean isAirborn() {
		return isAirborn;
	}

	public void setAirborn(boolean isAirborn) {
		this.isAirborn = isAirborn;
	}

	private Weapon weapon;
	
	public Weapon getWeapon() {
		return weapon;
	}

	public void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	private Controller controller;

	public boolean facingRight = true;

	public Body getPipBody() {
		return pipBody;
	}

	public Body getFoot() {
		return foot;
	}
	
	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}

	
	public boolean isFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

	public PipSqueak(World world, Vector2 startPos) {
		this.world = world;
		createBody(startPos);
		createPipSqueakFeet();
		
		colHelper = new CollisionHelper();
		
		weapon = new Weapon(this.pipBody, world);
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
		fixtureDef.density = 2.00f;
		fixtureDef.friction = 0.3f;
		fixtureDef.restitution = 0.5f;
		fixtureDef.shape = dynamicCircle;

		this.pipBody.createFixture(fixtureDef);
		dynamicCircle.dispose();		
		
		this.pipBody.setUserData(new CollisionInfo("Hit pip's body!", CollisionObjectType.PipSqueakBody, this));
	}

	private void createPipSqueakFeet() {
		Vector2 pipBodyCenterPosition = this.pipBody.getWorldCenter();

		BodyDef feetDef = new BodyDef();
		feetDef.type = BodyType.DynamicBody;
		feetDef.position.set(pipBodyCenterPosition.x, pipBodyCenterPosition.y-2.5f);
		feetDef.fixedRotation = false; //want feet to be able to rotate

		this.foot = world.createBody(feetDef);
		
		FixtureDef fixtureDef = new FixtureDef();
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(3f, 2.25f);
		fixtureDef.shape = boxShape;
		fixtureDef.restitution = 0.3f;
		fixtureDef.density = 0.0f;
		fixtureDef.friction = 0.3f;

		foot.createFixture(fixtureDef);
		
		boxShape.dispose();

		//create a joint at the point where the feet and body meet
		//will need to dynamically destroy/create joints for when facing left/right and call respective left/right joint creation methods
		if(facingRight){
			createFeetJointWhenFacingRight(pipBodyCenterPosition);
		}
		else{
			createFeetJointWhenFacingLeft(pipBodyCenterPosition);
		}
		
		this.foot.setUserData(new CollisionInfo("Hit foot", CollisionObjectType.PipSqueakFeet, this));
	}

	public void createFeetJointWhenFacingLeft(Vector2 pipBodyCenterPosition) {
		RevoluteJointDef revoFootJoint = new RevoluteJointDef();

		revoFootJoint.initialize(this.pipBody, this.foot, new Vector2(pipBodyCenterPosition.x+3, pipBodyCenterPosition.y-1f)); 
		revoFootJoint.collideConnected = true;
		revoFootJoint.enableLimit = false;

		world.createJoint(revoFootJoint);
	}
	
	public void createFeetJointWhenFacingRight(Vector2 pipBodyCenterPosition) {
		RevoluteJointDef revoFootJoint = new RevoluteJointDef();
		
		revoFootJoint.initialize(this.pipBody, this.foot, new Vector2(pipBodyCenterPosition.x-3, pipBodyCenterPosition.y-1f)); 		
		
		revoFootJoint.collideConnected = false;
		revoFootJoint.enableLimit = true;

		world.createJoint(revoFootJoint);
	}
	
	public void jump(){
		this.getPipBody().applyForce(new Vector2(0.0f, 100000.0f), this.getPipBody().getWorldCenter() , true );
	}

	public void move(float direction) {
		this.getPipBody().setLinearVelocity(new Vector2(direction * 10f, 0));
	}

}