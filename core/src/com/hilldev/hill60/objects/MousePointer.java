package com.hilldev.hill60.objects;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.InputManager;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class MousePointer extends GameObject {
    public MousePointer(IEngine engine) {
        super(engine);

        ResourceManager manager = engine.getResourceManager();

        this.addComponent(new WorldPosition(0, 0, false));
        this.addComponent(new BoardPosition(0, 0));
        this.addComponent(new SpriteRenderer(manager.getSprite("Player"), 0, 0, 5));

        this.addComponent(new BehaviourComponent(new Behaviour() {

            MousePointer pointer;

            @Override
            public void create(BehaviourComponent parentComponent) {
                pointer = (MousePointer)parentComponent.getParent();
            }

            @Override
            public void run() {
                WorldPosition p = pointer.getComponent(WorldPosition.class);

                Vector2 v = pointer.engine.getInputManager().getMousePos();

                p.x = v.x;
                p.y = v.y;

            }
        }));
    }
}
