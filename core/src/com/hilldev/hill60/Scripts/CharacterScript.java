package com.hilldev.hill60.Scripts;

import com.hilldev.hill60.components.*;
import com.hilldev.hill60.objects.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Ta klasa ogarnia ruch postaci, wyzwalanie dźwięku
 */

public class CharacterScript implements Behaviour {

    // Hardcoded values
    public static final int RUN_ANIMATION_SPEED = 3;    // How fast animation progresses while running
    public static final int SNEAK_ANIMATION_SPEED = 20; // How fast animation progresses while sneaking
    public static final float RUN_STEP_LOUDNESS = 10;   // How loud each step is
    public static final int RUN_STEP_INTERVAL = 15;     // How often the step sound is played
    public static final float RUN_SPEED = 4;            // How fast the character runs
    public static final float SNEAK_SPEED = 2;          // How fast the character sneaks

    // Ease of access
    public WorldPosition position;
    public Velocity velocity;
    public SpriteRenderer spriteRenderer;

    // Movement variables
    public boolean inSneakMode = false;
    public boolean goingLeft = false;
    public boolean goingRight = false;
    public boolean goingUp = false;
    public boolean goingDown = false;

    public float runVelocity;
    public float sneakVelocity;

    private BehaviourComponent parentComponent;
    private Character parent;

    @Override
    public void create(BehaviourComponent parentComponent) {
        this.parentComponent = parentComponent;
        this.parent = (Character) parentComponent.getParent();
    }

    @Override
    public void run() {

    }

    /**
     * Nie wiem jeszcze jak rozwiązać ten ekwipunek, ale raczej nie do końca w ten sposób
     *
     */

    // Items
    private List<Item> items = new ArrayList<>();
    public List<Item> getItems() {
        return null;
    }
    public void addItem(String itemName) {

    }
    public void delItem(String itemName) {

    }
    public void selItem(String itemName) {

    }

    public static class Item {

    }
}
