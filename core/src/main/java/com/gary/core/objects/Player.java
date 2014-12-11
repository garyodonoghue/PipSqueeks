package com.gary.core.objects;

import com.badlogic.gdx.controllers.Controller;

public class Player {

	private Controller controller;
	private int playerNumber;
	
	public int getPlayerNumber() {
		return playerNumber;
	}

	public void setPlayerNumber(int playerNumber) {
		this.playerNumber = playerNumber;
	}

	public Player(Controller controller) {
		super();
		this.controller = controller;
		this.playerNumber = playerNumber;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((controller == null) ? 0 : controller.hashCode());
		return result;
	}

	//custom equals based only on the controllers as we dont want the same 
	//person using the same controller joining as two different players
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (controller == null) {
			if (other.controller != null)
				return false;
		} else if (!controller.equals(other.controller))
			return false;
		return true;
	}
}
