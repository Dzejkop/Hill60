package com.hilldev.hill60;

public abstract class EntitySystem {

	Engine engine;
	
	public EntitySystem(Engine engine) {
		this.engine = engine;
	}
	
	public abstract void update();
}
