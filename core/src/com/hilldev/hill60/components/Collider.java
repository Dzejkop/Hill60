package com.hilldev.hill60.components;

import com.hilldev.hill60.GameObject;

public class Collider extends Component {

    public Collider(float w, float h) {
        width = w;
        height = h;
    }

    float width;
    float height;

    @Override
    public void setParent(GameObject parent) {
        super.setParent(parent);
    }
}
