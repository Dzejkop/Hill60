package com.hilldev.hill60;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.hilldev.hill60.systems.RenderingSystem;

public class InputManager {

    IEngine engine;

    public InputManager(IEngine engine) {
        this.engine = engine;
    }

    public boolean keyPressed(int i) {
        return Gdx.input.isKeyPressed(i);
    }

    public boolean keyJustPressed(int i ) {
        return Gdx.input.isKeyJustPressed(i);
    }

    public boolean upArrowPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.UP);
    }

    public boolean downArrowPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.DOWN);
    }

    public boolean leftArrowPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT);
    }

    public boolean rightArrowPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }

    public Vector2 getMousePos() {
        RenderingSystem rSystem = engine.getSystem(RenderingSystem.class);

        float x = Gdx.input.getX();
        float y = Gdx.input.getY();

        Vector3 mPos = new Vector3(x,y,0);

        rSystem.getCamera().unproject(mPos);

        return new Vector2(mPos.x, mPos.y);
    }

    public Vector2 getMousePosOnBoard() {
        return null;
    }

}
