package com.hilldev.hill60.systems;

import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;

public abstract class IEntitySystem {

	IEngine engine;
	
	public IEntitySystem(IEngine engine) {
		this.engine = engine;
	}
	
	public abstract void update();
    protected abstract boolean meetsConditions(GameObject obj);
    protected abstract void processObject(GameObject obj);
}
