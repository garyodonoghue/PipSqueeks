package com.gary.core.collision;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gary.core.objects.Feet;
import com.gary.core.objects.PipSqueak;

public class CollisionHelper implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		CollisionInfo bodyAInfo = getCollisionInfoFromFixture(fixtureA);
		CollisionInfo bodyBInfo = getCollisionInfoFromFixture(fixtureB);

		if (bodyAInfo != null && bodyBInfo != null) {
			if (bodyAInfo.type == CollisionObjectType.PipSqueakFeet) {
				if (bodyBInfo.type == CollisionObjectType.Platform) {
					Gdx.app.log("beginContact", bodyAInfo.text + " & "
							+ bodyBInfo.text);

					Feet feet = (Feet) bodyAInfo.object;
					feet.getPip().setAirborn(false);
				}
			}
			if (bodyAInfo.type == CollisionObjectType.Platform) {
				if (bodyBInfo.type == CollisionObjectType.PipSqueakFeet) {
					Gdx.app.log("beingContact", bodyBInfo.text + " & "
							+ bodyAInfo.text);

					Feet feet = (Feet) bodyBInfo.object;
					feet.getPip().setAirborn(false);
				}
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		CollisionInfo bodyAInfo = getCollisionInfoFromFixture(fixtureA);
		CollisionInfo bodyBInfo = getCollisionInfoFromFixture(fixtureB);

		// collision between platforms and pip body/feet
		if (bodyAInfo != null && bodyBInfo != null) {
			if (bodyAInfo.type == CollisionObjectType.PipSqueakFeet) {
				if (bodyBInfo.type == CollisionObjectType.Platform) {
					Gdx.app.log("endContact", bodyAInfo.text + " & "
							+ bodyBInfo.text);

					Feet feet = (Feet) bodyAInfo.object;
					feet.getPip().setAirborn(true);
				}
			}
			if (bodyAInfo.type == CollisionObjectType.Platform) {
				if (bodyBInfo.type == CollisionObjectType.PipSqueakFeet) {
					Gdx.app.log("endContact", bodyBInfo.text + " & "
							+ bodyAInfo.text);

					Feet feet = (Feet) bodyBInfo.object;
					feet.getPip().setAirborn(true);
				}
			}

			// collision between bullet and pips
			if (bodyAInfo.type == CollisionObjectType.PipSqueakFeet) {
				if (bodyBInfo.type == CollisionObjectType.Bullet) {
					Gdx.app.log("endContact", bodyAInfo.text + " & "
							+ bodyBInfo.text);

					Feet feet = (Feet) bodyAInfo.object;
					feet.getPip().getHealthBar().damageHealth();
				}
			}
			if (bodyAInfo.type == CollisionObjectType.Bullet) {
				if (bodyBInfo.type == CollisionObjectType.PipSqueakFeet) {
					Gdx.app.log("endContact", bodyBInfo.text + " & "
							+ bodyAInfo.text);

					Feet feet = (Feet) bodyBInfo.object;
					feet.getPip().getHealthBar().damageHealth();
				}
			}

			if (bodyAInfo.type == CollisionObjectType.PipSqueakBody) {
				if (bodyBInfo.type == CollisionObjectType.Bullet) {
					Gdx.app.log("endContact", bodyAInfo.text + " & "
							+ bodyBInfo.text);

					PipSqueak pip = (PipSqueak) bodyAInfo.object;
					pip.getHealthBar().damageHealth();
				}
			}
			if (bodyAInfo.type == CollisionObjectType.Bullet) {
				if (bodyBInfo.type == CollisionObjectType.PipSqueakBody) {
					Gdx.app.log("endContact", bodyBInfo.text + " & "
							+ bodyAInfo.text);

					PipSqueak pip = (PipSqueak) bodyBInfo.object;
					pip.getHealthBar().damageHealth();
				}
			}
		}
	}

	@Override
	public void postSolve(Contact arg0, ContactImpulse arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact arg0, Manifold arg1) {
		// TODO Auto-generated method stub

	}

	private CollisionInfo getCollisionInfoFromFixture(Fixture fix) {
		CollisionInfo colInfo = null;

		if (fix != null) {
			Body body = fix.getBody();

			if (body != null) {
				colInfo = (CollisionInfo) body.getUserData();
			}
		}

		return colInfo;
	}

}
