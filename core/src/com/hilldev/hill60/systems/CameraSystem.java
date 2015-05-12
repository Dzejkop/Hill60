package com.hilldev.hill60.systems;

import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.CameraTag;
import com.hilldev.hill60.components.WorldPosition;

public class CameraSystem extends AEntitySystem {

    public CameraSystem(IEngine engine) {
        super(engine);
    }

    private RenderingSystem renderingSystem;

    @Override
    public void start() {
        renderingSystem = engine.getSystem(RenderingSystem.class);
    }

    @Override
    public void update() {
        for(GameObject obj : engine.getObjectList()) {
            if(meetsConditions(obj)) {
                processObject(obj);
                return; // Only one camera pos allowed
            }
        }
    }

    @Override
    protected boolean meetsConditions(GameObject obj) {
        return obj.hasComponent(CameraTag.class) && obj.hasComponent(WorldPosition.class);
    }

    @Override
    protected void processObject(GameObject obj) {

        WorldPosition w = obj.getComponent(WorldPosition.class);

        renderingSystem.camera.position.set(w.x, w.y, 0);
    }
}
