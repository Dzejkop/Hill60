package com.hilldev.hill60;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hilldev.hill60.components.*;
import com.hilldev.hill60.systems.BehaviourSystem;
import com.hilldev.hill60.systems.IEntitySystem;
import com.hilldev.hill60.systems.InputSystem;
import com.hilldev.hill60.systems.RenderingSystem;

public class Hill60Main extends Game implements IEngine {
	
    // Singleton
    static Hill60Main instance;
    public static Hill60Main getInstance() {
        return instance;
    }

    // Game objects
    List<GameObject> gameObjects;
    
    // Systems
    List<IEntitySystem> systems;

    // TEMPORAL
    InputSystem inputSystem;

    public Hill60Main() {
        instance = this;

        systems = new ArrayList<IEntitySystem>();
        gameObjects = new ArrayList<GameObject>();
    }

	@Override
	public void create () {
		systems.add(new RenderingSystem(this));
        systems.add(new BehaviourSystem(this));
        systems.add(new InputSystem(this));
		
		// TESTING !!!!!!
		GameObject logo = new GameObject();
		Sprite sp = new Sprite(new Texture(new FileHandle("assets/Player.png")));
		logo.addComponent(new SpriteRenderer(sp));      // The image
		logo.addComponent(new WorldPosition(0, 20));    // The continuous position in game world
        logo.addComponent(new InputResponder());        // Responds to input from InputSystem
        logo.addComponent(new SimpleScript());          // Simple movement script
		
		gameObjects.add(logo);
	}

	@Override
	public void render () {
        //inputSystem

		update();
		
	}

    @Override
    public void dispose() {
        super.dispose();
    }

	@Override
	public List<GameObject> getObjectList() {
		return gameObjects;
	}

	@Override
	public GameObject getObject(int id) {
		for(GameObject obj : gameObjects) {
			if(obj.getID() == id) return obj;
		}
		return null;
	}
	
	@Override
	public void update() {
		// Update entity systems
		for(IEntitySystem e : systems) {
			e.update();
		}
	}
	
	
}
