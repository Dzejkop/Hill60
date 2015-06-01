package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.Scripts.AIScript;
import com.hilldev.hill60.components.*;

public class Enemy extends Character {

    // Ease of access
    public WorldPosition position;
    public Velocity velocity;
    public SpriteRenderer spriteRenderer;

    public AIScript aiScript;

    public Enemy(IEngine engine, int x, int y) {
        super(engine, x, y);

        // Set a tag
        this.tag = "Enemy";

        // Get the resource manager
        ResourceManager manager = engine.getResourceManager();

        this.addComponent(new AnimationController());
        this.addComponent(new SpriteRenderer(manager.getSprite("CharacterNeutral"), 0, 0, 1, 2));

        // Redden the color a bit
        getComponent(SpriteRenderer.class).setColor(1f, 0.2f, 0.1f);

        // Add ai script
        aiScript = new AIScript();
        getComponent(BehaviourComponent.class).add(aiScript);

        // Get access to most used components
        velocity = getComponent(Velocity.class);
        position = getComponent(WorldPosition.class);
        spriteRenderer = getComponent(SpriteRenderer.class);
    }
}
