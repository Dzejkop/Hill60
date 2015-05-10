package com.hilldev.hill60;

import java.util.List;

public interface Engine {
	public List<GameObject> getObjectList();
	public GameObject getObject(int id);
	
	// [To be implemented]
	//public void sendMessage(int receiverId, String message, int senderId);
}
