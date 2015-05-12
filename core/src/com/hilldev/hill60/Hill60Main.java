package com.hilldev.hill60;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hilldev.hill60.components.*;
import com.hilldev.hill60.systems.*;

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
        systems.add(new BoardSystem(this));
        systems.add(new CameraSystem(this));
        systems.add(new PhysicsSystem(this));

        start();
		
		// TESTING !!!!!!
		GameObject smiley = new GameObject();
		Sprite sp = new Sprite(new Texture(new FileHandle("assets/Player.png")));
		smiley.addComponent(new SpriteRenderer(sp));                        // The image
		smiley.addComponent(new WorldPosition(0, 20, false));               // The continuous position in game world
		smiley.addComponent(new BoardPosition(0, 0));                       // Position on the board
        smiley.addComponent(new InputResponder());                          // Responds to input from InputSystem
        smiley.addComponent(new BehaviourComponent(new SimpleScript()));    // Simple movmeent script
        smiley.addComponent(new CameraTag());                               // Camera should follow this object
        smiley.addComponent(new Collider(100, 100));
        smiley.addComponent(new Velocity(0, 0));

        // TESTING P2 !!!!!
        for(int x = 0; x < 10; x++) {
            for(int y = 0; y < 10; y++) {
                GameObject wall = new GameObject();

                if(x%2 == 0)    {
                    wall.addComponent(new SpriteRenderer(new Sprite(new Texture(new FileHandle("assets/Floor.png")))));
                }
                else            {
                    wall.addComponent(new SpriteRenderer(new Sprite(new Texture(new FileHandle("assets/Wall.png")))));
                    wall.addComponent(new Collider(100, 100));
                }
                wall.addComponent(new BoardPosition(x, y));
                wall.addComponent(new WorldPosition(0, 0));

                gameObjects.add(wall);
            }
        }

		gameObjects.add(smiley);
	}

    @Override
    public void start() {
        for(IEntitySystem s : systems) {
            s.start();
        }
    }

    @Override
    public <T extends IEntitySystem> T getSystem(Class<T> type) {
        for(IEntitySystem s : systems) {
            if(s.getClass() == type) return (T)s;
        }
        return null;
    }

    @Override
	public void render () {
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
