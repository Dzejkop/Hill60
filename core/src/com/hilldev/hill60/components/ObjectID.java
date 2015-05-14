package com.hilldev.hill60.components;

public class ObjectID extends AComponent {

	public ObjectID(int id) {
		this.id = id;
	}

	public ObjectID(String id) {
		switch (id) {
		case "floor":
			this.id = 1;
			break;
		case "wall":
			this.id = 2;
			break;
		case "player":
			this.id = 3;
			break;
		}

	}

	public String getID() {
		switch (id) {
		case 1:
			return "floor";
		case 2:
			return "wall";
		case 3:
			return "player";
		}
		return null;
	}
	
	
	public int id = 0;
}
