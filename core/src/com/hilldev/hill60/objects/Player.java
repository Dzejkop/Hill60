package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.Scripts.PlayerScript;
import com.hilldev.hill60.components.*;

public class Player extends Character {

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

        this.addComponent(new CameraTag());
        this.addComponent(new Viewer());
        this.addComponent(new AnimationController());
        this.addComponent(new SpriteRenderer(manager.getSprite("CharacterNeutral"), 0, 0, 2));

        // Add player script
        getComponent(BehaviourComponent.class).add(new PlayerScript());

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
