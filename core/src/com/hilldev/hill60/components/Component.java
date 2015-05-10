package com.hilldev.hill60.components;

import com.hilldev.hill60.GameObject;

public abstract class Component {
    GameObject parent;

    public void setParent(GameObject parent) {
        this.parent = parent;
    }
}
