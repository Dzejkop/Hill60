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
        Velocity v = parent.getComponent(Velocity.class);
        InputResponder i = parent.getComponent(InputResponder.class);

        v.x = 0;
        v.y = 0;

        if(i.upArrow) v.y = 2;
        if(i.downArrow) v.y = -2;
        if(i.leftArrow) v.x = -2;
        if(i.rightArrow) v.x = 2;
    }
}
