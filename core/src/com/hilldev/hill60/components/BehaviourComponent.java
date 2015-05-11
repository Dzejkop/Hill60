package com.hilldev.hill60.components;

import com.hilldev.hill60.GameObject;

import java.util.ArrayList;
import java.util.List;

// Contains Behaviour objects which are basically scripts
// Can contain multiple scripts
public class BehaviourComponent extends Component {

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
    }

    public void run() {
        for(Behaviour b : behaviours) {
            b.run();
        }
    }

}
