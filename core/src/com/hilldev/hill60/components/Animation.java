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
    int frameCount = 20;
    int currentFrame = 0;
    public int interval = 0;

    public void affect(SpriteRenderer renderer) {
        renderer.setSprite(spritesInAnimation.get(currentFrame));
    }

    public void step() {
        interval++;

        if(interval >= frameCount) {
            interval = 0;
            currentFrame++;
            if(currentFrame >= spritesInAnimation.size()) currentFrame = 0;
        }
    }

}
