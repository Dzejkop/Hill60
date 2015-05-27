package com.hilldev.hill60.objects;

import com.hilldev.hill60.GameScreen;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Explosion extends GameObject {
    public Explosion(IEngine engine, int x, int y, int power) {
        super(engine);

        this.tag = "Explosion";

        GameScreen main = ((GameScreen)engine);

        ResourceManager manager = main.resourceManager;

        this.addComponent(new BoardPosition(x, y));
        this.addComponent(new WorldPosition(0, 0));
        this.addComponent(new ExplosionComponent(power));
        this.addComponent(new SpriteRenderer(manager.getSprite("WhiteTile.png"), 0, 0, 3));
        this.addComponent(new Visibility());
        this.addComponent(new BehaviourComponent(new Behaviour() {

            public int life = 50;

            GameObject parentObject;

            @Override
            public void create(BehaviourComponent parentComponent) {
                parentObject = parentComponent.getParent();
            }

            @Override
            public void run() {
                life--;

                if(life <= 0) {
                    parentObject.engine.destroyObject(parentObject);
                }
            }
        }));
    }
}
