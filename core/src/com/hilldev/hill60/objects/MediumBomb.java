package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.Scripts.Behaviour;
import com.hilldev.hill60.Scripts.CharacterScript;
import com.hilldev.hill60.components.*;

public class MediumBomb extends GameObject {

    public MediumBomb(IEngine engine, int x, int y) {
    	
        super(engine);

        // Set a tag
        this.tag = "Bomb";
        
        // Get the resource manager
        ResourceManager manager = engine.getResourceManager();

        this.addComponent(new SpriteRenderer(manager.getSprite(CharacterScript.ITEM_LIST[2]), 0, 0, 0.6f, 2));
        this.addComponent(new WorldPosition(0, 0));
        this.addComponent(new BoardPosition(x, y));
        this.addComponent(new ExplosionSpawn(100, CharacterScript.ITEM_POWER_LIST[2]));
        this.addComponent(new Visibility());
        this.addComponent(new SoundTrigger(CharacterScript.ITEM_SOUND_LIST[2], 100));

        this.addComponent(new BehaviourComponent(new Behaviour() {

            MediumBomb parentObject;
            ExplosionSpawn spawn;

            @Override
            public void create(BehaviourComponent parentComponent) {
                parentObject = (MediumBomb)(parentComponent.getParent());
                spawn = parentObject.getComponent(ExplosionSpawn.class);
            }

            @Override
            public void run() {
                spawn.countdown--;
                if(spawn.countdown <= 0) {
                    parentObject.getComponent(SoundTrigger.class).triggered = true;
                }
            }
        }));
    }
}
