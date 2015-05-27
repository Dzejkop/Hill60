package com.hilldev.hill60.systems;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.Animation;
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
        return obj.hasComponent(SpriteRenderer.class) && obj.hasComponent(Animation.class);
    }

    @Override
    protected void processObject(GameObject obj) {
        Animation anim = obj.getComponent(Animation.class);
        SpriteRenderer spriteRenderer = obj.getComponent(SpriteRenderer.class);

        anim.step();
        anim.affect(spriteRenderer);
    }
}
