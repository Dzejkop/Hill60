package com.hilldev.hill60;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.objects.*;
import com.hilldev.hill60.systems.*;

public class GameScreen implements Screen, IEngine {
	
    // Singleton
    static GameScreen instance;

    // Game objects
    private List<GameObject> gameObjects;

    // Destruction and creation queue
    private List<GameObject> destructionQueue;
    private List<GameObject> creationQueue;

    // Systems
    List<AEntitySystem> systems;

    // Resource manager
    public ResourceManager resourceManager;

    // Input manager
    public InputManager inputManager;

    public GameScreen() {
        instance = this;

        systems = new ArrayList<>();
        gameObjects = new ArrayList<>();
        destructionQueue = new ArrayList<>();
        creationQueue = new ArrayList<>();

        resourceManager = new ResourceManager();
        inputManager = new InputManager(this);
        
        create();
    }

    // DEBUG
    public Player player;

	public void create() {

        // Set size
        Gdx.graphics.setDisplayMode(RenderingSystem.SCREEN_WIDTH, RenderingSystem.SCREEN_HEIGHT, false);

        // Load assets
        resourceManager.loadTextures();
        resourceManager.loadSounds();

        // Initialize all the systems
		systems.add(new RenderingSystem(this));
        systems.add(new BehaviourSystem(this));
        systems.add(new PhysicsSystem(this));
        systems.add(new BoardSystem(this));
        systems.add(new CameraSystem(this));
        systems.add(new BombSystem(this));
        systems.add(new ExplosionSystem(this));
        systems.add(new SoundSystem(this));
        systems.add(new VisibilitySystem(this));
        systems.add(new AnimationSystem(this));

        start();
		
		// TESTING !!!!!!
        player = new Player(this);
		gameObjects.add(player);
        gameObjects.add(new MousePointer(this));
        Random r = new Random();
        for(int x = 0; x < BoardSystem.BOARD_WIDTH; x++) {
            for(int y = 0; y < BoardSystem.BOARD_HEIGHT; y++) {
                if(r.nextInt()%100 > 30) {
                    gameObjects.add(new Floor(this, x, y));
                } else {
                    gameObjects.add(new Floor(this, x, y));
                    gameObjects.add(new Wall(this, x, y));
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
    public ResourceManager getResourceManager() {
		return resourceManager;
    }

    @Override
    public InputManager getInputManager() {
        return inputManager;
    }

    @Override
	public void render(float delta) {
		update();

        if(Gdx.input.isKeyJustPressed(Input.Keys.B)) {
            BoardPosition p = player.getComponent(BoardPosition.class);
            gameObjects.add(new Bomb(this, p.x, p.y));
        }
	}

    @Override
    public void dispose() {
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

        //Debug.log("");
		// Update entity systems
		for(AEntitySystem e : systems) {

            //long start = System.nanoTime();

			e.update();

            //long end = System.nanoTime();

            //Debug.log(e.getClass().getSimpleName() + " execution time: " + (end-start) );
		}

        // Destroy objects from the queue
        for(GameObject o : destructionQueue) {
            gameObjects.remove(o);
        }

        destructionQueue.clear();

        // Create objects from the queue
        for(GameObject o : creationQueue) {
            gameObjects.add(o);
        }

        creationQueue.clear();
	}

    @Override
    public void destroyObject(GameObject object) {
        destructionQueue.add(object);
        getSystem(BoardSystem.class).destroyObject(object); // Lepsze rozwiązanie :)
    }

    @Override
    public void destroyObject(int id) {
        destroyObject(getObject(id));
    }

    @Override
    public void createObject(GameObject object) {
        creationQueue.add(object);
    }

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
