package com.hilldev.hill60.components;

public class BoardPosition extends AComponent {
	public static final float TILE_SIZE = 100;

    public BoardPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int x;
	public int y;
}
