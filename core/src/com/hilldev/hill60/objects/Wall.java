package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Wall extends GameObject {

    public Wall(IEngine engine, int x, int y) {
        super(engine);

        // Set a tag
        this.tag = "Wall";

        // Get the resource manager
        ResourceManager manager = engine.getResourceManager();

        this.addComponent(new BoardPosition(x, y));
        this.addComponent(new WorldPosition(0, 0));
        this.addComponent(new SpriteRenderer(manager.getSprite("Wall.png"),0, 10, 3));
        this.addComponent(new Collider(100, 100));
        this.addComponent(new ExplosionResistance(3));
        this.addComponent(new Visibility());
    }

    @Override
    public void receiveMessage(String message, GameObject sender) {
        super.receiveMessage(message, sender);
    }
}
