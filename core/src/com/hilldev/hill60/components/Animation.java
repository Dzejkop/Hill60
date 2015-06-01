package com.hilldev.hill60.components;

import java.util.ArrayList;
import java.util.List;

public class Animation {

    public Animation(String[] frameNames) {
        getSpriteNames(frameNames);
    }

    public Animation(String[] frameNames, int stepsPerFrame) {
        getSpriteNames(frameNames);

        this.stepsPerFrame = stepsPerFrame;
    }

    private void getSpriteNames(String[] frameNames) {
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
    public boolean inReverse = false;

    public void affect(SpriteRenderer renderer) {
        renderer.setSprite(spritesInAnimation.get(currentFrame));
    }

    public void reset() {
        interval = 0;
        currentFrame = 0;
    }

    public void step() {
        if(isActive) {
            interval++;

            if (interval >= stepsPerFrame) {
                interval = 0;

                if(!inReverse) currentFrame++;
                else currentFrame--;

                if (currentFrame >= spritesInAnimation.size()) {
                    currentFrame = 0;

                    if (!isRepeating) isActive = false;
                } else if (currentFrame < 0) {
                    currentFrame = spritesInAnimation.size()-1;

                    if (!isRepeating) isActive = false;
                }
            }
        }
    }

}
