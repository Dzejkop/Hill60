package com.hilldev.hill60;

import com.badlogic.gdx.Game;

public class Hill60Main extends Game {

    GameScreen gameScreen;
    MenuScreen menuScreen;

	public void create() {

        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        switchToMenu();
	}

    public void switchToGame() {
        if(gameScreen != null) gameScreen.dispose();
        gameScreen = new GameScreen(this);
        this.setScreen(gameScreen);
    }

    public void switchToMenu() {
        this.setScreen(menuScreen);
    }

	public void render() {
		super.render();
	}

    public void dispose() {
    	super.dispose();

        menuScreen.dispose();
        gameScreen.dispose();
    }
}
