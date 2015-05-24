package com.hilldev.hill60.components;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteRenderer extends AComponent {
	
	public SpriteRenderer(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public SpriteRenderer(Sprite sprite,float x, float y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
	}

    public SpriteRenderer(Sprite sprite, float x, float y, int layer) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

    public Sprite sprite;

    // Rendering layer
    public int layer = 1;

    // Offset
    public float x = 0;
    public float y = 0;

    // Scale
    public float horizontalScale;
    public float verticalScale;
}
