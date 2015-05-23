package com.hilldev.hill60.components;

public class ExplosionSpawn extends AComponent {

    public ExplosionSpawn(int c, int power) {
        this.countdown = c;
        this.bombPower = power;
    }

    public int bombPower;
    public int countdown;
}
