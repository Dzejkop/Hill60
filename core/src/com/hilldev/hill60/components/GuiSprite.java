package com.hilldev.hill60.components;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class GuiSprite extends AComponent{
	public Sprite sprite;
	public int x,y;
	public GuiSprite(Sprite sprite,int x,int y)
	{
		this.sprite=sprite;
		this.x=x;
		this.y=y;
	}
}
