package com.hilldev.hill60.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.InputResponder;

import java.util.List;

public class InputSystem extends AEntitySystem {

    // The state of arrow keys
    boolean[] arrowsState;

	public InputSystem(IEngine engine) {
		super(engine);

        arrowsState = new boolean[4];
	}

	@Override
	public void update() {

        List<GameObject> list = engine.getObjectList();

        arrowsState[0] = Gdx.input.isKeyPressed(Input.Keys.UP);
        arrowsState[1] = Gdx.input.isKeyPressed(Input.Keys.DOWN);
        arrowsState[2] = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        arrowsState[3] = Gdx.input.isKeyPressed(Input.Keys.RIGHT);

        for(GameObject g : list) {
            if(meetsConditions(g)) processObject(g);
        }

	}

    @Override
    protected void processObject(GameObject obj) {
        obj.getComponent(InputResponder.class).set(arrowsState[0], arrowsState[1], arrowsState[2], arrowsState[3]);
    }

    @Override
    protected boolean meetsConditions(GameObject obj) {
        return obj.hasComponent(InputResponder.class);
    }
}
