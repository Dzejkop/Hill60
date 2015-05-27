package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Player extends GameObject {

    public WorldPosition position;
    public Animation animation;
    public Velocity velocity;

    public Player(IEngine engine) {
        super(engine);

        // Set a tag
        this.tag = "Player";

        // Get the resource manager
        ResourceManager manager = engine.getResourceManager();

        this.addComponent(new SpriteRenderer(manager.getSprite("CharacterNeutral"), 0, 0, 2));
        this.addComponent(new WorldPosition(0, 20, false));					// The continuous position in game world
        this.addComponent(new BoardPosition(0, 0));							// Position on the board
        this.addComponent(new InputResponder());							// Responds to input from InputSystem
        this.addComponent(new BehaviourComponent(new PlayerScript()));		// Simple movmeent script
        this.addComponent(new CameraTag());									// Camera should follow this object
        this.addComponent(new Collider(22, 29));
        this.addComponent(new Velocity(0, 0));
        this.addComponent(new SoundTrigger("footstepBrick.ogg"));
        this.addComponent(new Viewer());

        String[] frames = walkAnimationFrames();
        this.addComponent(new Animation(frames, 5));

        // Get access to most used components
        animation = getComponent(Animation.class);
        velocity = getComponent(Velocity.class);
        position = getComponent(WorldPosition.class);
    }

    private String[] walkAnimationFrames() {
        String[] f = new String[10];

        for(int i = 1 ; i <= 10; i++) {
            f[i-1] = "CharacterWalk" + (i==10 ? i : "0"+i);
        }

        return f;
    }

    @Override
    public void receiveMessage(String message, GameObject sender) {
        super.receiveMessage(message, sender);
    }
}
