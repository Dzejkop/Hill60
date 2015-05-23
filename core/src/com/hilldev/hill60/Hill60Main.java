package com.hilldev.hill60;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.WorldPosition;
import com.hilldev.hill60.objects.*;
import com.hilldev.hill60.systems.*;
import com.sun.org.apache.xml.internal.security.utils.resolver.implementations.ResolverAnonymous;

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
    public ResourceManager resourceManager;

    public Hill60Main() {
        instance = this;

        systems = new ArrayList<>();
        gameObjects = new ArrayList<>();

        resourceManager = new ResourceManager();
    }

    // DEBUG
    public Player player;

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
        systems.add(new BombSystem(this));
        //systems.add(new FramerateSystem(this));

        start();
		
		// TESTING !!!!!!
        player = new Player();
		gameObjects.add(player);
        Random r = new Random();
        for(int x = 0; x < 10; x++) {
            for(int y = 0; y < 10; y++) {
                if(r.nextInt()%100 > 30) {
                    gameObjects.add(new Floor(x, y));
                } else {
                    gameObjects.add(new Wall(x, y));
                }
            }
        }
	}

    @Override
    public void start() {
        for(AEntitySystem s : systems) {
            s.start();
        }
    }

    @SuppressWarnings("unchecked")
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

        if(Gdx.input.isKeyPressed(Input.Keys.B)) {
            BoardPosition p = player.getComponent(BoardPosition.class);

            gameObjects.add(new Bomb(p.x, p.y));
        }
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

    @Override
    public void destroyObject(GameObject object) {
        gameObjects.remove(object);
    }

    @Override
    public void destroyObject(int id) {
        gameObjects.remove(getObject(id));
    }

    @Override
    public void createObject(GameObject object) {
        gameObjects.add(object);
    }
}
