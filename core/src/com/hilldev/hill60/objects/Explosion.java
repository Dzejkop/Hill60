package com.hilldev.hill60.objects;

import com.hilldev.hill60.Hill60Main;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Explosion extends GameObject {
    public Explosion(int x, int y, int power) {
        super();

        this.tag = "Explosion";

        Hill60Main main = Hill60Main.getInstance();

        ResourceManager manager = main.resourceManager;

        this.addComponent(new BoardPosition(x, y));
        this.addComponent(new WorldPosition(0, 0));
        this.addComponent(new ExplosionComponent(power));
        this.addComponent(new SpriteRenderer(manager.getSprite("WhiteTile.png")));
        this.addComponent(new Layer(5));
        /*this.addComponent(new BehaviourComponent(new Behaviour() {

            public int life = 100;

            GameObject parentObject;

            @Override
            public void create(BehaviourComponent parentComponent) {
                parentObject = parentComponent.getParent();
            }

            @Override
            public void run() {
                life--;

                if(life <= 0) {
                    Hill60Main.getInstance().destroyObject(parentObject);
                }
            }
        }));*/
    }
}
