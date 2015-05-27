package com.hilldev.hill60;

import com.badlogic.gdx.Game;

public class Hill60Main extends Game {

	public void create() {
		this.setScreen(new MenuScreen());
	}
	
	public void render() {
		super.render();
	}

	
    public void dispose() {
    	super.dispose();
    }
}
