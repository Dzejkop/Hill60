package com.hilldev.hill60.systems;

import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.Collider;
import com.hilldev.hill60.components.Velocity;
import com.hilldev.hill60.components.WorldPosition;

public class PhysicsSystem extends IEntitySystem {

    public PhysicsSystem(IEngine engine) {
        super(engine);
    }

    @Override
    public void update() {
        
    }

    @Override
    protected boolean meetsConditions(GameObject obj) {
        return obj.hasComponent(Collider.class) && obj.hasComponent(Velocity.class) && obj.hasComponent(WorldPosition.class);
    }

    @Override
    protected void processObject(GameObject obj) {

    }
}
