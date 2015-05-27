package com.hilldev.hill60.systems;

import java.util.List;

import com.badlogic.gdx.audio.Music;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.SoundTrigger;
import com.hilldev.hill60.components.WorldPosition;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.objects.SoundWave;

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

            WorldPosition p = obj.getComponent(WorldPosition.class);

			SoundTrigger soundTrigger = obj.getComponent(SoundTrigger.class);

            if(soundTrigger.triggered == true) {
                if (soundTrigger.sound.isEmpty() == false) {
                    Music sound;
                    sound = engine.getResourceManager().getSound(soundTrigger.sound);

                    if (sound.isPlaying() == false)
                        sound.play();

                    engine.createObject(new SoundWave(engine, p.x, p.y));
                }

                soundTrigger.triggered = false;
            }
		}
	}
}
