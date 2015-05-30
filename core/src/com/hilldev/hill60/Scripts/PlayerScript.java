package com.hilldev.hill60.Scripts;

import com.hilldev.hill60.InputManager;
import com.hilldev.hill60.components.*;
import com.hilldev.hill60.objects.Player;

/**
 * Ta klasa ogarnia input oraz animacje (ponieważ będą różne animacje dla gracza i różne dla AI)
 */

public class PlayerScript implements Behaviour {

    private static final int RUN_ANIMATION_SPEED = 3;
    private static final int SNEAK_ANIMATION_SPEED = 20;

    // Ease of access
    Player parent;
    BehaviourComponent parentComponent;
    AnimationController animationController;
    CharacterScript characterScript;

    // Animations
    Animation walkSidewaysAnimation;
    Animation walkBackwardAnimation;
    Animation walkForwardAnimation;

    // State vars
    private boolean inSneakMode = false;

    @Override
    public void create(BehaviourComponent parentComponent) {
        this.parentComponent = parentComponent;
        parent = (Player)(parentComponent.getParent());

        animationController = parent.getComponent(AnimationController.class);

        walkSidewaysAnimation = new Animation(walkAnimationFrames());
        walkBackwardAnimation = new Animation(walkBackwardAnimationFrames());
        walkForwardAnimation = new Animation(walkForwardAnimationFrames());

        // Connect to the character script
        characterScript = parentComponent.get(CharacterScript.class);

        if(animationController.getCurrentAnimation() == null) animationController.setAnimation(walkSidewaysAnimation);
    }

    @Override
    public void run() {

    }

    private String[] walkAnimationFrames() {
        String[] f = new String[10];

        for(int i = 1 ; i <= 10; i++) {
            f[i-1] = "CharacterWalk" + (i==10 ? i : "0"+i);
        }

        return f;
    }

    private String[] walkBackwardAnimationFrames() {
        String[] f = new String[10];

        for(int i = 0 ; i < 10; i++) {
            f[i] = "CharacterWalkBackward0" + i;
        }

        return f;
    }

    private String[] walkForwardAnimationFrames() {
        String[] f = new String[10];

        for(int i = 0 ; i < 10; i++) {
            f[i] = "CharacterWalkForward0" + i;
        }

        return f;
    }
}
