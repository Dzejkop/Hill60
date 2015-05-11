package com.hilldev.hill60.components;

// A base class for every script
public interface Behaviour {
    public void create(BehaviourComponent parentComponent);
    public void run();
}
