package com.hilldev.hill60;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Hill60Main extends ApplicationAdapter {
    // Singleton
    static Hill60Main instance;
    public static Hill60Main getInstance() {
        return instance;
    }

    RenderingManager renderingManager;

    public Hill60Main() {

        instance = this;
    }

	@Override
	public void create () {
		renderingManager = new RenderingManager();
	}

	@Override
	public void render () {

        renderingManager.render();

	}

    @Override
    public void dispose() {
        super.dispose();
        renderingManager.dispose();
    }
}
