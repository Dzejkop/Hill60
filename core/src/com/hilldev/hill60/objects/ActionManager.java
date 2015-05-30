package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.InputManager;
import com.hilldev.hill60.components.Behaviour;
import com.hilldev.hill60.components.BehaviourComponent;

/**
 * Responds to input from keyboard and mouse
 * Correlates said input to actions in game world.
 */
public class ActionManager extends GameObject {
    public ActionManager(IEngine engine) {
        super(engine);

        // GUISystem guiSystem;

        this.addComponent(new BehaviourComponent(new Behaviour() {

            IEngine engine;
            InputManager manager;
            ActionManager parent;

            String currentItem = "Shovel";

            @Override
            public void create(BehaviourComponent parentComponent) {
                parent = (ActionManager)parentComponent.getParent();
                engine = parent.engine;
                manager = engine.getInputManager();
            }

            @Override
            public void run() {

            }
        }));

    }


}
