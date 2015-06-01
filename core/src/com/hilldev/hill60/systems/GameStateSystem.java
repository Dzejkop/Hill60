package com.hilldev.hill60.systems;

import java.util.ArrayList;
import java.util.List;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BehaviourComponent;
import com.hilldev.hill60.components.Collider;
import com.hilldev.hill60.objects.Character;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.objects.HUD.HudManager;

public class GameStateSystem extends AEntitySystem {

	List<Character> characterList = new ArrayList<>();
	HudManager hud;

    enum GameState {
        Won,
        Lost,
        InProgress
    }
    GameState gameState = GameState.InProgress;

    int sinceEnd = 0;
    int maxTimeSinceEnd = 150;

	public GameStateSystem(IEngine engine) {
		super(engine);
	}

	@Override
	public void update() {

        // If lost or won, wait for a few frames and then exit
        if(gameState != GameState.InProgress) {
            sinceEnd++;
            if(sinceEnd >= maxTimeSinceEnd) {
                engine.quit();
            }
            return;
        }

        // Find player and enemies if the list is empty
		if (characterList.size() == 0) {
			characterList.clear();
			List<GameObject> objects = engine.getObjectList();
			for (GameObject obj : objects) {
				if (meetsConditions(obj))
					characterList.add((Character) obj);
			}

		}
		hud = (HudManager)engine.findObject("HudManager");
		List<Character> deleteList = new ArrayList<>();
		if (characterList.size() > 1)
			for (Character obj : characterList) {
				if (!obj.isAlive) {
					Collider col = obj.getComponent(Collider.class);
					obj.deleteComponent(col);
					obj.isActive = false;
					obj.getComponent(BehaviourComponent.class).clear();
					if (obj.tag.equals("Player")) {
                        gameState = GameState.Lost;
						hud.endScreen = true;
						hud.won = false;
					}

					deleteList.add(obj);

					VisibilitySystem.DEBUG_MODE = true;
				}
			}
		else {
            gameState = GameState.Won;
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
		return (obj.tag.equals("Enemy") || obj.tag.equals("Player"));
	}

	@Override
	protected void processObject(GameObject obj) {
	}

}
