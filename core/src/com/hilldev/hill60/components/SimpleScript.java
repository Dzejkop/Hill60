package com.hilldev.hill60.components;

import com.hilldev.hill60.objects.GameObject;

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
        WorldPosition pos = parent.getComponent(WorldPosition.class);
        SoundTrigger s = parent.getComponent(SoundTrigger.class);
        v.x = 0;
        v.y = 0;

        float veloc = 4;

        if(i.upArrow){
        	v.y = veloc;
        	s.setSound(2, 25, 80,pos.x,pos.y+v.y);
        }
        if(i.downArrow){
        	v.y = -veloc;
        	s.setSound(2, 25, 80,pos.x,pos.y+v.y);
        }
        if(i.leftArrow){
        	v.x = -veloc;
        	s.setSound(2, 25, 80,pos.x+v.x,pos.y);
        }
        if(i.rightArrow){
        	v.x = veloc;
        	s.setSound(2, 25, 80,pos.x+v.x,pos.y);
        }
    }
}
