package com.hilldev.hill60.components;

import com.hilldev.hill60.GameObject;

// More for debug purposes than anything else
public class SimpleScript implements Behaviour {

    GameObject parent;
    BehaviourComponent parentComponent;

    @Override
    public void create(BehaviourComponent parentComponent) {
        this.parentComponent = parentComponent;
        parent = parentComponent.parent;
    }

    @Override
    public void run() {
        WorldPosition w = parent.getComponent(WorldPosition.class);
        InputResponder i = parent.getComponent(InputResponder.class);

        if(i.upArrow) w.y += 2;
        if(i.downArrow) w.y -= 2;
        if(i.leftArrow) w.x -= 2;
        if(i.rightArrow) w.x += 2;
    }
}
