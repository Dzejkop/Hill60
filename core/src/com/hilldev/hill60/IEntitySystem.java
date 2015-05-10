package com.hilldev.hill60;

public abstract class IEntitySystem {

	IEngine engine;
	
	public IEntitySystem(IEngine engine) {
		this.engine = engine;
	}
	
	public abstract void update();
}
