package com.hilldev.hill60.components;

public class WorldPosition extends Component {

	public WorldPosition(float x, float y, boolean boardDependent) {
		this.x = x;
		this.y = y;
		this.boardDependent = boardDependent;
	}
	
	public WorldPosition(float x, float y) {
		this(x, y, true);
	}

	public float x;
	public float y;
	public boolean boardDependent;
}
