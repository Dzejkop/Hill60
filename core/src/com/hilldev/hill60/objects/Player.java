package com.hilldev.hill60.objects;

import com.hilldev.hill60.Hill60Main;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;

public class Player extends GameObject {
    public Player() {
        super();

        // Set a tag
        this.tag = "Player";

        // Self initialize things

        // Connect to main
        Hill60Main main = Hill60Main.getInstance();

        // Get the resource manager
        ResourceManager manager = main.resourceManager;

        this.addComponent(new SpriteRenderer(manager.getSprite("Character.png")));
        this.addComponent(new WorldPosition(0, 20, false));					// The continuous position in game world
        this.addComponent(new BoardPosition(0, 0));							// Position on the board
        this.addComponent(new Layer(2));									// Rendering layer (0 - floor, 1 - bombs, 2 - player and bots, 3 - walls)
        this.addComponent(new InputResponder());							// Responds to input from InputSystem
        this.addComponent(new BehaviourComponent(new SimpleScript()));		// Simple movmeent script
        this.addComponent(new CameraTag());									// Camera should follow this object
        this.addComponent(new Collider(22, 29));
        this.addComponent(new Velocity(0, 0));
    }

    @Override
    public void receiveMessage(String message, GameObject sender) {
        super.receiveMessage(message, sender);
    }
}
