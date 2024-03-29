package com.hilldev.hill60.systems;

import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BehaviourComponent;

public class BehaviourSystem extends AEntitySystem {

    public BehaviourSystem(IEngine engine) {
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
        return obj.hasComponent(BehaviourComponent.class);
    }

    @Override
    protected void processObject(GameObject obj) {
        obj.getComponent(BehaviourComponent.class).run();
    }
}
