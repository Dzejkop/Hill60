package com.hilldev.hill60.objects;

import com.hilldev.hill60.components.SpriteRenderer;
import com.hilldev.hill60.components.Velocity;
import com.hilldev.hill60.components.WorldPosition;

public abstract class ACharacter {

    // Ease of access
    public WorldPosition position;
    public Velocity velocity;
    public SpriteRenderer spriteRenderer;
    
    // Direction and velocity
    public boolean inSneakMode;
    public boolean goingLeft;
    public boolean goingRight;
    public boolean goingUp;
    public boolean goingDown;
    public float stdVelocity;
    public float sneakModeVelocity;
    
    // Animation
    public static final int RUN_ANIMATION_SPEED = 3;
    public static final int SNEAK_ANIMATION_SPEED = 20;
    public static final float RUN_STEP_LOUDNESS = 10;
    public static final int RUN_STEP_INTERVAL = 15;
    public static final float RUN_SPEED = 4;
    public static final float SNEAK_SPEED = 2;
    
    // Items
    private List<Item> items = new ArrayList<>();
    public List<Item> getItems();
    public void addItem(Item);
    public void delItem(Item);
    public void selItem(Item);
}
