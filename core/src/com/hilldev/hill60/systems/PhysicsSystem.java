package com.hilldev.hill60.systems;

import java.util.List;

import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.Collider;
import com.hilldev.hill60.components.Velocity;
import com.hilldev.hill60.components.WorldPosition;

public class PhysicsSystem extends IEntitySystem {

	public PhysicsSystem(IEngine engine) {
		super(engine);
	}

	public void checkAndResolveCollsion(GameObject obj1, GameObject obj2) {
		float x11 = obj1.getComponent(WorldPosition.class).x
				- ((obj1.getComponent(Collider.class).width) / 2);
		float x12 = obj1.getComponent(WorldPosition.class).x
				+ ((obj1.getComponent(Collider.class).width) / 2);
		float y11 = obj1.getComponent(WorldPosition.class).y
				- ((obj1.getComponent(Collider.class).height) / 2);
		float y12 = obj1.getComponent(WorldPosition.class).y
				+ ((obj1.getComponent(Collider.class).height) / 2);
		float x21 = obj2.getComponent(WorldPosition.class).x
				- ((obj2.getComponent(Collider.class).width) / 2);
		float x22 = obj2.getComponent(WorldPosition.class).x
				+ ((obj2.getComponent(Collider.class).width) / 2);
		float y21 = obj2.getComponent(WorldPosition.class).y
				- ((obj2.getComponent(Collider.class).height) / 2);
		float y22 = obj2.getComponent(WorldPosition.class).y
				+ ((obj2.getComponent(Collider.class).height) / 2);
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
				for (GameObject o2 : list) {
					if (canCollide(o2)) {
						checkAndResolveCollsion(o1, o2);
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
