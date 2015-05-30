package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.GuiSprite;


public class Hud extends GameObject{
	

    public Hud(IEngine engine) {
        super(engine);
        ResourceManager manager = engine.getResourceManager();
        this.addComponent(new GuiSprite(manager.getSprite("BigBomb"), 0, 0));
	}


}
