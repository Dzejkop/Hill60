package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.Scripts.CharacterScript;
import com.hilldev.hill60.components.*;
import com.hilldev.hill60.systems.BoardSystem;

public class Character extends GameObject {

    public CharacterScript characterScript;

    public Character(IEngine engine, int x, int y) {
    	
        super(engine);

        characterScript = new CharacterScript();
        BehaviourComponent behaviourComponent = new BehaviourComponent(characterScript);

        this.addComponent(new WorldPosition(x*BoardPosition.TILE_SIZE, y* BoardPosition.TILE_SIZE, false));					// The continuous position in game world
        this.addComponent(new BoardPosition(x, y));							// Position on the board
        this.addComponent(new Collider(22, 29));
        this.addComponent(new Velocity(0, 0));
        this.addComponent(new SoundTrigger("footstepBrick.ogg", 10));
        this.addComponent(new Viewer());
        this.addComponent(behaviourComponent);
    }
}
