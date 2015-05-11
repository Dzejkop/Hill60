package com.hilldev.hill60.systems;

import java.util.List;

import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.Collider;
import com.hilldev.hill60.components.Velocity;
import com.hilldev.hill60.components.WorldPosition;

public class PhysicsSystem extends IEntitySystem {

	public PhysicsSystem(IEngine engine) {
		super(engine);
	}

	public void checkAndResolveCollision(GameObject obj1, GameObject obj2) {

        Collider c1 = obj1.getComponent(Collider.class);
        Collider c2 = obj2.getComponent(Collider.class);
        WorldPosition p1 = obj1.getComponent(WorldPosition.class);
        WorldPosition p2 = obj2.getComponent(WorldPosition.class);

		float x11 = p1.x
				- ((c1.width) / 2);
		float x12 = p1.x
				+ ((c1.width) / 2);
		float y11 = p1.y
				- ((c1.height) / 2);
		float y12 = p1.y
				+ ((c1.height) / 2);
		float x21 = p2.x
				- ((c2.width) / 2);
		float x22 = p2.x
				+ ((c2.width) / 2);
		float y21 = p2.y
				- ((c2.height) / 2);
		float y22 = p2.y
				+ ((c2.height) / 2);

		if (x11 > x22 && x12 < x21 && y11 > y22 && y12 < y21)
			if(x11 > x22 && x12 < x21 ){
				
			}else
			if(y11 > y22 && y12 < y21){
				
			}
	}



	@Override
	public void update() {
		List<GameObject> list = engine.getObjectList();
		for (GameObject o1 : list) {
			if (meetsConditions(o1)) {

                // Execute movement
                WorldPosition w = o1.getComponent(WorldPosition.class);
                Velocity v = o1.getComponent(Velocity.class);

                w.x += v.x;
                w.y += v.y;

				for (GameObject o2 : list) {
					if (o1.equals(o2) == false && canCollide(o2)) {
						checkAndResolveCollision(o1, o2);
					}
				}
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
