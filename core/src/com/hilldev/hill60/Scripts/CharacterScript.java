package com.hilldev.hill60.Scripts;

import com.hilldev.hill60.components.*;
import com.hilldev.hill60.objects.Character;

import java.util.ArrayList;
import java.util.List;

/**
 * Ta klasa ogarnia ruch postaci, wyzwalanie dźwięku, ekwipunek
 */

public class CharacterScript implements Behaviour {

	// Hardcoded values
	public static final float RUN_STEP_VOLUME = 10; // How loud each step is
	public static final int RUN_STEP_INTERVAL = 15; // How often the step sound
													// is played
	public static final float RUN_SPEED = 4; // How fast the character runs
	public static final float SNEAK_SPEED = 2; // How fast the character sneaks

	// Ease of access
	public Velocity velocity;

	// Movement variables
	public boolean inSneakMode = false;
	public boolean goingLeft = false;
	public boolean goingRight = false;
	public boolean goingUp = false;
	public boolean goingDown = false;
	String lastStep="";

    private int sinceLastStep = RUN_STEP_INTERVAL;

	public float runVelocity = RUN_SPEED;
	public float sneakVelocity = SNEAK_SPEED;

	// Items variables
	public List<Item> items = new ArrayList<>();
    public final static String[] ITEM_LIST = {"Shovel", "SmallBomb", "MediumBomb", "BigBomb"};
    public final static int[] ITEM_POWER_LIST = {3, 2, 3, 5}; 
    public final static String[] ITEM_SOUND_LIST = {"Explosion.wav","Explosion.wav","Explosion.wav","Explosion.wav"};
	private BehaviourComponent parentComponent;
	private Character parent;

	@Override
	public void create(BehaviourComponent parentComponent) {
		this.parentComponent = parentComponent;
		this.parent = (Character) parentComponent.getParent();

		// Connect to components
		velocity = parent.getComponent(Velocity.class);

        // Set step loudness
        parent.getComponent(SoundTrigger.class).volume = RUN_STEP_VOLUME;

		// Create the item list
		items = new ArrayList<>();
		items.add(Item.getShovel());
		items.add(Item.getSmallBomb());
		items.add(Item.getMediumBomb());
		items.add(Item.getBigBomb());
	}

	@Override
	public void run() {
		setLastStep();
		float veloc = runVelocity;
		if (inSneakMode)
			veloc = sneakVelocity;

		float xv = 0;
		float yv = 0;

		if (goingUp)
			yv = veloc;
		else if (goingDown)
			yv = -veloc;

		if (goingRight)
			xv = veloc;
		else if (goingLeft)
			xv = -veloc;

        if(!inSneakMode && (xv !=0 || yv != 0)) {
            sinceLastStep--;
            if(sinceLastStep <= 0) {
                sinceLastStep = RUN_STEP_INTERVAL;
                parent.getComponent(SoundTrigger.class).triggered = true;
            }
        }

		velocity.x = xv;
		velocity.y = yv;
		for(Item item:items){
			item.update();
		}
	}

    public Item getItem(String itemName) {
        for(Item i : items) {
            if(i.name.equals(itemName)) return i;
        }
        return null;
    }
    public String getLastStep(){
    	return setLastStep();
    }
    
    private String setLastStep(){
    	if(goingUp){
    		lastStep="up";
    		return "up";
    	}
    	if(goingDown){
    		lastStep="down";
    		return "down";
    	}
    	if(goingLeft){
    		lastStep="left";
    		return "left";
    	}
    	if(goingRight){
    		lastStep="right";
    		return "right";
    	}
    	return lastStep;
    }
    
    
	public static String[] getItemList() {
		return ITEM_LIST;
	}


}
