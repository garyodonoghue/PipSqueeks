package com.gary.core.collision;

public class CollisionInfo {

	public Object object;
	public String text;
	public CollisionObjectType type;

	public CollisionInfo(String text, CollisionObjectType type, Object object) {
		this.text = text;
		this.type = type;
		this.object = object;
	};
}
