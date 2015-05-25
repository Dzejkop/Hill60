package com.hilldev.hill60.objects;

import com.hilldev.hill60.Hill60Main;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Wall extends GameObject {

    public Wall(int x, int y) {
        super();

        // Set a tag
        this.tag = "Wall";

        // Connect to main
        Hill60Main main = Hill60Main.getInstance();

        // Get the resource manager
        ResourceManager manager = main.resourceManager;

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
