package com.hilldev.hill60.systems;

import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.WorldPosition;

public class BoardSystem extends IEntitySystem {
	
    public BoardSystem(IEngine engine) {
		super(engine);
	}
    
    @Override
    public void update() {
        for(GameObject o : engine.getObjectList()) {
            if(meetsConditions(o)) processObject(o);
        }
    }
    
    @Override
    protected boolean meetsConditions(GameObject obj) {
        return obj.hasComponent(BoardPosition.class);
    }
    
    @Override
    protected void processObject(GameObject obj) {
    	float worldX = obj.getComponent(WorldPosition.class).x;
    	float worldY = obj.getComponent(WorldPosition.class).y;
        obj.getComponent(BoardPosition.class).x = (int) (worldX/25);
        obj.getComponent(BoardPosition.class).x = (int) (worldY/25);
    }
}
