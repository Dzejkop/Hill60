package com.hilldev.hill60.components;

import com.hilldev.hill60.objects.GameObject;

public class Collider extends AComponent {

    public Collider(float x, float y, float w, float h) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
    }

    public Collider(float w, float h) {
        width = w;
        height = h;
    }

    public float x = 0;     // Horizontal offset
    public float y = 0;     // Vertical offset
    public float width;     // Width
    public float height;    // Height

    @Override
    public void setParent(GameObject parent) {
        super.setParent(parent);
    }
}
