package com.hilldev.hill60.components;

import java.util.ArrayList;
import java.util.List;

public class Animation extends AComponent {

    public Animation(String[] frameNames) {

        spritesInAnimation = new ArrayList<>();

        for(String n : frameNames) {
            spritesInAnimation.add(n);
        }
    }

    List<String> spritesInAnimation;

    int interval = 0;
    int currentFrame = 0;

    public int stepsPerFrame = 20;
    public boolean isActive = true;
    public boolean isRepeating = true;

    public void affect(SpriteRenderer renderer) {
        renderer.setSprite(spritesInAnimation.get(currentFrame));
    }

    public void step() {
        if(isActive) {
            interval++;

            if (interval >= stepsPerFrame) {
                interval = 0;
                currentFrame++;
                if (currentFrame >= spritesInAnimation.size()) {
                    currentFrame = 0;

                    if (isRepeating == false) isActive = false;
                }
            }
        }
    }

}
