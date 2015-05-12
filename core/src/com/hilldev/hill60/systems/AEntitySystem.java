package com.hilldev.hill60.systems;

import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;

public abstract class AEntitySystem {

	IEngine engine;
	
	public AEntitySystem(IEngine engine) {
		this.engine = engine;
	}

    public void start() {

    }

	public abstract void update();
    protected abstract boolean meetsConditions(GameObject obj);
    protected abstract void processObject(GameObject obj);
}
