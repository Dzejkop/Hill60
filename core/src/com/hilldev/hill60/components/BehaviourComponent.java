package com.hilldev.hill60.components;

import com.hilldev.hill60.Scripts.Behaviour;
import com.hilldev.hill60.objects.GameObject;

import java.util.ArrayList;
import java.util.List;

// Contains Behaviour objects which are basically scripts
// Can contain multiple scripts
public class BehaviourComponent extends AComponent {

    private List<Behaviour> behaviours;

    public BehaviourComponent() {
        behaviours = new ArrayList<Behaviour>();
    }

    public BehaviourComponent(Behaviour b) {
        behaviours = new ArrayList<Behaviour>();
        add(b);
    }

    @Override
    public void setParent(GameObject parent) {
        super.setParent(parent);

        for(Behaviour b : behaviours) {
            b.create(this);
        }
    }

    public void add(Behaviour b) {
        behaviours.add(b);
        if(parent != null) b.create(this);
    }
    
    public void clear(){
    	behaviours.clear();
    }

    public void run() {
        for(Behaviour b : behaviours) {
            b.run();
        }
    }

    @SuppressWarnings("unchecked")
	public <T extends Behaviour> T get(Class<T> type) {
        for(Behaviour b : behaviours) {
            if(b.getClass() == type) return (T)b;
        }

        return null;
    }
}
