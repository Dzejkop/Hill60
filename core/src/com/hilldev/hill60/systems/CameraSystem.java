package com.hilldev.hill60.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.components.WorldPosition;
import com.hilldev.hill60.components.CameraTag;

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
                return; // Only one camera position allowed
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

        // DEBUG AND TESTING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        if(Gdx.input.isKeyPressed(Input.Keys.X)) {
            renderingSystem.dynamicCamera.zoom -= 0.01f;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.Z)) {
            renderingSystem.dynamicCamera.zoom += 0.01f;
        }

        renderingSystem.dynamicCamera.position.set(w.x, w.y, 0);
    }
}
