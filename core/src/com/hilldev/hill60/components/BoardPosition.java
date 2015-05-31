package com.hilldev.hill60.components;

import com.badlogic.gdx.math.Vector2;

public class BoardPosition extends AComponent {
	public static final float TILE_SIZE = 100;

    public BoardPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public BoardPosition(Vector2 v) {
        this.x = (int)v.x;
        this.y = (int)v.y;
    }

    public Vector2 getVector() {
        return new Vector2(x, y);
    }

    public boolean placedOnBoard = false;
    public int x;
	public int y;
}
