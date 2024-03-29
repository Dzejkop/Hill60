package com.hilldev.hill60.scripts;

import com.hilldev.hill60.components.BehaviourComponent;

// A base class for every script
public interface Behaviour {
    public void create(BehaviourComponent parentComponent);
    public void run();
}
