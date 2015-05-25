package com.hilldev.hill60.components;

public class Visibility extends AComponent {

    /*public Visibility(boolean isHigh) {
        this.isHigh = isHigh;
    }*/

    public static enum Type {
        Visible,
        HalfVisible,
        Invisible
    }

    public boolean isHigh = false;

    public Type isVisible = Type.Invisible;

}
