package com.hilldev.hill60.systems;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.components.Explosion;

public class ExplosionSystem extends AEntitySystem {

    public ExplosionSystem(IEngine engine) {
        super(engine);
    }

    @Override
    public void update() {
        for(GameObject o : engine.getObjectList()) {
            if(meetsConditions(o)) processObject(o);
        }
    }

    @Override
    protected boolean meetsConditions(GameObject obj) {
        return obj.hasComponent(Explosion.class);
    }

    @Override
    protected void processObject(GameObject obj) {
    	
    }
}
