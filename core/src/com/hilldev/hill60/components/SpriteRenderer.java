package com.hilldev.hill60.components;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteRenderer extends AComponent {
	
	public SpriteRenderer(Sprite sprite) {
		this.sprite = sprite;
	}

    public Sprite sprite;

    public float x = 0; // Horizontal offset
    public float y = 0; // Vertical offset
}
