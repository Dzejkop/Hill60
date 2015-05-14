package com.hilldev.hill60.systems;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.ObjectID;
import com.hilldev.hill60.components.SpriteRenderer;
import com.hilldev.hill60.components.WorldPosition;

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
        if(obj.hasComponent(BoardPosition.class)
        && obj.hasComponent(WorldPosition.class)) return true;
        return false;
    }
    
    private void setMidXY(GameObject obj){
		WorldPosition worldPos = obj.getComponent(WorldPosition.class);
		Sprite sprite = obj.getComponent(SpriteRenderer.class).sprite;
		if(obj.getComponent(ObjectID.class).getID()=="wall")
		{
			worldPos.midX=worldPos.x+sprite.getWidth()/2;
			worldPos.midY=worldPos.y+(sprite.getHeight()-20)/2;
		}else{
			worldPos.midX=worldPos.x+sprite.getWidth()/2;
			worldPos.midY=worldPos.y+sprite.getHeight()/2;	
		}
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
			boardPos.x = (int) (worldPos.x/tileSize);
			boardPos.y = (int) (worldPos.y/tileSize);
		}
		setMidXY(obj);
    }
}
