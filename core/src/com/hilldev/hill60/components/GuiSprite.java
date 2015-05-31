package com.hilldev.hill60.components;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class GuiSprite extends AComponent{
	public Sprite sprite;
	public int x,y,layer;
	public float alpha;
	public GuiSprite(Sprite sprite,int x,int y,int layer)
	{
		alpha=1;
		this.sprite=sprite;
		this.x=x;
		this.y=y;
		this.layer=layer;
	}
}
