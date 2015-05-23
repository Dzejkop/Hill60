package com.hilldev.hill60.components;

// An explosion node on a single tile.
public class ExplosionComponent extends AComponent {

    public ExplosionComponent(int explosivePotential) {
        this.explosivePotential = explosivePotential;
    }

    // Describes the strength of this explosion
    public int explosivePotential = 0;

    // Describes the direction in which the explosion is happening
    public int xDirection = 1;
    public int yDirection = 0;
}
