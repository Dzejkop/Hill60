package com.hilldev.hill60.objects;

import com.hilldev.hill60.Hill60Main;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Player extends GameObject {
    public Player(IEngine engine) {
        super(engine);

        // Set a tag
        this.tag = "Player";

        // Self initialize things

        // Connect to main
        Hill60Main main = ((Hill60Main)engine);

        // Get the resource manager
        ResourceManager manager = main.resourceManager;

        this.addComponent(new SpriteRenderer(manager.getSprite("Character.png"), 0, 0, 2));
        this.addComponent(new WorldPosition(0, 20, false));					// The continuous position in game world
        this.addComponent(new BoardPosition(0, 0));							// Position on the board
        this.addComponent(new InputResponder());							// Responds to input from InputSystem
        this.addComponent(new BehaviourComponent(new SimpleScript()));		// Simple movmeent script
        this.addComponent(new CameraTag());									// Camera should follow this object
        this.addComponent(new Collider(22, 29));
        this.addComponent(new Velocity(0, 0));
        this.addComponent(new SoundTrigger("footstepBrick.ogg"));
        this.addComponent(new Viewer());

        String[] frames = {"Character.png", "Player.png"};
        this.addComponent(new Animation(frames));
    }

    @Override
    public void receiveMessage(String message, GameObject sender) {
        super.receiveMessage(message, sender);
    }
}
