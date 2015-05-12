package com.hilldev.hill60.components;

import com.hilldev.hill60.GameObject;

public abstract class AComponent {
    protected GameObject parent;

    public void setParent(GameObject parent) {
        this.parent = parent;
    }
}
