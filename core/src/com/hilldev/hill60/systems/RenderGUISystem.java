package com.hilldev.hill60.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.GuiSprite;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.objects.HUD.HudManager;

public class RenderGUISystem extends AEntitySystem {
	private static final int MAX_LAYER = 1;
	public int screenWidth;
	public int screenHeight;
	boolean renderGui;
	SpriteBatch batch;
	public RenderGUISystem(IEngine engine) {
		super(engine);
	}

	@Override
	public void start() {
		// simple class to hold sprite and its x,y
		super.start();
		// Set default state for showing gui
		switchGui(true);
		// Get vars from rendering system
		screenWidth = RenderingSystem.SCREEN_WIDTH;
		screenHeight = RenderingSystem.SCREEN_HEIGHT;
		batch = new SpriteBatch();
		Gdx.gl.glEnable(GL20.GL_BLEND);
	}

	public void switchGui(boolean status) {
		renderGui = status;
	}

	public void switchGui() {
		switchGui(!renderGui);
	}

	@Override
	public void update() {
		int j=0;
		if (renderGui) {
			for (int i = 0; i <= MAX_LAYER; i++)
				for (GameObject obj : engine.getObjectList())
					if (meetsConditions(obj)) {
						GuiSprite guiSprite = obj.getComponent(GuiSprite.class);
						if (guiSprite.layer == i) {
							float x = (guiSprite.x - (guiSprite.sprite
									.getWidth() / 2));
							float y = (guiSprite.y - (guiSprite.sprite
									.getHeight() / 2));
							batch.begin();
							guiSprite.sprite.setPosition(x, y);
							guiSprite.sprite.draw(batch,guiSprite.alpha);
							batch.end();
							j++;
						}
					}
		}
		if (j==0){
			HudManager hud=(HudManager)engine.findObject("HudManager");
			batch.begin();
			Sprite sprite2;
			Sprite sprite = engine.getResourceManager().getSprite("BlackTile");
			if(hud.won){
				sprite2 = engine.getResourceManager().getSprite("Victory");
                sprite2.setColor(Color.valueOf("C5A833"));
			}else{
				sprite2 = engine.getResourceManager().getSprite("GameOver");
                sprite2.setColor(Color.valueOf("800000"));
			}

			//sprite2.scale(1);
			sprite2.setPosition(screenWidth/2-(sprite2.getWidth()/2), screenHeight/2-(sprite2.getHeight()/2));
			sprite.scale(screenWidth);
			sprite.draw(batch,1-hud.alpha);
			sprite2.draw(batch,1-hud.alpha);
			batch.end();
		}

	}

	@Override
	protected boolean meetsConditions(GameObject obj) {
		if (obj.hasComponent(GuiSprite.class)&&obj.isActive)
			return true;
		return false;
	}

	@Override
	protected void processObject(GameObject obj) {
	}

}
