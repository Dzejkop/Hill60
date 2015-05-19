package com.hilldev.hill60;

import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.systems.AEntitySystem;

import java.util.List;

public interface IEngine {
	public List<GameObject> getObjectList();	// Returns all the objects
	public GameObject getObject(int id);		// Finds the object with specified id
    public void start();                        // Called AFTER initializing all the systems
	public void update();						// Updates all entity systems

    public <T extends AEntitySystem> T getSystem(Class<T> type);

	// [To be implemented]
	//public void sendMessage(int receiverId, String message, int senderId);
}
