package com.hilldev.hill60.components;

public class AnimationController extends AComponent {

    Animation currentAnimation;

    public void setAnimation(Animation animation) {
        this.currentAnimation = animation;
    }

    public Animation getCurrentAnimation() {
        return currentAnimation;
    }

}
