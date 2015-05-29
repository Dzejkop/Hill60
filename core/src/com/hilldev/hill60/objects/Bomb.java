package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Bomb extends GameObject {

    public Bomb(IEngine engine, int x, int y) {
        super(engine);

        // Set a tag
        this.tag = "Bomb";
        
        // Get the resource manager
        ResourceManager manager = engine.getResourceManager();

        this.addComponent(new SpriteRenderer(manager.getSprite("Player"), 0, 0, 4));
        this.addComponent(new WorldPosition(0, 0));
        this.addComponent(new BoardPosition(x, y));
        this.addComponent(new ExplosionSpawn(100, 5));
        this.addComponent(new Visibility());
        this.addComponent(new SoundTrigger("Explosion.wav"));

        this.addComponent(new BehaviourComponent(new Behaviour() {

            Bomb parentObject;
            ExplosionSpawn spawn;

            @Override
            public void create(BehaviourComponent parentComponent) {
                parentObject = (Bomb)(parentComponent.getParent());
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
