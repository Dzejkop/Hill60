package com.hilldev.hill60;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.hilldev.hill60.components.SpriteRenderer;
import com.hilldev.hill60.components.WorldPosition;

public class Hill60Main extends ApplicationAdapter implements IEngine {
	
    // Singleton
    static Hill60Main instance;
    public static Hill60Main getInstance() {
        return instance;
    }

    // Game objects
    List<GameObject> gameObjects;
    
    // Systems
    List<IEntitySystem> systems;

    public Hill60Main() {
        instance = this;
        
        systems = new ArrayList<IEntitySystem>();
        gameObjects = new ArrayList<GameObject>();
    }

	@Override
	public void create () {
		
		systems.add(new RenderingSystem(this));
		
		// TESTING !!!!!!
		GameObject logo = new GameObject();
		Sprite sp = new Sprite(new Texture(new FileHandle("assets/Player.png")));
		logo.addComponent(new SpriteRenderer(sp));
		logo.addComponent(new WorldPosition(0, 20));
		
		gameObjects.add(logo);
	}

	@Override
	public void render () {

		for(IEntitySystem e : systems) {
			e.update();
		}

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
	
	
}
