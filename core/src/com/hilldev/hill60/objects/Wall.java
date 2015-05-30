package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.*;

public class Wall extends GameObject {

    public Wall(IEngine engine, int x, int y) {
    	
        super(engine);

        // Set a tag
        this.tag = "Wall";

        this.addComponent(new BoardPosition(x, y));
        this.addComponent(new WorldPosition(0, 0));
        this.addComponent(new Collider(100, 100));
    }

    @Override
    public void receiveMessage(String message, GameObject sender) {
        super.receiveMessage(message, sender);
    }
}
