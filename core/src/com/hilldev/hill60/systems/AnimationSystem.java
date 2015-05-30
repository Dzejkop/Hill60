package com.hilldev.hill60.systems;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.AnimationController;
import com.hilldev.hill60.components.SpriteRenderer;
import com.hilldev.hill60.objects.GameObject;

public class AnimationSystem extends AEntitySystem {

    public AnimationSystem(IEngine engine) {
        super(engine);
    }

    @Override
    public void update() {
        for(GameObject obj : engine.getObjectList()) {
            if(meetsConditions(obj)) processObject(obj);
        }
    }

    @Override
    protected boolean meetsConditions(GameObject obj) {
        return obj.hasComponent(SpriteRenderer.class) && obj.hasComponent(AnimationController.class);
    }

    @Override
    protected void processObject(GameObject obj) {
        AnimationController anim = obj.getComponent(AnimationController.class);

        if(anim == null) return;

        SpriteRenderer spriteRenderer = obj.getComponent(SpriteRenderer.class);

        anim.getCurrentAnimation().step();
        anim.getCurrentAnimation().affect(spriteRenderer);
    }
}
