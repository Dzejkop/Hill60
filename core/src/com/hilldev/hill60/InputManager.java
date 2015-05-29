package com.hilldev.hill60;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class InputManager {

    public static boolean keyPressed(int i) {
        return Gdx.input.isKeyPressed(i);
    }

    public static boolean keyJustPressed(int i ) {
        return Gdx.input.isKeyJustPressed(i);
    }

    public static boolean upArrowPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.UP);
    }

    public static boolean downArrowPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.DOWN);
    }

    public static boolean leftArrowPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.LEFT);
    }

    public static boolean rightArrowPressed() {
        return Gdx.input.isKeyPressed(Input.Keys.RIGHT);
    }

    public static Vector2 getMousePos() {
        return null;
    }

    public static Vector2 getMousePosOnBoard() {
        return null;
    }

}
