package com.hilldev.hill60.scripts;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.InputManager;
import com.hilldev.hill60.components.*;
import com.hilldev.hill60.objects.Player;
import com.hilldev.hill60.systems.RenderingSystem;

/**
 * Ta klasa ogarnia input oraz animacje (ponieważ będą różne animacje dla
 * gracza i różne dla AI)
 */

public class PlayerScript implements Behaviour {

	private static final int RUN_ANIMATION_SPEED = 3;
	private static final int SNEAK_ANIMATION_SPEED = 20;

	// Ease of access
	Player parent;
	IEngine engine;
	BehaviourComponent parentComponent;
	AnimationController animationController;
	CharacterScript characterScript;

	// Animations
	Animation walkSidewaysAnimation;
	Animation walkBackwardAnimation;
	Animation walkForwardAnimation;

	// State vars
	private boolean inSneakMode = false;

    // Items
    int currentItem = 0;

	// Zoom vars
	float currentZoom = 1;
	float zoomInVal = 0.6f;
	float zoomOutVal = 1;
	float targetZoom = zoomOutVal;

	@Override
	public void create(BehaviourComponent parentComponent) {

		this.parentComponent = parentComponent;
		parent = (Player) (parentComponent.getParent());
		animationController = parent.getComponent(AnimationController.class);

		walkSidewaysAnimation = new Animation(walkAnimationFrames());
		walkBackwardAnimation = new Animation(walkBackwardAnimationFrames());
		walkForwardAnimation = new Animation(walkForwardAnimationFrames());

		// Connect to the character script
		characterScript = parentComponent.get(CharacterScript.class);

		// Connect to game engine
		engine = parent.engine;

		if (animationController.getCurrentAnimation() == null)
			animationController.setAnimation(walkSidewaysAnimation);
	}

	@Override
	public void run() {

		InputManager manager = engine.getInputManager();

		characterScript.goingUp = manager.upArrowPressed();
		characterScript.goingDown = manager.downArrowPressed();
		characterScript.goingRight = manager.rightArrowPressed();
		characterScript.goingLeft = manager.leftArrowPressed();

		animate();
		if (manager.keyJustPressed(Input.Keys.SHIFT_LEFT)) {
			inSneakMode = !inSneakMode;
			characterScript.inSneakMode = inSneakMode;

            if(inSneakMode) {
                targetZoom = zoomInVal;
            } else {
                targetZoom = zoomOutVal;
            }
		}

		if (manager.keyJustPressed(Input.Keys.Q)) {
			prevItem();
		}

		if (manager.keyJustPressed(Input.Keys.E)) {
			nextItem();
		}
        if(manager.keyJustPressed(Input.Keys.B)) {
        	useItem(currentItem);
        }

		currentZoom += (targetZoom - currentZoom) * 0.1f;
		((OrthographicCamera) (engine.getSystem(RenderingSystem.class)
				.getCamera())).zoom = currentZoom;
	}

    public boolean isSneaking() {
        return inSneakMode;
    }

	private void useItem(int item) {
        BoardPosition p = parent.getComponent(BoardPosition.class);
        if(characterScript.getItem(getCurrentItem()).isReady())
        characterScript.getItem(getCurrentItem()).use(characterScript.getLastStep(),p.x,p.y,engine);
	}

	private void prevItem() {
        currentItem--;
        if(currentItem < 0 ) currentItem = CharacterScript.getItemList().length-1;
	}

	private void nextItem() {
        currentItem++;
        if(currentItem >= CharacterScript.getItemList().length) currentItem = 0;
	}

    public String getCurrentItem() {
        return CharacterScript.getItemList()[currentItem];
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
