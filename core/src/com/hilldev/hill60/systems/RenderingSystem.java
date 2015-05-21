package com.hilldev.hill60.systems;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.components.WorldPosition;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.Layer;
import com.hilldev.hill60.components.Collider;
import com.hilldev.hill60.components.SpriteRenderer;

public class RenderingSystem extends AEntitySystem {
	
	boolean inDebugMode = false;
    OrthographicCamera camera;
    SpriteBatch batch;
	ShapeRenderer shape;
	
    public RenderingSystem(IEngine engine) {
    	super(engine);
    	
        camera = new OrthographicCamera(800, 600);
        batch = new SpriteBatch();
        shape = new ShapeRenderer();
    }
    
    // Renders all objects with required components
    private void render() {

        // Prepare the camera
    	camera.update();
    	batch.setProjectionMatrix(camera.combined);
        if(inDebugMode) shape.setProjectionMatrix(camera.combined);

        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Begin rendering
        batch.begin();
        if(inDebugMode) {
        	shape.begin(ShapeType.Line);
    		shape.setColor(0, 255, 0, 1);
        }
        
        // Get the objects
        List<GameObject> list = engine.getObjectList();
        
        int boardHeight = 100;
        int lastLayer = 4;

        // For each y position on board
        for(int i=boardHeight; i>=0; i--) {

            // For each rendering layer
            for(int j=0; j<=lastLayer; j++) {

                // Render an object...
                for(GameObject o : list) {

                    // If the conditions are met
                    if (meetsConditions(o) && o.getComponent(BoardPosition.class).y == i && o.getComponent(Layer.class).layer == j)
                        processObject(o);
                }
            }
        }
        
        if(inDebugMode) shape.end();
        batch.end();
    }

    // Checks if given objects contains BoardPosition, WorldPosition, Layer and SpriteRenderer
    @Override
    protected boolean meetsConditions(GameObject o) {
    	return o.hasComponent(BoardPosition.class) && o.hasComponent(WorldPosition.class) && o.hasComponent(Layer.class) && o.hasComponent(SpriteRenderer.class);
    }
    
    // Draws an object
    @Override
    protected void processObject(GameObject obj) {

        // Get components
        SpriteRenderer spriteRenderer = obj.getComponent(SpriteRenderer.class);

    	Sprite s = spriteRenderer.sprite;

    	WorldPosition worldPosition = obj.getComponent(WorldPosition.class);
    	s.setPosition(worldPosition.x + spriteRenderer.x - (s.getWidth()/2), worldPosition.y + spriteRenderer.y - (s.getHeight()/2));

    	s.draw(batch);
    	
    	// While debugging renders colliders green shapes
    	if(inDebugMode && obj.hasComponent(Collider.class)) {
    		Collider collider = obj.getComponent(Collider.class);
    		shape.rect(worldPosition.x + collider.x - (collider.width/2), worldPosition.y + collider.y - (collider.height/2), collider.width, collider.height);
    	}
    }
    
	@Override
	public void update() {

        // Debugging handle
        inDebugMode = Gdx.input.isKeyPressed(Input.Keys.D);

		render();
	}
}
