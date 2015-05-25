package com.hilldev.hill60.objects;

import com.hilldev.hill60.Hill60Main;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Floor extends GameObject {

    public Floor(int x, int y) {
        super();

        // Connect to main
        Hill60Main main = Hill60Main.getInstance();

        // Get the resource manager
        ResourceManager manager = main.resourceManager;

        this.addComponent(new BoardPosition(x, y));
        this.addComponent(new WorldPosition(0, 0));
        this.addComponent(new SpriteRenderer(manager.getSprite("Floor.png"), 0, 0, 0));
        this.addComponent(new Visibility());
    }

    @Override
    public void receiveMessage(String message, GameObject sender) {
        super.receiveMessage(message, sender);
    }
}
