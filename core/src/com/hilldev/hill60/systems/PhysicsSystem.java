package com.hilldev.hill60.systems;

import java.util.List;

import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.Collider;
import com.hilldev.hill60.components.Velocity;
import com.hilldev.hill60.components.WorldPosition;

public class PhysicsSystem extends AEntitySystem {

	public PhysicsSystem(IEngine engine) {
		super(engine);
	}

	public boolean checkCollision(GameObject obj1, GameObject obj2) {

		Collider c1 = obj1.getComponent(Collider.class);
		Collider c2 = obj2.getComponent(Collider.class);
		WorldPosition p1 = obj1.getComponent(WorldPosition.class);
		WorldPosition p2 = obj2.getComponent(WorldPosition.class);

		float x1 = p1.x + c1.width + c2.width;
		float y1 = p1.y + c2.height + c1.height;
		float x2 = p2.x + c2.width + c1.width;
		float y2 = p2.y + c2.height + c1.height;

		if (x1 > p2.x && p1.x < x2 && y1 > p2.y && p1.y < y2) {
			return true; //
		} else
			return false;
	}

	@Override
	public void update() {
		List<GameObject> list = engine.getObjectList();
		for (GameObject o1 : list) {

			// Get components
			WorldPosition w = o1.getComponent(WorldPosition.class);
			Velocity v = o1.getComponent(Velocity.class);

			// Only consider collisions if the object is moving
			if (meetsConditions(o1) && !v.isZero()) {

				// Check for collisions and block movement
				for (GameObject o2 : list) {

					// Make sure it's a different object
					if (o1.equals(o2) == false && canCollide(o2)) {
						if (checkCollision(o1, o2)) {
							collisonHandling(o1, o2);
						}
					}
				}

				// Execute movement if possible
				w.x += v.x;
				w.y += v.y;
			}
		}
	}

	private void collisonHandling(GameObject obj1, GameObject obj2) {
		Collider c1 = obj1.getComponent(Collider.class);
		Collider c2 = obj2.getComponent(Collider.class);
		WorldPosition p1 = obj1.getComponent(WorldPosition.class);
		WorldPosition p2 = obj2.getComponent(WorldPosition.class);
		Velocity v = obj1.getComponent(Velocity.class);
		float x1 = p1.x + c1.width + c2.width;
		float y1 = p1.y + c2.height + c1.height;
		float x2 = p2.x + c2.width + c1.width;
		float y2 = p2.y + c2.height + c1.height;
		// Horizontal collision detected
		if ((y1 <= y2 && y2 <= p1.y) || (y1 <= p2.y && p2.y <= p1.y)) {

			// Case 1 - Object on left
			if (v.x <= 0 && x1 <= p2.x && p1.x >= p2.x) {
				v.x = 0;
			}

			// Case 2 - object on right
			if (v.x >= 0 && x1 <= x2 && p1.x >= x2) {
				v.x = 0;
			}
		}

		// Vertical collision detected
		if ((x1 <= x2 && x2 <= p1.x) || (x1 <= p2.x && p2.x <= p1.x)) {
			// Case 1 - Object above
			if (v.y >= 0 && y1 <= y2 && p1.y >= y2) {
				v.y = 0;
			}

			// Case 2 - Object below
			if (v.y <= 0 && y1 <= p2.y && p1.y >= p2.y) {
				v.y = 0;
			}
		}

	}

	@Override
	protected boolean meetsConditions(GameObject obj) {
		return obj.hasComponent(Collider.class)
				&& obj.hasComponent(Velocity.class)
				&& obj.hasComponent(WorldPosition.class);
	}

	protected boolean canCollide(GameObject obj) {
		return obj.hasComponent(Collider.class)
				&& obj.hasComponent(WorldPosition.class);
	}

	@Override
	protected void processObject(GameObject obj) {

	}
}
