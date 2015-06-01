package com.hilldev.hill60;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.hilldev.hill60.objects.*;
import com.hilldev.hill60.objects.HUD.HudManager;
import com.hilldev.hill60.objects.Walls.*;
import com.hilldev.hill60.systems.*;
//import com.hilldev.hill60.Debug;

public class GameScreen implements Screen, IEngine {
	
    // Singleton
    static GameScreen instance;
    
    // 
    Hill60Main game;
    
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

    // DEBUG
    public Player player;

    public GameScreen(Hill60Main gam) {
        instance = this;
        game=gam;
        systems = new ArrayList<>();
        gameObjects = new ArrayList<>();
        destructionQueue = new ArrayList<>();
        creationQueue = new ArrayList<>();

        resourceManager = new ResourceManager();
        inputManager = new InputManager(this);
        
        create();
    }

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
        systems.add(new RenderGUISystem(this));
        systems.add(new CheckingGameStateSystem(this));
        
        start();
		
		createTheBoard();
		spawnCharacters();
	}

    @Override
    public void start() {
        for(AEntitySystem s : systems) {
            s.start();
        }
    }
    
	private void createTheBoard() {
        
        // Creating the board map array
        char[][] boardMap = new char[BoardSystem.BOARD_WIDTH][BoardSystem.BOARD_HEIGHT];
        for(int x=0; x<BoardSystem.BOARD_WIDTH; x++)
        	for(int y=0; y<BoardSystem.BOARD_HEIGHT; y++)
        		boardMap[x][y] = 'F';
        
        // Starting random generator
        Random r = new Random();
        
        // Creating wall circles
        int circles = Math.abs(r.nextInt()%10)+15;
        for(int i=0; i<circles; i++) {
        	int x = Math.abs(r.nextInt()%BoardSystem.BOARD_WIDTH);
        	int y = Math.abs(r.nextInt()%BoardSystem.BOARD_HEIGHT);
        	int circleSize = Math.abs(r.nextInt()%10)+5;
        	
        	for(int j=0; j<circleSize; j++) {
        		char wallType;
        		if(j > circleSize/2) wallType = 'D';
        		else wallType = 'S';
        		
        		if(x+j < BoardSystem.BOARD_WIDTH) boardMap[x+j][y] = wallType;
    			if(x-j > 0) boardMap[x-j][y] = wallType;
    			for(int k=0; k<circleSize-j; k++) {
    				if(x+j < BoardSystem.BOARD_WIDTH && y+k < BoardSystem.BOARD_HEIGHT) boardMap[x+j][y+k] = wallType;
    				if(x+j < BoardSystem.BOARD_WIDTH && y-k > 0) boardMap[x+j][y-k] = wallType;
    				if(x-j > 0 && y+k < BoardSystem.BOARD_HEIGHT) boardMap[x-j][y+k] = wallType;
    				if(x-j > 0 && y-k > 0) boardMap[x-j][y-k] = wallType;
    			}
    			
    			if(y+j < BoardSystem.BOARD_HEIGHT) boardMap[x][y+j] = wallType;
    			if(y-j > 0) boardMap[x][y-j] = wallType;
    			for(int k=0; k<circleSize-j; k++) {
    				if(y+j < BoardSystem.BOARD_HEIGHT && x+k < BoardSystem.BOARD_WIDTH) boardMap[x+k][y+j] = wallType;
    				if(y+j < BoardSystem.BOARD_HEIGHT && x-k > 0) boardMap[x-k][y+j] = wallType;
    				if(y-j > 0 && x+k < BoardSystem.BOARD_WIDTH) boardMap[x+k][y-j] = wallType;
    				if(y-j > 0 && x-k > 0) boardMap[x-k][y-j] = wallType;
    			}
        	}
        }
        
        // Removing some random walls
        for(int x=0; x<BoardSystem.BOARD_WIDTH; x++)
        	for(int y=0; y<BoardSystem.BOARD_HEIGHT; y++)
        		if(Math.abs(r.nextInt()%5) == 0)
        			boardMap[x][y] = 'F';
        
        // Clearing the corners
        boardMap[1][1] = 'F';
	    boardMap[1][2] = 'F';
	    boardMap[2][1] = 'F';
	    boardMap[2][2] = 'F';

        boardMap[BoardSystem.BOARD_WIDTH-2][1] = 'F';
	    boardMap[BoardSystem.BOARD_WIDTH-2][2] = 'F';
	    boardMap[BoardSystem.BOARD_WIDTH-3][1] = 'F';
	    boardMap[BoardSystem.BOARD_WIDTH-3][2] = 'F';

        boardMap[1][BoardSystem.BOARD_HEIGHT-2] = 'F';
	    boardMap[1][BoardSystem.BOARD_HEIGHT-3] = 'F';
	    boardMap[2][BoardSystem.BOARD_HEIGHT-2] = 'F';
	    boardMap[2][BoardSystem.BOARD_HEIGHT-3] = 'F';

        boardMap[BoardSystem.BOARD_WIDTH-2][BoardSystem.BOARD_HEIGHT-2] = 'F';
	    boardMap[BoardSystem.BOARD_WIDTH-2][BoardSystem.BOARD_HEIGHT-3] = 'F';
	    boardMap[BoardSystem.BOARD_WIDTH-3][BoardSystem.BOARD_HEIGHT-2] = 'F';
	    boardMap[BoardSystem.BOARD_WIDTH-3][BoardSystem.BOARD_HEIGHT-3] = 'F';
	    
        // Clearing the tunnels
	    int tunnels = Math.abs(r.nextInt()%5)+5;
	    for(int i=0; i<tunnels; i++) {
	    	int startX = Math.abs(r.nextInt()%BoardSystem.BOARD_WIDTH);
	    	int startY = Math.abs(r.nextInt()%BoardSystem.BOARD_HEIGHT);
	    	int length = Math.abs(r.nextInt()%15)+5;
	    	int direction = Math.abs(r.nextInt()%4);
	    	
	    	switch(direction) {
	    	case 0:
	    		for(int x=startX; x<startX+length; x++)
	    			if(x < BoardSystem.BOARD_WIDTH)
	    				boardMap[x][startY] = 'F';
	    		break;
	    	case 1:
	    		for(int y=startY; y<startY+length; y++)
	    			if(y < BoardSystem.BOARD_HEIGHT)
	    				boardMap[startX][y] = 'F';
	    		break;
	    	case 2:
	    		for(int x=startX; x>startX-length; x--)
	    			if(x > 0)
	    				boardMap[x][startY] = 'F';
	    		break;
	    	case 3:
	    		for(int y=startY; y>startY-length; y--)
	    			if(y > 0)
	    				boardMap[startX][y] = 'F';
	    		break;
	    	}
	    }
	    
	    // Adding the indestructible walls frame
	    for(int x=0; x<BoardSystem.BOARD_WIDTH; x++) {
	    	boardMap[x][0] = 'I';
	    	boardMap[x][BoardSystem.BOARD_HEIGHT-1] = 'I';
	    }
	    
	    for(int y=0; y<BoardSystem.BOARD_HEIGHT; y++) {
	    	boardMap[0][y] = 'I';
	    	boardMap[BoardSystem.BOARD_WIDTH-1][y] = 'I';
	    }
        
	    // Spawning the walls and floors
        for(int x=0; x<BoardSystem.BOARD_WIDTH; x++)
        	for(int y=0; y<BoardSystem.BOARD_HEIGHT; y++)
        		if(boardMap[x][y] == 'F') {
        			gameObjects.add(new Floor(this, x, y));
        		} else if(boardMap[x][y] == 'S') {
                    gameObjects.add(new Floor(this, x, y));
                    gameObjects.add(new StoneWall(this, x, y));
        		} else if(boardMap[x][y] == 'D') {
                    gameObjects.add(new Floor(this, x, y));
                    gameObjects.add(new StoneWall(this, x, y));
        		} else if(boardMap[x][y] == 'I') {
                    gameObjects.add(new Floor(this, x, y));
                    gameObjects.add(new IndestructibleWall(this, x, y));
        		}
	}
	
	private void spawnCharacters() {
		
        player = new Player(this, 1, 1);
        
		gameObjects.add(player);
        gameObjects.add(new Enemy(this, BoardSystem.BOARD_WIDTH-2, 1));
        gameObjects.add(new Enemy(this, BoardSystem.BOARD_WIDTH-2, BoardSystem.BOARD_HEIGHT-2));
        gameObjects.add(new MousePointer(this));
        gameObjects.add(new HudManager(this));
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
	}

    @Override
    public void dispose() {
        resourceManager.dispose();
    }
	
	@Override
	public void update() {

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
    public GameObject findObject(String className) {
        for(GameObject o : gameObjects) {
            if(o.getClass().getSimpleName().equals(className)) {
                return o;
            }
        }
        return null;
    }

    @Override
    public void createObject(GameObject object) {
        creationQueue.add(object);
    }

    @Override
    public void destroyObject(GameObject object) {
        destructionQueue.add(object);
        getSystem(BoardSystem.class).destroyObject(object);
    }

    @Override
    public void destroyObject(int id) {
        destroyObject(getObject(id));
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
