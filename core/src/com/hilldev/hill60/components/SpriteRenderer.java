package com.hilldev.hill60.components;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteRenderer extends AComponent {
	
	public SpriteRenderer(Sprite sprite) {
		this.sprite = sprite;
        color = Color.WHITE;
	}
	
	public SpriteRenderer(Sprite sprite,float x, float y) {
		this.sprite = sprite;
		this.x = x;
		this.y = y;
        color = Color.WHITE;
	}

    public SpriteRenderer(Sprite sprite, float x, float y, int layer) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.layer = layer;
        color = Color.WHITE;
    }

    public void setScale(float scale) {
        this.horizontalScale = scale;
        this.verticalScale = scale;
    }

    public void setColor(float r, float g, float b, float a) {
        color = new Color(r, g, b, a);
    }

   public void setAlpha(float a) {
        color = new Color(color.r, color.g, color.b, a);
    }

    public void setColor(float r, float g, float b) {
        setColor(r, g, b, 1);
    }

    public Sprite sprite;

    // Rendering layer
    public int layer = 1;

    // Offset
    public float x = 0;
    public float y = 0;

    // Scale
    public float horizontalScale = 1;
    public float verticalScale = 1;

    // Color (tint)
    public Color color;
}
