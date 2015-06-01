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

    public PlayerScript playerScript;

    public Player(IEngine engine, int x, int y) {
    	
        super(engine, x, y);

        // Set a tag
        this.tag = "Player";

        // Get the resource manager
        ResourceManager manager = engine.getResourceManager();

        this.addComponent(new CameraTag());
        this.addComponent(new ViewerTag());
        this.addComponent(new AnimationController());
        this.addComponent(new SpriteRenderer(manager.getSprite("CharacterNeutral"), 0, 0, 1, 2));

        // Darken the color a bit
        getComponent(SpriteRenderer.class).setColor(0.2f, 0.2f, 0.1f);

        // Add player script
        playerScript = new PlayerScript();
        getComponent(BehaviourComponent.class).add(playerScript);

        // Get access to most used components
        velocity = getComponent(Velocity.class);
        position = getComponent(WorldPosition.class);
        spriteRenderer = getComponent(SpriteRenderer.class);
    }
}
