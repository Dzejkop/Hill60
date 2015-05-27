package com.hilldev.hill60;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen {
	
	private int SCREEN_WIDTH = 800;
	private int SCREEN_HEIGHT = 600;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private List<String> menuEntries;
	private int selection;
	private char lastKey;
	
	MenuScreen() {
		camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		menuEntries = new ArrayList<>();
		menuEntries.add("aaa aaa aaa");
		menuEntries.add("bbb bbb bbb");
		menuEntries.add("ccc ccc ccc");
		menuEntries.add("ddd ddd ddd");
		
		selection = 0;
		lastKey = '_';
	}
	
	@Override
	public void render(float delta) {
		
		if(Gdx.input.isKeyPressed(Keys.DOWN) && lastKey != 'D') {
			selection++;
			lastKey = 'D';
		}
		else if(Gdx.input.isKeyPressed(Keys.UP) && lastKey != 'U') {
			selection--;
			lastKey = 'U';
		}
		else {
			lastKey = '_';
		}
		
		camera.update();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		
		int i = 0;
		for (String menuEntry : menuEntries) {
			if(i == selection) font.setColor(Color.RED);
			else font.setColor(Color.WHITE);
			font.draw(batch, menuEntry, 0, menuEntries.size()*10 - i*20);
			i++;
		}
		
		batch.end();
	}
	
	@Override
	public void show() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void hide() {
	}

	@Override
	public void dispose() {
	}
}
