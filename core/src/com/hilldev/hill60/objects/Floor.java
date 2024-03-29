package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Floor extends GameObject {

    public Floor(IEngine engine, int x, int y) {
    	
        super(engine);
        
        // Get the resource manager
        ResourceManager manager = engine.getResourceManager();

        this.addComponent(new BoardPosition(x, y));
        this.addComponent(new WorldPosition(0, 0));
        this.addComponent(new SpriteRenderer(manager.getSprite("Floor"), 0, 0, 2.5f, 0));
        this.addComponent(new Visibility());
    }

    @Override
    public void receiveMessage(String message, GameObject sender) {
        super.receiveMessage(message, sender);
    }
}
