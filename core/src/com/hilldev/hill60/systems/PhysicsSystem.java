package com.hilldev.hill60.systems;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.Collider;
import com.hilldev.hill60.components.Velocity;
import com.hilldev.hill60.components.WorldPosition;

public class PhysicsSystem extends AEntitySystem {

	boolean inDebugMode = false;

	public PhysicsSystem(IEngine engine) {
		super(engine);
	}

	@Override
	public void update() {
		inDebugMode = Gdx.input.isKeyPressed(Input.Keys.D);
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

	public boolean checkCollision(GameObject obj1, GameObject obj2) {
        // Get  components
		Collider c1 = obj1.getComponent(Collider.class);
		Collider c2 = obj2.getComponent(Collider.class);
		WorldPosition p1 = obj1.getComponent(WorldPosition.class);
		WorldPosition p2 = obj2.getComponent(WorldPosition.class);

        float xO1 = p1.x + c1.x;    // Middle x coord of the first collider
        float yO1 = p1.y + c1.y;    // Middle y
        float xO2 = p2.x + c2.x;    // Middle x coord of the second collider
        float yO2 = p2.y + c2.y;    // Middle y

		float distanceX = (c1.width + c2.width+2) / 2;
		float distanceY = (c1.height + c2.height+2) / 2;

		if (xO2 + distanceX > xO1 && xO2 - distanceX < xO1
				&& yO2 + distanceY > yO1 && yO2 - distanceY < yO1) {
			return true;
		} else
			return false;
	}

	private void collisonHandling(GameObject obj1, GameObject obj2) {
        // Get  components
		Collider c1 = obj1.getComponent(Collider.class);
		Collider c2 = obj2.getComponent(Collider.class);
		WorldPosition p1 = obj1.getComponent(WorldPosition.class);
		WorldPosition p2 = obj2.getComponent(WorldPosition.class);
		Velocity v = obj1.getComponent(Velocity.class);

		float distanceX = (c1.width + c2.width) / 2;
		float distanceY = (c1.height + c2.height) / 2;

		double distanceXY2 = Math.sqrt((distanceX * distanceX) +(distanceY * distanceY));

		float xO1 = p1.x + c1.x;    // Middle x coord of the first collider
		float yO1 = p1.y + c1.y;    // Middle y
		float xO2 = p2.x + c2.x;    // Middle x coord of the second collider
		float yO2 = p2.y + c2.y;    // Middle y
		float deltaX = Math.abs(xO1 - xO2);
		float deltaY = Math.abs(yO1 - yO2);
		double deltaXY2 = Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));

		// Case 1 - Object on left
		if (v.x > 0 && xO2 - distanceX < xO1 && xO2 > xO1
				&& xO2 - distanceX - xO1 > yO2 - distanceY - yO1
				&& xO2 - distanceX - xO1 > yO1 - distanceY - yO2) {
			v.x = 0;
			if (inDebugMode) {
				System.out.println("-------");
				System.out.println("left");
			}
		} else
		// Case 2 - Object on right
		if (v.x < 0 && xO1 - distanceX < xO2 && xO1 > xO2
				&& xO1 - distanceX - xO2 > yO2 - distanceY - yO1
				&& xO1 - distanceX - xO2 > yO1 - distanceY - yO2) {
			v.x = 0;
			if (inDebugMode) {
				System.out.println("-------");
				System.out.println("right");
			}
		} else
		// Case 3 - Object bellow
		if (v.y > 0 && yO2 - distanceY < yO1 && yO2 > yO1
				&& yO2 - distanceY - yO1 > xO1 - distanceX - xO2
				&& yO2 - distanceY - yO1 > xO2 - distanceX - xO1) {
			v.y = 0;
			if (inDebugMode) {
				System.out.println("-------");
				System.out.println("bellow");
			}

		} else
		// Case 4 - Object above
		if (v.y < 0 && yO1 - distanceY < yO2 && yO1 > yO2
				&& yO1 - distanceY - yO2 > xO1 - distanceX - xO2
				&& yO1 - distanceY - yO2 > xO2 - distanceX - xO1) {
			v.y = 0;
			if (inDebugMode) {
				System.out.println("-------");
				System.out.println("above");
			}
		} else
		// Case 5 - left corners
		if (v.x != 0
				&& v.y != 0
				&& xO2 - distanceX < xO1
				&& xO2 > xO1
				&& (xO2 - distanceX - xO1 == yO2 - distanceY - yO1 || xO2
						- distanceX - xO1 == yO1 - distanceY - yO2)) {
			v.x = 0;
			v.y = 0;
			if (inDebugMode) {
				System.out.println("-------");
				System.out.println("left");
				System.out.println("corners");
			}
		} else
		// Case 6 - right corners
		if (v.x != 0
				&& v.y != 0
				&& xO2 - distanceX < xO1
				&& xO2 > xO1
				&& (xO1 - distanceX - xO2 == yO1 - distanceY - yO2 || xO1
						- distanceX - xO2 == yO2 - distanceY - yO1)) {
			v.x = 0;
			v.y = 0;
			if (inDebugMode) {
				System.out.println("-------");
				System.out.println("right");
				System.out.println("corners");
			}
		}else
		{
			if (inDebugMode) {
				System.out.println("-------");
				System.out.println("other?");}
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
