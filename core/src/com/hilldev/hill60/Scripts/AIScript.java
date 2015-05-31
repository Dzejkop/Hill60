package com.hilldev.hill60.Scripts;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.InputManager;
import com.hilldev.hill60.components.*;
import com.hilldev.hill60.objects.Enemy;
import com.hilldev.hill60.objects.Player;
import com.hilldev.hill60.systems.BoardSystem;
import com.hilldev.hill60.systems.RenderingSystem;

public class AIScript implements Behaviour {

    private static final int RUN_ANIMATION_SPEED = 3;
    private static final int SNEAK_ANIMATION_SPEED = 20;

    // Ease of access
    Enemy parent;
    IEngine engine;
    BehaviourComponent parentComponent;
    AnimationController animationController;
    CharacterScript characterScript;

    // A bit of cheating
    Player player;

    // Animations
    Animation walkSidewaysAnimation;
    Animation walkBackwardAnimation;
    Animation walkForwardAnimation;

    // State vars
    private boolean inSneakMode = false;

    @Override
    public void create(BehaviourComponent parentComponent) {

        this.parentComponent = parentComponent;
        parent = (Enemy) (parentComponent.getParent());
        animationController = parent.getComponent(AnimationController.class);

        walkSidewaysAnimation = new Animation(walkAnimationFrames());
        walkBackwardAnimation = new Animation(walkBackwardAnimationFrames());
        walkForwardAnimation = new Animation(walkForwardAnimationFrames());

        // Connect to the character script
        characterScript = parentComponent.get(CharacterScript.class);

        // Connect to game engine
        engine = parent.engine;

        // Find player
        player = (Player)engine.findObject("Player");

        if (animationController.getCurrentAnimation() == null)
            animationController.setAnimation(walkSidewaysAnimation);
    }

    @Override
    public void run() {
        animate();

        BoardPosition playerPos = player.getComponent(BoardPosition.class);

        goTo(playerPos.x, playerPos.y);
    }

    // In board pos
    public void goTo(int x, int y) {

        goTo((float)x*BoardPosition.TILE_SIZE, (float)y*BoardPosition.TILE_SIZE);

        /*BoardPosition bPos  = parent.getComponent(BoardPosition.class);

        // Get movement vector
        int vx = x - bPos.x;
        int vy = y - bPos.y;

        characterScript.goingRight = vx > 0;
        characterScript.goingLeft = vx < 0;
        characterScript.goingUp= vy > 0;
        characterScript.goingDown= vy < 0;*/

    }

    public void goTo(float x, float y) {
        WorldPosition bPos  = parent.getComponent(WorldPosition.class);

        // Get movement vector
        float vx = x - bPos.x;
        float vy = y - bPos.y;

        characterScript.goingRight = vx > 0;
        characterScript.goingLeft = vx < 0;
        characterScript.goingUp= vy > 0;
        characterScript.goingDown= vy < 0;
    }

    private void animate() {

        if (characterScript.goingRight) {
            animationController.setAnimation(walkSidewaysAnimation);
            animationController.getCurrentAnimation().isActive = true;
            parent.spriteRenderer.isFlipped = false;
        } else if (characterScript.goingLeft) {
            animationController.setAnimation(walkSidewaysAnimation);
            animationController.getCurrentAnimation().isActive = true;
            parent.spriteRenderer.isFlipped = true;
        } else if (characterScript.goingUp) {
            animationController.setAnimation(walkForwardAnimation);
            animationController.getCurrentAnimation().isActive = true;
        } else if (characterScript.goingDown) {
            animationController.setAnimation(walkBackwardAnimation);
            animationController.getCurrentAnimation().isActive = true;
        } else {
            animationController.getCurrentAnimation().isActive = false;
            animationController.getCurrentAnimation().reset();
        }

        if (inSneakMode) {
            animationController.getCurrentAnimation().stepsPerFrame = SNEAK_ANIMATION_SPEED;
        } else {
            animationController.getCurrentAnimation().stepsPerFrame = RUN_ANIMATION_SPEED;
        }
    }

    private String[] walkAnimationFrames() {
        String[] f = new String[10];

        for (int i = 1; i <= 10; i++) {
            f[i - 1] = "CharacterWalk" + (i == 10 ? i : "0" + i);
        }

        return f;
    }

    private String[] walkBackwardAnimationFrames() {
        String[] f = new String[10];

        for (int i = 0; i < 10; i++) {
            f[i] = "CharacterWalkBackward0" + i;
        }

        return f;
    }

    private String[] walkForwardAnimationFrames() {
        String[] f = new String[10];

        for (int i = 0; i < 10; i++) {
            f[i] = "CharacterWalkForward0" + i;
        }

        return f;
    }
}
