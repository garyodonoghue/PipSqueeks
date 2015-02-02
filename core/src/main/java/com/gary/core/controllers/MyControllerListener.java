package com.gary.core.controllers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import com.gary.core.objects.PipSqueak;
import com.gary.core.screens.GameScreen;

public class MyControllerListener implements
		com.badlogic.gdx.controllers.ControllerListener {

	@Override
	public boolean accelerometerMoved(Controller arg0, int arg1, Vector3 arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value) {
		// left analog
		if (axisCode == Xbox360Pad.AXIS_LEFT_X) {
			for (PipSqueak pip : GameScreen.pipSqueaks) {
				if (pip.getController() == controller) {
					if (value <= 0) {
						pip.setFacingRight(false);
					} else {
						pip.setFacingRight(true);
					}
					pip.move(value);
				}
			}
		}

		// TODO: right analogue - used for aiming
		if (axisCode == Xbox360Pad.AXIS_RIGHT_Y) {
			for (PipSqueak pip : GameScreen.pipSqueaks) {
				if (pip.getController() == controller) {
					pip.getWeapon().changeAim(value);
				}
			}
		}

		return false;
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonCode) {

		// jump
		if (buttonCode == Xbox360Pad.BUTTON_A) {
			// get pip assigned to controller and call jump()
			for (PipSqueak pip : GameScreen.pipSqueaks) {
				if (pip.getController() == controller) {
					pip.jump();
				}
			}
		}

		// shoot
		// TODO Trigger isnt getting recognised in debug mode, have to use the
		// 'R1' button instead
		if (buttonCode == Xbox360Pad.BUTTON_RB) {
			for (PipSqueak pip : GameScreen.pipSqueaks) {
				if (pip.getController() == controller) {
					pip.getWeapon().shoot();
				}
			}
		}

		return false;
	}

	@Override
	public boolean buttonUp(Controller arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void connected(Controller arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disconnected(Controller arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean povMoved(Controller arg0, int arg1, PovDirection arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean xSliderMoved(Controller arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ySliderMoved(Controller arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		return false;
	}

}
