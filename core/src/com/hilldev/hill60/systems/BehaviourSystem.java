package com.hilldev.hill60.systems;

import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.SimpleScript;

public class BehaviourSystem extends IEntitySystem {

    public BehaviourSystem(IEngine engine) {
        super(engine);
    }

    @Override
    public void update() {
        for(GameObject o : engine.getObjectList()) {
            if(meetsConditions(o)) processObject(o);
        }
    }

    /* !!!!!!!!!!!!!!!!!
        For now the system uses SimpleScript,
        It should use BehaviourComponent to run these scripts
     */

    @Override
    protected boolean meetsConditions(GameObject obj) {
        return obj.hasComponent(SimpleScript.class);
    }

    @Override
    protected void processObject(GameObject obj) {
        obj.getComponent(SimpleScript.class).run();
    }
}