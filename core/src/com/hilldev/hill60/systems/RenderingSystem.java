package com.hilldev.hill60.systems;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.SpriteRenderer;
import com.hilldev.hill60.components.WorldPosition;
import com.hilldev.hill60.components.BoardPosition;;

public class RenderingSystem extends IEntitySystem {
	
    SpriteBatch batch;
    Camera camera;

    public RenderingSystem(IEngine engine) {
    	super(engine);
    	
        camera = new OrthographicCamera(800, 600);
        batch = new SpriteBatch();
    }
    
    // Renders all objects with required components
    private void render() {

        // Prepare the camera
    	camera.update();
    	batch.setProjectionMatrix(camera.combined);

        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin rendering
        batch.begin();

        // Get the objects
        List<GameObject> list = engine.getObjectList();
        
        int boardHeight = 100;
        for(int i=boardHeight; i>=0; i--) {
            for (GameObject o : list)
                if (meetsConditions(o) && o.getComponent(BoardPosition.class).y == i)
                    processObject(o);
        }
        
        batch.end();
    }

    // Checks if given objects contains WorldPosition and SpriteRenderer
    @Override
    protected boolean meetsConditions(GameObject o) {
    	return o.hasComponent(BoardPosition.class) && o.hasComponent(WorldPosition.class) && o.hasComponent(SpriteRenderer.class);
    }
    
    // Draws an object
    @Override
    protected void processObject(GameObject obj) {
    	Sprite s = obj.getComponent(SpriteRenderer.class).sprite;
    	
    	float x = obj.getComponent(WorldPosition.class).x;
    	float y = obj.getComponent(WorldPosition.class).y;
    	
    	s.setPosition(x, y);
    	s.draw(batch);
    }
   
	@Override
	public void update() {
		render();
	}
}
