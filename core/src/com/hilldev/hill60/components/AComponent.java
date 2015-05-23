package com.hilldev.hill60.components;

import com.hilldev.hill60.objects.GameObject;

public abstract class AComponent {

    protected GameObject parent;

    public void setParent(GameObject parent) {
        this.parent = parent;
    }
    public GameObject getParent() {
        return parent;
    }
}
