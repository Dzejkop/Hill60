package com.hilldev.hill60.objects.HUD;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.objects.Player;
import com.hilldev.hill60.systems.RenderingSystem;

public class HudManager extends GameObject {

	ItemDisplay itemDisplay;
	ItemIcon shovelIcon;
	ItemIcon bigBombIcon;
	ItemIcon smallBombIcon;
	ItemIcon mediumBombIcon;
	public Player player;

	public HudManager(IEngine engine) {
		super(engine);

        // Get the player
        player = (Player)engine.findObject("Player");

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
		bigBombIcon.setPos(w / 2, 40);
		mediumBombIcon.setPos(w / 2, 40);
		smallBombIcon.setPos(w / 2, 40);

		setAllInactive();
		shovelIcon.isActive=true;
	}

	public void setAllInactive() {
		shovelIcon.isActive=false;
		bigBombIcon.isActive=false;
		smallBombIcon.isActive=false;
		mediumBombIcon.isActive=false;
	}

	public void update() {
		switch (player.playerScript.getCurrentItem()){
		case "Shovel":
			setAllInactive();
			shovelIcon.isActive=true;
			break;
		case "SmallBomb":
			setAllInactive();
			smallBombIcon.isActive=true;
			break;
		case "MediumBomb":
			setAllInactive();
			mediumBombIcon.isActive=true;
			break;
		case "BigBomb":
			setAllInactive();
			bigBombIcon.isActive=true;
			break;
		default:
			break;
		}
	}

}
