package com.hilldev.hill60.components;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hilldev.hill60.Hill60Main;

public class SoundTrigger extends AComponent {
	
	public final float SCALE=1.025f;
	public int animationFrames=0,animation=0,sound=0;
	public float size=0,x=0,y=0;
	public Sprite sprite=Hill60Main.getInstance().resourceManager.getSprite("Ring.png");
	public boolean draw=false;
	

	public void setSound(int sound, int animationFrames,int maxCircleSize,float x,float y){
		this.sound=sound;
		if (animation == 0){
		this.animationFrames=animationFrames;
		float temp=1;
		for(int i=0;i<animationFrames;i++){
			temp*=SCALE;
		}
		this.size=maxCircleSize/temp;
		this.x=x;
		this.y=y;}
	}
}
