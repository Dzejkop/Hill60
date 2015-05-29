package com.hilldev.hill60.components;

import com.hilldev.hill60.objects.Player;

// More for debug purposes than anything else
public class PlayerScript implements Behaviour {

    private static final int RUN_ANIMATION_SPEED = 3;
    private static final int SNEAK_ANIMATION_SPEED = 20;

    private static final float RUN_STEP_LOUDNESS = 10;

    private static final int RUN_STEP_INTERVAL = 15;

    private static final float RUN_SPEED = 7;
    private static final float SNEAK_SPEED = 2;

    // Ease of access
    Player parent;
    BehaviourComponent parentComponent;
    AnimationController animationController;

    // Animations
    Animation walkSidewayAnimation;
    Animation walkBackwardAnimation;
    Animation walkForwardAnimation;

    // State vars
    private boolean inSneakMode = false;

    @Override
    public void create(BehaviourComponent parentComponent) {
        this.parentComponent = parentComponent;
        parent = (Player)(parentComponent.parent);

        animationController = parent.getComponent(AnimationController.class);

        walkSidewayAnimation = new Animation(walkAnimationFrames());
        walkBackwardAnimation = new Animation(walkBackwardAnimationFrames());
        walkForwardAnimation = new Animation(walkForwardAnimationFrames());

        if(animationController.getCurrentAnimation() == null) animationController.setAnimation(walkSidewayAnimation);
    }

    // TEMPORAL SOLUTION
    int sinceLastStep = RUN_STEP_INTERVAL;

    @Override
    public void run() {
        Velocity v = parent.getComponent(Velocity.class);
        InputResponder i = parent.getComponent(InputResponder.class);
        SoundTrigger s = parent.getComponent(SoundTrigger.class);
        v.x = 0;
        v.y = 0;

        float veloc = 2;

        float xv = 0;
        float yv = 0;

        if(i.upArrow) yv = veloc;
        if(i.downArrow) yv = -veloc;
        if(i.leftArrow) xv = -veloc;
        if(i.rightArrow) xv = veloc;
        
        if(Math.abs(xv) == 1 && Math.abs(yv) == 0) {
        	xv *= 0.5;
        	yv *= 0.5;
        }

        if(Math.abs(xv) + Math.abs(yv) > 0) {
            sinceLastStep--;
            if(sinceLastStep < 0) {
                sinceLastStep = 20;
                s.triggered = true;
            }
        }

        if(xv > 0) {
            animationController.setAnimation(walkSidewayAnimation);
            animationController.getCurrentAnimation().isActive = true;
            parent.spriteRenderer.isFlipped = false;
        }
        else if(xv < 0){
            animationController.setAnimation(walkSidewayAnimation);
            animationController.getCurrentAnimation().isActive = true;
            parent.spriteRenderer.isFlipped = true;
        }

        if(yv > 0 ) {
            animationController.setAnimation(walkForwardAnimation);
            animationController.getCurrentAnimation().isActive = true;
        } else if(yv < 0) {
            animationController.setAnimation(walkBackwardAnimation);
            animationController.getCurrentAnimation().isActive = true;
        }

        if(xv == 0 && yv == 0) {
            animationController.getCurrentAnimation().isActive = false;
            animationController.getCurrentAnimation().reset();
        }

        if(inSneakMode) {
            animationController.getCurrentAnimation().stepsPerFrame = SNEAK_ANIMATION_SPEED;
        } else {
            animationController.getCurrentAnimation().stepsPerFrame = RUN_ANIMATION_SPEED;
        }

        v.x = xv;
        v.y = yv;
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
