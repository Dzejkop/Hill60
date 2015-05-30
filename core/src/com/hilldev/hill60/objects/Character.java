package com.hilldev.hill60.objects;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.Scripts.CharacterScript;
import com.hilldev.hill60.components.*;
public class Character extends GameObject {

    public CharacterScript characterScript;

    public Character(IEngine engine) {
        super(engine);

        characterScript = new CharacterScript();
        BehaviourComponent behaviourComponent = new BehaviourComponent(characterScript);

        this.addComponent(new WorldPosition(0, 20, false));					// The continuous position in game world
        this.addComponent(new BoardPosition(0, 0));							// Position on the board
        this.addComponent(new Collider(22, 29));
        this.addComponent(new Velocity(0, 0));
        this.addComponent(new SoundTrigger("footstepBrick.ogg", 10));
        this.addComponent(new Viewer());
        this.addComponent(behaviourComponent);
    }
}
