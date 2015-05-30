package com.hilldev.hill60.systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.GuiSprite;
import com.hilldev.hill60.objects.GameObject;


public class RenderGUISystem extends AEntitySystem {
	public int screenWidth;
	public int screenHeight;
	boolean renderGui;
	SpriteBatch batch;
	    
	public RenderGUISystem(IEngine engine) {
		super(engine);
	}
	
    @Override
    public void start() {
    	//simple class to hold sprite and its x,y 
        super.start();
        // Set default state for showing gui
		switchGui(true);
		// Get vars from rendering system
		screenWidth=RenderingSystem.SCREEN_WIDTH;
		screenHeight=RenderingSystem.SCREEN_HEIGHT;
		batch=new SpriteBatch();
    }

	public void switchGui(boolean status){
		renderGui=status;
	}
	
	public void switchGui(){
		switchGui(!renderGui);
	}
	
	@Override
	public void update() {
		if(renderGui)
		for(GameObject obj: engine.getObjectList())
		if (meetsConditions(obj))
		{
			batch.begin();
			GuiSprite guiSprite = obj.getComponent(GuiSprite.class);
			batch.draw(guiSprite.sprite,guiSprite.x,guiSprite.y);
			batch.end();
		}
		
	}

	@Override
	protected boolean meetsConditions(GameObject obj) {
		if(obj.hasComponent(GuiSprite.class))	
			return true;
		return false;
	}

	@Override
	protected void processObject(GameObject obj) {}

}
