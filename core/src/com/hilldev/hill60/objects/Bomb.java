package com.hilldev.hill60.objects;

import com.hilldev.hill60.Hill60Main;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Bomb extends GameObject {

    public Bomb(int x, int y) {
        super();

        // Set a tag
        this.tag = "Bomb";

        // Connect to main
        Hill60Main main = Hill60Main.getInstance();

        // Get the resource manager
        ResourceManager manager = main.resourceManager;

        this.addComponent(new SpriteRenderer(manager.getSprite("Player.png")));
        this.addComponent(new WorldPosition(0, 0));
        this.addComponent(new BoardPosition(x, y));
        this.addComponent(new Layer(4));
        this.addComponent(new ExplosionSpawn(1, 3));
    }
}
