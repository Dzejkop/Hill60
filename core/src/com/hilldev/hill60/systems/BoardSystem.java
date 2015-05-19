package com.hilldev.hill60.systems;

import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.WorldPosition;
import com.hilldev.hill60.objects.Wall;

import java.util.ArrayList;
import java.util.List;

public class BoardSystem extends AEntitySystem {
	
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
        return obj.hasComponent(BoardPosition.class) && obj.hasComponent(WorldPosition.class);

    }

    @Override
    protected void processObject(GameObject obj) {
    	float tileSize = BoardPosition.TILE_SIZE;
		BoardPosition boardPos = obj.getComponent(BoardPosition.class);
		WorldPosition worldPos = obj.getComponent(WorldPosition.class);
		if(worldPos.boardDependent) {
			worldPos.x = boardPos.x*tileSize;
			worldPos.y = boardPos.y*tileSize;
		} else {
			boardPos.x = (int) (worldPos.x/(tileSize));
			boardPos.y = (int) (worldPos.y/(tileSize));
		}
    }

    public List<GameObject> getObjectsAt(int x, int y) {

        List<GameObject> objects = new ArrayList<>();

        BoardPosition boardPosition;
        for(GameObject o : engine.getObjectList()) {
            if(meetsConditions(o)) {
                boardPosition = o.getComponent(BoardPosition.class);

                if(boardPosition.x == x && boardPosition.y == y) objects.add(o);
            }
        }

        return objects;
    }

    public Wall getWallAt(int x, int y) {
        BoardPosition boardPosition;
        for(GameObject o : engine.getObjectList()) {
            if(meetsConditions(o) && o.tag == "Wall") {
                boardPosition = o.getComponent(BoardPosition.class);

                if(boardPosition.x == x && boardPosition.y == y) return (Wall)o;
            }
        }
        
        return null;
    }
}
