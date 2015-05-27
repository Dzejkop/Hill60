package com.hilldev.hill60.components;

import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.objects.Player;

// More for debug purposes than anything else
public class PlayerScript implements Behaviour {

    Player parent;
    BehaviourComponent parentComponent;

    @Override
    public void create(BehaviourComponent parentComponent) {
        this.parentComponent = parentComponent;
        parent = (Player)(parentComponent.parent);
    }

    // TEMPORAL SOLUTION
    int sinceLastStep = 20;

    @Override
    public void run() {
        Velocity v = parent.getComponent(Velocity.class);
        InputResponder i = parent.getComponent(InputResponder.class);
        //WorldPosition pos = parent.getComponent(WorldPosition.class);
        SoundTrigger s = parent.getComponent(SoundTrigger.class);
        v.x = 0;
        v.y = 0;

        float veloc = 5;

        float xv = 0;
        float yv = 0;

        if(i.upArrow){
            yv = veloc;
        }
        if(i.downArrow){
            yv = -veloc;
        }
        if(i.leftArrow){
            xv = -veloc;
        }
        if(i.rightArrow){
            xv = veloc;
        }

        if(Math.abs(xv) + Math.abs(yv) > 0) {
            sinceLastStep--;
            if(sinceLastStep < 0) {
                sinceLastStep = 20;
                s.triggered = true;
            }
        }

        if(xv > 0) parent.animation.isActive = true;
        else parent.animation.isActive = false;

        v.x = xv;
        v.y = yv;
    }
}
