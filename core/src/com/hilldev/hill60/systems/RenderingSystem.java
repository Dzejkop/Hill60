package com.hilldev.hill60.systems;

import java.util.ArrayList;
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
    }
    
    // Renders all objects with required components
    private void render() {
    	
        // Reorder the objects
        List<GameObject> objList = engine.getObjectList();
        List<GameObject> objListInOrder = new ArrayList<>();
        
        int boardHeight = 100;
        int lastLayer = 4;

        // For each y position on board
        for(int i=boardHeight; i>=0; i--) {

            // For each rendering layer
            for(int j=0; j<=lastLayer; j++) {

                // Render an object...
                for(GameObject o : objList) {

                    // If the conditions are met
                    if(meetsConditions(o) && o.getComponent(BoardPosition.class).y == i && o.getComponent(Layer.class).layer == j)
                    	objListInOrder.add(o);
                }
            }
        }

        // Prepare the camera
        camera.update();

        // Clear the screen
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Render the objects sprites
        batch = new SpriteBatch();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        
        for(GameObject obj : objListInOrder) {
        	drawObject(obj);
        }
        
        batch.end();
        
        // Render the colliders shapes
        if(inDebugMode) {
	        shape = new ShapeRenderer();
	        shape.setProjectionMatrix(camera.combined);
	        shape.begin(ShapeType.Line);
	        shape.setColor(0, 255, 0, 1);
	
	        for(GameObject obj : objListInOrder) {
	        	if(obj.hasComponent(Collider.class)) {
	        		drawColliderShape(obj);
	        	}
	        }
	        
	    	shape.end();
        }
    }
    
    private void drawObject(GameObject obj) {
        SpriteRenderer spriteRenderer = obj.getComponent(SpriteRenderer.class);
    	Sprite sprite = spriteRenderer.sprite;
    	
    	WorldPosition worldPosition = obj.getComponent(WorldPosition.class);
    	float x = worldPosition.x + spriteRenderer.x - (sprite.getWidth()/2);
    	float y = worldPosition.y + spriteRenderer.y - (sprite.getHeight()/2);
    	
    	sprite.setPosition(x, y);
    	sprite.draw(batch);
    }
    
    private void drawColliderShape(GameObject obj) {
		Collider collider = obj.getComponent(Collider.class);
		
    	WorldPosition worldPosition = obj.getComponent(WorldPosition.class);
		float x = worldPosition.x + collider.x - (collider.width/2);
		float y = worldPosition.y + collider.y - (collider.height/2);
		
		shape.rect(x, y, collider.width, collider.height);
    }
    
    // Checks if given objects contains BoardPosition, WorldPosition, Layer and SpriteRenderer
    @Override
    protected boolean meetsConditions(GameObject o) {
    	return o.hasComponent(BoardPosition.class) && o.hasComponent(WorldPosition.class) && o.hasComponent(Layer.class) && o.hasComponent(SpriteRenderer.class);
    }
    
    @Override
    protected void processObject(GameObject obj) {}
    
	@Override
	public void update() {

        // Debugging handle
        inDebugMode = Gdx.input.isKeyPressed(Input.Keys.D);

		render();
	}
}
