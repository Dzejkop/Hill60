package com.hilldev.hill60.systems;

import java.util.List;

import com.badlogic.gdx.audio.Music;
import com.hilldev.hill60.Hill60Main;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.SoundTrigger;
import com.hilldev.hill60.objects.GameObject;

public class SoundSystem extends AEntitySystem {

	public SoundSystem(IEngine engine) {
		super(engine);
	}

	@Override
	public void update() {
		List<GameObject> objList = engine.getObjectList();
		for (GameObject o : objList) {
			processObject(o);
		}
	}

	@Override
	protected boolean meetsConditions(GameObject obj) {
		return (obj.hasComponent(SoundTrigger.class));
	}

	@Override
	protected void processObject(GameObject obj) {
		if (meetsConditions(obj)) {
			System.out.println(obj.getComponent(SoundTrigger.class).sound);
			if(obj.getComponent(SoundTrigger.class).sound!=0){
			Music sound;
			sound = Hill60Main.getInstance().resourceManager.getSound(obj
					.getComponent(SoundTrigger.class).sound);
			if (!sound.isPlaying())
				sound.play();
			}
			obj.getComponent(SoundTrigger.class).sound=0;
		}
	}
}
