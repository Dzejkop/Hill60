package com.hilldev.hill60.objects.HUD;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.GuiSprite;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.systems.RenderingSystem;


public class HudManager extends GameObject {


    ItemDisplay itemDisplay;
    ItemIcon shovelIcon;
    ItemIcon bigBombIcon;
    ItemIcon smallBombIcon;
    ItemIcon mediumBombIcon;


    public HudManager(IEngine engine) {
        super(engine);
        ResourceManager manager = engine.getResourceManager();
        itemDisplay = new ItemDisplay(engine);
        shovelIcon = new ItemIcon(engine, "ShovelIcon");
        bigBombIcon = new ItemIcon(engine, "BigBomb");
        mediumBombIcon = new ItemIcon(engine, "MediumBomb");
        smallBombIcon = new ItemIcon(engine, "SmallBomb");


        engine.createObject(shovelIcon);
        engine.createObject(bigBombIcon);
        engine.createObject(mediumBombIcon);
        engine.createObject(smallBombIcon);
        engine.createObject(itemDisplay);
        int w = RenderingSystem.SCREEN_WIDTH;
        int h = RenderingSystem.SCREEN_HEIGHT;
        itemDisplay.setPos(w/2, 20);
        shovelIcon.setPos(w/2, 40);
        bigBombIcon.setPos(w/2, 40);
        mediumBombIcon.setPos(w/2, 40);
        smallBombIcon.setPos(w/2, 40);


	}


}
