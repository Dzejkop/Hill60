package com.hilldev.hill60.objects.HUD;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.Scripts.HudManagerScript;
import com.hilldev.hill60.components.BehaviourComponent;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.objects.Player;
import com.hilldev.hill60.systems.RenderingSystem;

public class HudManager extends GameObject {

	public ItemDisplay itemDisplay;
	public ItemIcon shovelIcon;
	public ItemIcon bigBombIcon;
	public ItemIcon smallBombIcon;
	public ItemIcon mediumBombIcon;
	public Player player;
	public boolean endScreen;
	public boolean won;
	public float alpha;

	public HudManager(IEngine engine) {
		super(engine);

        // Get the player
        player = (Player)engine.findObject("Player");
        endScreen=false;
        won=false;
        this.tag = "HUD";
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

		itemDisplay.setPos(w / 2, 20);
		shovelIcon.setPos(w / 2, 40);
		smallBombIcon.setPos(w / 2, 40);
		mediumBombIcon.setPos(w / 2, 40);
		bigBombIcon.setPos(w / 2, 40);

        this.addComponent(new BehaviourComponent(new HudManagerScript()));
	}

}
