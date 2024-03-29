package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.scripts.CharacterScript;
import com.hilldev.hill60.components.*;

public class Character extends GameObject {

	public boolean isAlive;
    public CharacterScript characterScript;

    public Character(IEngine engine, int x, int y) {
    	
        super(engine);

        characterScript = new CharacterScript();
        BehaviourComponent behaviourComponent = new BehaviourComponent(characterScript);

        isAlive=true;

        this.addComponent(new WorldPosition(x*BoardPosition.TILE_SIZE, y* BoardPosition.TILE_SIZE, false));
        this.addComponent(new BoardPosition(x, y));
        this.addComponent(new Collider(22, 29));
        this.addComponent(new Velocity(0, 0));
        this.addComponent(new SoundTrigger("footstepBrick.ogg", 20));
        this.addComponent(new ViewerTag());
        this.addComponent(new Visibility());
        this.addComponent(behaviourComponent);
    }
}
