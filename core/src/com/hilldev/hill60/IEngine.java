package com.hilldev.hill60;

import java.util.List;

public interface IEngine {
	public List<GameObject> getObjectList();	// Returns all the objects
	public GameObject getObject(int id);		// Finds the object with specified id
	public void update();						// Updates all entity systems
	
	// [To be implemented]
	//public void sendMessage(int receiverId, String message, int senderId);
}
