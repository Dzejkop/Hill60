package com.hilldev.hill60;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.hilldev.hill60.systems.RenderingSystem;

public class InputManager {

    private static final int UP_KEY = Input.Keys.UP;
    private static final int DOWN_KEY = Input.Keys.DOWN;
    private static final int LEFT_KEY = Input.Keys.LEFT;
    private static final int RIGHT_KEY = Input.Keys.RIGHT;

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
        return Gdx.input.isKeyPressed(UP_KEY);
    }

    public boolean downArrowPressed() {
        return Gdx.input.isKeyPressed(DOWN_KEY);
    }

    public boolean leftArrowPressed() {
        return Gdx.input.isKeyPressed(LEFT_KEY);
    }

    public boolean rightArrowPressed() {
        return Gdx.input.isKeyPressed(RIGHT_KEY);
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
