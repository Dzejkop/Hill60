package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class SoundWave extends GameObject {

    float volume;

    public SoundWave(IEngine engine, float volume, float x, float y) {
    	
        super(engine);

        // Set tag
        this.tag = "Sound";

        // Get the resource manager
        ResourceManager manager = engine.getResourceManager();

        // Get the vars
        this.volume = volume;

        // Create components
        this.addComponent(new WorldPosition(x, y, false)); // Only world pos
        this.addComponent(new SpriteRenderer(manager.getSprite("Ring"), 0, 0, 4));
        getComponent(SpriteRenderer.class).setColor(0.9f, 0.9f, 0.9f);
        this.addComponent(new BoardPosition(0, 0));

        // Add a custom behaviour script
        this.addComponent(new BehaviourComponent(new Behaviour() {

            SoundWave parentObject;
			@SuppressWarnings("unused")
			BehaviourComponent parentComponent;
            SpriteRenderer sprite;

            @Override
            public void create(BehaviourComponent parentComponent) {
                this.parentComponent = parentComponent;
                this.parentObject = (SoundWave)parentComponent.getParent();

                // Ease of access
                this.sprite = parentObject.getComponent(SpriteRenderer.class);

                // Set initial values
                maxLife = (int)parentObject.volume;
                life = maxLife;
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
