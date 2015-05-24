package com.hilldev.hill60.objects;

import com.hilldev.hill60.Hill60Main;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class SoundWave extends GameObject {

    public SoundWave(float x, float y) {
        super();

        // Set tag
        this.tag = "Sound";

        // Connect
        ResourceManager resourceManager = Hill60Main.getInstance().resourceManager;

        // Create components
        this.addComponent(new WorldPosition(x, y, false)); // Only world pos
        this.addComponent(new SpriteRenderer(resourceManager.getSprite("Ring.png"), 0, 0, 5));
        this.addComponent(new BoardPosition(0, 0));

        // Add a custom behaviour script
        this.addComponent(new BehaviourComponent(new Behaviour() {

            GameObject parentObject;
            BehaviourComponent parentComponent;

            @Override
            public void create(BehaviourComponent parentComponent) {
                this.parentComponent = parentComponent;
                this.parentObject = parentComponent.getParent();
            }

            @Override
            public void run() {

                /*
                     IMPLEMENT ANIMATION, DEATH TIMING AND RESISIZING HERE
                 */

            }
        }));
    }


}
