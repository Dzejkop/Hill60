package com.hilldev.hill60.scripts;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.ExplosionResistance;
import com.hilldev.hill60.objects.BigBomb;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.objects.MediumBomb;
import com.hilldev.hill60.objects.SmallBomb;
import com.hilldev.hill60.systems.BoardSystem;

public class Item {
	public final static String[] ITEM_LIST = { "Shovel", "SmallBomb",
			"MediumBomb", "BigBomb" };
	public String name;
	private boolean isReady = true;
	private int maxCooldown;
	private int cooldown = 0;

	public Item(String name, int cooldown) {
		this.maxCooldown = cooldown;
		this.name = name;
	}

	public void update() {
		if (!isReady)
			cooldown--;
		if (cooldown <= 0) {
			isReady = true;
		}
	}

	public void use(String facingDirection, int x, int y, IEngine engine) {
		cooldown = maxCooldown;
		isReady = false;
		switch (name) {
		case "Shovel": {
			BoardSystem boardSystem = engine.getSystem(BoardSystem.class);
			switch (facingDirection) {
			case "up": {
				y++;
				break;
			}
			case "down": {
				y--;
				break;
			}

			case "left": {
				x--;
				break;
			}

			case "right": {
				x++;
				break;
			}
			}
			GameObject object = boardSystem.getWallAt(x, y);
			if (object == null)
				break;
			if (object.hasComponent(ExplosionResistance.class))
				if(object.getComponent(ExplosionResistance.class).resistancePoints<=CharacterScript.ITEM_POWER_LIST[0])
					engine.destroyObject(object);
			break;
		}
		case "SmallBomb":
			engine.createObject(new SmallBomb(engine, x, y));
			break;
		case "MediumBomb":
			engine.createObject(new MediumBomb(engine, x, y));
			break;
		case "BigBomb":
			engine.createObject(new BigBomb(engine, x, y));
			break;
		default:
			break;
		}
	}

	public float getAlpha() {
		return (float) cooldown / (float) maxCooldown;
	}

	public boolean isReady() {
		return isReady;
	}

	public static Item getShovel() {
		return new Item("Shovel", 100);
	}

	public static Item getSmallBomb() {
		return new Item("SmallBomb", 500);
	}

	public static Item getMediumBomb() {
		return new Item("MediumBomb", 1000);
	}

	public static Item getBigBomb() {
		return new Item("BigBomb", 1500);
	}
}