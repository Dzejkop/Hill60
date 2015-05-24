package com.hilldev.hill60.components;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteRenderer extends AComponent {
	
	public SpriteRenderer(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public SpriteRenderer(Sprite sprite,float x, float y) {
		this.sprite = sprite;
		this.x=x;
		this.y=y;
	}

    public Sprite sprite;

    public float x = 0; // Horizontal offset
    public float y = 0; // Vertical offset
}
