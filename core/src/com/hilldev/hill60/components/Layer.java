package com.hilldev.hill60.components;

import com.hilldev.hill60.objects.GameObject;

public class Layer extends AComponent {
	
    public Layer(int l) {
        layer = l;
    }

    public int layer;

    @Override
    public void setParent(GameObject parent) {
        super.setParent(parent);
    }
}
