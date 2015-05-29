package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Player extends GameObject {

    // Ease of access
    public WorldPosition position;
    public Velocity velocity;
    public SpriteRenderer spriteRenderer;

    public Player(IEngine engine) {
        super(engine);

        // Set a tag
        this.tag = "Player";

        // Get the resource manager
        ResourceManager manager = engine.getResourceManager();

        this.addComponent(new SpriteRenderer(manager.getSprite("CharacterNeutral"), 0, 0, 2));
        this.addComponent(new WorldPosition(0, 20, false));					// The continuous position in game world
        this.addComponent(new BoardPosition(0, 0));							// Position on the board
        this.addComponent(new CameraTag());									// Camera should follow this object
        this.addComponent(new Collider(22, 29));
        this.addComponent(new Velocity(0, 0));
        this.addComponent(new SoundTrigger("footstepBrick.ogg"));
        this.addComponent(new Viewer());
        this.addComponent(new AnimationController());
        this.addComponent(new BehaviourComponent(new PlayerScript()));		// Simple movmeent script

        // Get access to most used components
        velocity = getComponent(Velocity.class);
        position = getComponent(WorldPosition.class);
        spriteRenderer = getComponent(SpriteRenderer.class);
    }

    @Override
    public void receiveMessage(String message, GameObject sender) {
        super.receiveMessage(message, sender);
    }
}
