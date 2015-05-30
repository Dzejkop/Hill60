package com.hilldev.hill60.objects;

import java.util.ArrayList;
import java.util.List;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.AComponent;

/*
 * Every object in the game descends from this class
 * It functions as a container for components
 */
public class GameObject {

    private static int ID = 1;
    private int objectID;

    public String tag = "Default";

    public IEngine engine;

    public boolean isActive = true;
    
    // A list of components
    List<AComponent> componentList;

    public GameObject(IEngine engine) {
        objectID = ID++;    // Unique id
        componentList = new ArrayList<AComponent>();

        this.engine = engine;
    }

    public int getID() {
    	return objectID;
    }
    
    // Adds a new component to list
    public void addComponent(AComponent component) {
        component.setParent(this);
        componentList.add(component);
    }

    // Checks if the object has a component of such type
    @SuppressWarnings("rawtypes")
	public boolean hasComponent(Class type) {
    	
    	for(AComponent c : componentList) {
    		if(c.getClass() == type) return true;
    	}
    	
    	return false;
    }
    
    // Returns a component of specified type, returns null if there's no such component
	@SuppressWarnings("unchecked")
	public <T extends AComponent> T getComponent(Class<T> type) {

    	for(AComponent c : componentList) {
    		if(c.getClass() == type) return (T)c;
    	}
    	
    	return null;
    }
    
    // Receives messages
    public void receiveMessage(String message, GameObject sender) {
    	
    }
}
