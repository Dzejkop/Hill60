package com.hilldev.hill60.systems;

import java.util.List;

import com.badlogic.gdx.audio.Sound;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.SoundTrigger;
import com.hilldev.hill60.components.WorldPosition;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.objects.Player;
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

            if(soundTrigger.triggered) {
                if (!soundTrigger.sound.isEmpty()) {
                	Player player=(Player) engine.findObject("Player");
                	if (player!=null){
                	int soundID=-1;
                    Sound sound;
                    sound = engine.getResourceManager().getSound(soundTrigger.sound);
                    WorldPosition pos = player.getComponent(WorldPosition.class);
                    double distance=Math.sqrt((p.x-pos.x)*(p.x-pos.x)+(p.y-pos.y)*(p.y-pos.y));
                    if (distance<1000)
                    if (soundTrigger.soundID <=0)
                        soundID = (int)sound.play((float)(1-distance/1000));
                    engine.createObject(new SoundWave(engine, soundTrigger.volume, p.x, p.y,soundID, sound ,player));}
                }

                soundTrigger.triggered = false;
            }
		}
	}
}
