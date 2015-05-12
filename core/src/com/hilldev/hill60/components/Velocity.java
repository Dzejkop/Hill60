package com.hilldev.hill60.components;

public class Velocity extends Component {

    public Velocity(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x;
    public float y;

    public float length() {
        return (float)Math.sqrt((double)(x*x) + (double)(y*y));
    }

    public boolean isZero() {
        return x == 0 && y == 0;
    }

}
