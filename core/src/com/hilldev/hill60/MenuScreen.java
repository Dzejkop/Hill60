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
	
	Hill60Main game;
	private int SCREEN_WIDTH = 800;
	private int SCREEN_HEIGHT = 600;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private BitmapFont font;
	private List<String> menuEntries;
	private int selection;
	
	public MenuScreen(Hill60Main gam) {
		game = gam;
		
		camera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		batch = new SpriteBatch();
		font = new BitmapFont();
		
		menuEntries = new ArrayList<>();
		menuEntries.add("Start");
		menuEntries.add("Exit");
		
		selection = 0;
	}

	@Override
	public void render(float delta) {
		
		if(Gdx.input.isKeyJustPressed(Keys.DOWN) && selection < menuEntries.size()-1)
			selection++;
		else if(Gdx.input.isKeyJustPressed(Keys.UP) && selection > 0)
			selection--;
		else if(Gdx.input.isKeyJustPressed(Keys.ENTER))
			switch(selection) {
			case 0:
				game.setScreen(new GameScreen());
				break;
			case 1:
				Gdx.app.exit();
				break;
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
