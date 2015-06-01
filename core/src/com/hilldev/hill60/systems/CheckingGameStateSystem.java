package com.hilldev.hill60.systems;

import java.util.ArrayList;
import java.util.List;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BehaviourComponent;
import com.hilldev.hill60.components.Collider;
import com.hilldev.hill60.objects.Character;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.objects.HUD.HudManager;

public class CheckingGameStateSystem extends AEntitySystem {

	List<Character> characterList = new ArrayList<Character>();
	HudManager hud;

	public CheckingGameStateSystem(IEngine engine) {
		super(engine);
	}

	@Override
	public void update() {
		if (characterList.size() == 0) {
			characterList.clear();
			List<GameObject> objects = engine.getObjectList();
			for (GameObject obj : objects) {
				if (meetsConditions(obj))
					characterList.add((Character) obj);
			}

		}
		hud = (HudManager)engine.findObject("HudManager");
		List<Character> deleteList = new ArrayList<Character>();
		if (characterList.size() > 1)
			for (Character obj : characterList) {
				if (!obj.isAlive) {
					Collider col = obj.getComponent(Collider.class);
					obj.deleteComponent(col);
					obj.isActive = false;
					obj.getComponent(BehaviourComponent.class).clear();
					if (obj.tag == "Player") {
						hud.endScreen = true;
						hud.won = false;
					}

					deleteList.add(obj);

					VisibilitySystem.DEBUG_MODE = true;
				}
			}
		else {
			hud.endScreen = true;
			hud.won = true;
		}
		for (GameObject obj:deleteList){
			characterList.remove(obj);	
			engine.destroyObject(obj);
		}

	}

	@Override
	protected boolean meetsConditions(GameObject obj) {
		return (obj.tag == "Enemy" || obj.tag == "Player");
	}

	@Override
	protected void processObject(GameObject obj) {
		// TODO Auto-generated method stub

	}

}
