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
        if(obj.hasComponent(BoardPosition.class)
        && obj.hasComponent(WorldPosition.class)) return true;
        return false;
    }
    
    @Override
    protected void processObject(GameObject obj) {
    	float tileSize = BoardPosition.TILE_SIZE;
		BoardPosition boardPos = obj.getComponent(BoardPosition.class);
		WorldPosition worldPos = obj.getComponent(WorldPosition.class);
		worldPos.x = boardPos.x*tileSize;
        worldPos.y = boardPos.y*tileSize;
    }
}
