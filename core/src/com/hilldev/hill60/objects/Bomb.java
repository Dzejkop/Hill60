package com.hilldev.hill60.objects;

import com.hilldev.hill60.GameScreen;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Bomb extends GameObject {

    public Bomb(IEngine engine, int x, int y) {
        super(engine);

        // Set a tag
        this.tag = "Bomb";

        // Connect to main
        GameScreen main = ((GameScreen)engine);

        // Get the resource manager
        ResourceManager manager = main.resourceManager;

        this.addComponent(new SpriteRenderer(manager.getSprite("Player.png"), 0, 0, 4));
        this.addComponent(new WorldPosition(0, 0));
        this.addComponent(new BoardPosition(x, y));
        this.addComponent(new ExplosionSpawn(5, 5));
        this.addComponent(new Visibility());
    }
}
