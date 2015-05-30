package com.hilldev.hill60.objects.Walls;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.ExplosionResistance;
import com.hilldev.hill60.objects.Wall;

public class IndestructibleWall extends Wall {
	
    public IndestructibleWall(IEngine engine, int x, int y) {
    	
        super(engine, x, y);

        this.addComponent(new ExplosionResistance(Integer.MAX_VALUE));
    }
}
