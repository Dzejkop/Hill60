package com.hilldev.hill60.components;

public class InputResponder extends Component {
	
	public InputResponder() {
		downArrow = false;
		upArrow = false;
		rightArrow  = false;
		leftArrow = false;
	}
	
	public void set(boolean a, boolean b, boolean c, boolean d) {
        upArrow = a;
        downArrow = b;
        leftArrow = c;
        rightArrow = d;
	}
	
	public boolean downArrow;
	public boolean upArrow;
	public boolean rightArrow;
	public boolean leftArrow;
}
