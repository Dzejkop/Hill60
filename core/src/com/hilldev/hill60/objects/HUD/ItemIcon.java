package com.hilldev.hill60.objects.HUD;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.GuiSprite;
import com.hilldev.hill60.objects.GameObject;

public class ItemIcon extends GameObject implements HUDObject {
	public ItemIcon(IEngine engine, String name) {
		super(engine);

		ResourceManager manager = engine.getResourceManager();

		this.addComponent(new GuiSprite(manager.getSprite(name), 0, 0, 1));
	}

	@Override
	public void setPos(int x, int y) {
		GuiSprite s = getComponent(GuiSprite.class);
		s.x = x;
		s.y = y;
	}

	@Override
	public void setLayer(int layer) {
		GuiSprite s = getComponent(GuiSprite.class);
		s.layer = layer;
	}
}
