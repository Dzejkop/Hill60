package com.hilldev.hill60.objects;

import com.hilldev.hill60.Hill60Main;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class SoundWave extends GameObject {

    public SoundWave(IEngine engine, float x, float y) {
        super(engine);

        // Set tag
        this.tag = "Sound";

        // Connect
        ResourceManager resourceManager = ((Hill60Main)engine).resourceManager;

        // Create components
        this.addComponent(new WorldPosition(x, y, false)); // Only world pos
        this.addComponent(new SpriteRenderer(resourceManager.getSprite("Ring.png"), 0, 0, 4));
        this.addComponent(new BoardPosition(0, 0));

        // Add a custom behaviour script
        this.addComponent(new BehaviourComponent(new Behaviour() {

            GameObject parentObject;
            BehaviourComponent parentComponent;
            SpriteRenderer sprite;

            @Override
            public void create(BehaviourComponent parentComponent) {
                this.parentComponent = parentComponent;
                this.parentObject = parentComponent.getParent();

                // Ease of access
                this.sprite = parentObject.getComponent(SpriteRenderer.class);
            }

            int maxLife = 25;
            int life = maxLife;
            float currentScale = 1.0f;
            float scaleMultiplier = 1.1f;

            @Override
            public void run() {

                life--;
                if(life > 0 ) {
                    currentScale*=scaleMultiplier;

                    sprite.setScale(currentScale);
                    sprite.setAlpha((float)life / (float)maxLife);

                } else {
                    parentObject.engine.destroyObject(parentObject);
                }
            }
        }));
    }


}
