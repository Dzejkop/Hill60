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
			SoundTrigger soundTrigg = obj.getComponent(SoundTrigger.class);
			if (soundTrigg.sound != 0) {
				Music sound;
				sound = Hill60Main.getInstance().resourceManager
						.getSound(soundTrigg.sound);
				if (!sound.isPlaying())
					sound.play();
				if (soundTrigg.animation == 0) {
					System.out.println(soundTrigg.animation);
					soundTrigg.animation = soundTrigg.animationFrames;
					soundTrigg.sprite.setSize(soundTrigg.size, soundTrigg.size);
					soundTrigg.sprite.setPosition(soundTrigg.x
							- soundTrigg.size / 2, soundTrigg.y
							- soundTrigg.size / 2);
					soundTrigg.draw = true;
				}
			}
			soundTrigg.sound = 0;
			if (soundTrigg.animation > 0) {
				soundTrigg.animation--;
				soundTrigg.size *= soundTrigg.SCALE;
				soundTrigg.sprite.setSize(soundTrigg.size, soundTrigg.size);
				soundTrigg.sprite.setPosition(soundTrigg.x - soundTrigg.size
						/ 2, soundTrigg.y - soundTrigg.size / 2);
			} else {
				soundTrigg.draw = false;
			}
		}
	}
}
