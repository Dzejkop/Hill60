package com.hilldev.hill60;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Game;
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
    List<AEntitySystem> systems;

    // Resource manager
    ResourceManager resourceManager;

    // TEMPORAL
    InputSystem inputSystem;

    public Hill60Main() {
        instance = this;

        systems = new ArrayList<AEntitySystem>();
        gameObjects = new ArrayList<GameObject>();

        resourceManager = new ResourceManager();
    }

	@Override
	public void create () {

        // Load assets
        resourceManager.loadTextures();

        // Initialize all the systems
		systems.add(new RenderingSystem(this));
        systems.add(new BehaviourSystem(this));
        systems.add(new PhysicsSystem(this));
        systems.add(new InputSystem(this));
        systems.add(new BoardSystem(this));
        systems.add(new CameraSystem(this));
        systems.add(new ForcingFramerateSystem(this));

        start();
		
		// TESTING !!!!!!
		GameObject smiley = new GameObject();
		Sprite sp = resourceManager.getSprite("Player.png");                //image size is 100!!!
		smiley.addComponent(new SpriteRenderer(sp));                          // The image
		smiley.addComponent(new WorldPosition(0, 20, false));               // The continuous position in game world
		smiley.addComponent(new BoardPosition(0, 0));                       // Position on the board
        smiley.addComponent(new Layer(2));									// Rendering layer (0 - floor, 1 - bombs, 2 - player and bots, 3 - walls)
        smiley.addComponent(new InputResponder());                          // Responds to input from InputSystem
        smiley.addComponent(new BehaviourComponent(new SimpleScript()));    // Simple movmeent script
        smiley.addComponent(new CameraTag());                               // Camera should follow this object
        smiley.addComponent(new Collider(100, 100));
        smiley.addComponent(new Velocity(0, 0));
        smiley.addComponent(new ObjectID("player"));

        // TESTING P2 !!!!!
        for(int x = 0; x < 10; x++) {
            for(int y = 0; y < 10; y++) {
                GameObject wall = new GameObject();

                if(y%2 == 0)    {
                    wall.addComponent(new SpriteRenderer(new Sprite(new Texture(new FileHandle("assets/Floor.png")))));
                    wall.addComponent(new ObjectID("floor"));
                    wall.addComponent(new Layer(0));
                }
                else {
                    wall.addComponent(new SpriteRenderer(new Sprite(new Texture(new FileHandle("assets/Wall.png")))));
                    wall.addComponent(new ObjectID("wall"));
                    wall.addComponent(new Collider(100, 100));
                    wall.addComponent(new Layer(3));
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
        for(AEntitySystem s : systems) {
            s.start();
        }
    }

    @Override
    public <T extends AEntitySystem> T getSystem(Class<T> type) {
        for(AEntitySystem s : systems) {
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

        resourceManager.dispose();
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
		for(AEntitySystem e : systems) {
			e.update();
		}
	}
}
