package com.hilldev.hill60.components;

// More for debug purposes than anything else
public class SimpleScript extends BehaviourComponent {

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
