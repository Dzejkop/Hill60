package com.hilldev.hill60.objects.Walls;

import com.badlogic.gdx.graphics.Color;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.ExplosionResistance;
import com.hilldev.hill60.components.SpriteRenderer;
import com.hilldev.hill60.components.Visibility;
import com.hilldev.hill60.objects.Wall;

public class DirtWall extends Wall {
	
    public DirtWall(IEngine engine, int x, int y) {
    	
        super(engine, x, y);

        // Get the resource manager
        ResourceManager manager = engine.getResourceManager();

        this.addComponent(new SpriteRenderer(manager.getSprite("StoneWall"),0, 8, 2.5f, 3));
        this.addComponent(new ExplosionResistance(1));
        this.addComponent(new Visibility());
        
        this.getComponent(SpriteRenderer.class).color = Color.valueOf("A16F1F");
    }
}
