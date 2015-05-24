package com.hilldev.hill60.systems;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.components.SoundTrigger;
import com.hilldev.hill60.components.WorldPosition;
import com.hilldev.hill60.components.Collider;
import com.hilldev.hill60.components.Velocity;

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
		Velocity v = obj1.getComponent(Velocity.class);

        float xO1 = p1.x + c1.x;    // Middle x coord of the first collider
        float yO1 = p1.y + c1.y;    // Middle y
        float xO2 = p2.x + c2.x;    // Middle x coord of the second collider
        float yO2 = p2.y + c2.y;    // Middle y

		float distanceX = ((c1.width + c2.width) / 2 + Math.abs(v.x));
		float distanceY = ((c1.height + c2.height) / 2)+ Math.abs(v.y);

		if (xO2 + distanceX > xO1 && xO2 - distanceX < xO1
				&& yO2 + distanceY > yO1 && yO2 - distanceY < yO1) {
			return true;
		} else {
			return false;
		}
	}

	private void collisonHandling(GameObject obj1, GameObject obj2) {
		
        // Get  components
		Collider c1 = obj1.getComponent(Collider.class);
		Collider c2 = obj2.getComponent(Collider.class);
		WorldPosition p1 = obj1.getComponent(WorldPosition.class);
		WorldPosition p2 = obj2.getComponent(WorldPosition.class);
		Velocity v = obj1.getComponent(Velocity.class);

		float distanceX = ((c1.width + c2.width) / 2 + Math.abs(v.x));
		float distanceY = ((c1.height + c2.height) / 2)+ Math.abs(v.y);

		float xO1 = p1.x + c1.x;    // Middle x coord of the first collider
		float yO1 = p1.y + c1.y;    // Middle y
		float xO2 = p2.x + c2.x;    // Middle x coord of the second collider
		float yO2 = p2.y + c2.y;    // Middle y
		
		// Object on the right
		if(objOnRight(v, distanceX, distanceY, xO1, yO1, xO2, yO2)) {
			v.x = 0;
			if(inDebugMode) System.out.println("Collision on the right");
		
		// Object on the left
		} else if(objOnLeft(v, distanceX, distanceY, xO1, yO1, xO2, yO2)) {
			v.x = 0;
			if(inDebugMode) System.out.println("Collision on the left");
		
		// Object above
		} else if(objAbove(v, distanceX, distanceY, xO1, yO1, xO2, yO2)) {
			v.y = 0;
			if(inDebugMode) System.out.println("Collision above");
		
		// Object bellow
		} else if(objBellow(v, distanceX, distanceY, xO1, yO1, xO2, yO2)) {
			v.y = 0;
			if(inDebugMode) System.out.println("Collision bellow");
		
		// Object in the right corners
		} else if(objInRightCorners(v, distanceX, distanceY, xO1, yO1, xO2, yO2)) {
			v.x = 0;
			v.y = 0;
			if(inDebugMode) System.out.println("Collision in the right corners");
		
		// Object in the left corners
		} else if(objInLeftCorners(v, distanceX, distanceY, xO1, yO1, xO2, yO2)) {
			v.x = 0;
			v.y = 0;
			if(inDebugMode) System.out.println("Collision in the left corners");
		
		// Object somewhere else
		} else {
			if(inDebugMode) System.out.println("Other collision(?)");
		}
	}

	private boolean objOnRight(Velocity v, float distanceX, float distanceY,
			float xO1, float yO1, float xO2, float yO2) {
		return v.x < 0 && xO1 - distanceX < xO2 && xO1 > xO2
				&& xO1 - distanceX - xO2 > yO2 - distanceY - yO1
				&& xO1 - distanceX - xO2 > yO1 - distanceY - yO2;
	}

	private boolean objOnLeft(Velocity v, float distanceX, float distanceY,
			float xO1, float yO1, float xO2, float yO2) {
		return v.x > 0 && xO2 - distanceX < xO1 && xO2 > xO1
				&& xO2 - distanceX - xO1 > yO2 - distanceY - yO1
				&& xO2 - distanceX - xO1 > yO1 - distanceY - yO2;
	}

	private boolean objAbove(Velocity v, float distanceX, float distanceY,
			float xO1, float yO1, float xO2, float yO2) {
		return v.y > 0 && yO2 - distanceY < yO1 && yO2 > yO1
				&& yO2 - distanceY - yO1 > xO1 - distanceX - xO2
				&& yO2 - distanceY - yO1 > xO2 - distanceX - xO1;
	}

	private boolean objBellow(Velocity v, float distanceX, float distanceY,
			float xO1, float yO1, float xO2, float yO2) {
		return v.y < 0 && yO1 - distanceY < yO2 && yO1 > yO2
				&& yO1 - distanceY - yO2 > xO1 - distanceX - xO2
				&& yO1 - distanceY - yO2 > xO2 - distanceX - xO1;
	}

	private boolean objInRightCorners(Velocity v, float distanceX,
			float distanceY, float xO1, float yO1, float xO2, float yO2) {
		return v.x != 0 && v.y != 0 && xO2 - distanceX < xO1 && xO2 > xO1
				&& (xO1 - distanceX - xO2 == yO1 - distanceY - yO2
				|| xO1 - distanceX - xO2 == yO2 - distanceY - yO1);
	}

	private boolean objInLeftCorners(Velocity v, float distanceX,
			float distanceY, float xO1, float yO1, float xO2, float yO2) {
		return v.x != 0 && v.y != 0 && xO2 - distanceX < xO1 && xO2 > xO1
				&& (xO2 - distanceX - xO1 == yO2 - distanceY - yO1
				|| xO2 - distanceX - xO1 == yO1 - distanceY - yO2);
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
	protected void processObject(GameObject obj) {}
}
