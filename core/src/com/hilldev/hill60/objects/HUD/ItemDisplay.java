package com.hilldev.hill60.objects.HUD;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.GuiSprite;
import com.hilldev.hill60.objects.GameObject;

public class ItemDisplay extends GameObject implements HUDObject{
    public ItemDisplay(IEngine engine) {
        super(engine);

        ResourceManager manager = engine.getResourceManager();
        this.addComponent(new GuiSprite(manager.getSprite("ItemDisplay"), 0, 0));
    }

    @Override
    public void setPos(int x, int y) {
        GuiSprite s = getComponent(GuiSprite.class);
        s.x = x;
        s.y = y;
    }
}
