package com.hilldev.hill60;

import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.systems.AEntitySystem;

import java.util.List;

public interface IEngine {
	
    public void start();                        	// Called AFTER initializing all the systems
	public void update();							// Updates all entity systems
	public List<GameObject> getObjectList();		// Returns all the objects
	public GameObject getObject(int id);			// Finds the object with specified id
    public GameObject findObject(String className); // Finds the object with specified simpleClass name (used for Player, HUD)
    public void createObject(GameObject object);    // Creates an object
    public void destroyObject(GameObject object);   // Destroys an object
    public void destroyObject(int id);              // Same only using id

    public void quit();

    public <T extends AEntitySystem> T getSystem(Class<T> type);
    public ResourceManager getResourceManager();
    public InputManager getInputManager();
}
