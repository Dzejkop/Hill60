package com.hilldev.hill60.Scripts;

import com.hilldev.hill60.components.*;
import com.hilldev.hill60.objects.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Ta klasa ogarnia ruch postaci, wyzwalanie dźwięku
 */

public class CharacterScript implements Behaviour {

	// Hardcoded values
	public static final int RUN_ANIMATION_SPEED = 3; // How fast animation
														// progresses while
														// running
	public static final int SNEAK_ANIMATION_SPEED = 20; // How fast animation
														// progresses while
														// sneaking
	public static final float RUN_STEP_LOUDNESS = 10; // How loud each step is
	public static final int RUN_STEP_INTERVAL = 15; // How often the step sound
													// is played
	public static final float RUN_SPEED = 4; // How fast the character runs
	public static final float SNEAK_SPEED = 2; // How fast the character sneaks

	// Ease of access
	public WorldPosition position;
	public Velocity velocity;
	public SpriteRenderer spriteRenderer;

	// Movement variables
	public boolean inSneakMode = false;
	public boolean goingLeft = false;
	public boolean goingRight = false;
	public boolean goingUp = false;
	public boolean goingDown = false;

	public float runVelocity = RUN_SPEED;
	public float sneakVelocity = SNEAK_SPEED;

	public List<Item> items = new ArrayList<>();

	@SuppressWarnings("unused")
	private BehaviourComponent parentComponent;
	private Character parent;

	@Override
	public void create(BehaviourComponent parentComponent) {
		this.parentComponent = parentComponent;
		this.parent = (Character) parentComponent.getParent();

		// Connect to components
		velocity = parent.getComponent(Velocity.class);

		// Create the item list
		items = new ArrayList<>();
		items.add(Item.getShovel());
		items.add(Item.getSmallBomb());
		items.add(Item.getMediumBomb());
		items.add(Item.getBigBomb());
	}

	@Override
	public void run() {

		float veloc = runVelocity;
		if (inSneakMode)
			veloc = sneakVelocity;

		float xv = 0;
		float yv = 0;

		if (goingUp)
			yv = veloc;
		else if (goingDown)
			yv = -veloc;

		if (goingRight)
			xv = veloc;
		else if (goingLeft)
			xv = -veloc;

		velocity.x = xv;
		velocity.y = yv;
	}

	// Items
	public List<Item> getItems() {
		return items;
	}

    public Item getItem(String itemName) {
        for(Item i : items) {
            if(i.name.equals(itemName)) return i;
        }
        return null;
    }

	public static class Item {
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

		public void use() {
			cooldown = maxCooldown;
			isReady = false;
		}

        public float getAlpha() {
            return (float)cooldown / (float)maxCooldown;
        }

		public boolean isReady() {
			return isReady;
		}

		public static Item getShovel() {
			return new Item("Shovel", 10);
		}

		public static Item getSmallBomb() {
			return new Item("SmallBomb", 50);
		}

		public static Item getMediumBomb() {
			return new Item("MediumBomb", 100);
		}

		public static Item getBigBomb() {
			return new Item("BigBomb", 150);
		}
	}
}
