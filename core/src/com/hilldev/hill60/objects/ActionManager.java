package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.InputManager;
import com.hilldev.hill60.components.Behaviour;
import com.hilldev.hill60.components.BehaviourComponent;

public class ActionManager extends GameObject {
    public ActionManager(IEngine engine) {
        super(engine);

        this.addComponent(new BehaviourComponent(new Behaviour() {

            InputManager manager;
            ActionManager parent;

            @Override
            public void create(BehaviourComponent parentComponent) {
                parent = (ActionManager)parentComponent.getParent();
            }

            @Override
            public void run() {

            }
        }));

    }


}
