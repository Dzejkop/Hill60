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
		//Velocity v = obj1.getComponent(Velocity.class);

		float x1 = p1.x + c1.width + c2.width;
		float y1 = p1.y + c2.height + c1.height;
		float x2 = p2.x + c2.width + c1.width;
		float y2 = p2.y + c2.height + c1.height;

		if (x1 > p2.x && p1.x < x2 && y1 > p2.y && p1.y < y2) {
			return true; //
		} else
			return false;

		/*
		 * Twoj zachowany kod
		 * 
		 * // Horizontal collision detected if ((y11 <= y21 && y21 <= y12) ||
		 * (y11 <= y22 && y22 <= y12)) {
		 * 
		 * // Case 1 - Object on left if(v.x <= 0 && x11 <= x22 && x12 >= x22) {
		 * v.x = 0; }
		 * 
		 * // Case 2 - object on right if(v.x >= 0 && x11 <= x21 && x12 >= x21)
		 * { v.x = 0; } }
		 * 
		 * // Vertical collision detected if((x11 <= x21 && x21 <= x12) || (x11
		 * <= x22 && x22 <= x12)) { // Case 1 - Object above if(v.y >= 0 && y11
		 * <= y21 && y12 >= y21) { v.y = 0; }
		 * 
		 * // Case 2 - Object below if(v.y <= 0 && y11 <= y22 && y12 >= y22) {
		 * v.y = 0; } }
		 */
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
						if (checkCollision(o1, o2)){
							
						}
					}
				}

				// Execute movement if possible
				w.x += v.x;
				w.y += v.y;
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
