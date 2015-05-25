package com.hilldev.hill60.systems;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.hilldev.hill60.Debug;
import com.hilldev.hill60.Hill60Main;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.components.SoundTrigger;
import com.hilldev.hill60.components.WorldPosition;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.Layer;
import com.hilldev.hill60.components.Collider;
import com.hilldev.hill60.components.SpriteRenderer;

public class RenderingSystem extends AEntitySystem {

	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;
	boolean inDebugMode = false;
	OrthographicCamera dynamicCamera;
	OrthographicCamera staticCamera;
	SpriteBatch batch;
	ShapeRenderer shape;

	public RenderingSystem(IEngine engine) {
		super(engine);

		dynamicCamera = new OrthographicCamera(800, 600);
		staticCamera = new OrthographicCamera(800, 600);

		// Initialize batchers
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
	}

	// Renders all objects with required components
	private void render() {
		// Reorder the objects
		List<GameObject> objList = engine.getObjectList();
		List<GameObject> objListInOrder = new ArrayList<>();

		int boardHeight = 100;
		int lastLayer = 5;

        Debug.log("");

        long a = System.nanoTime();

		// For each rendering layer
		for (int j = 0; j <= lastLayer; j++) {
			// For each y position on board
			for (int i = boardHeight; i >= 0; i--) {

				// Render an object...
				for (GameObject o : objList) {

					// If the conditions are met
					if (meetsConditions(o)
							&& o.getComponent(BoardPosition.class).y == i
                            && o.getComponent(SpriteRenderer.class).layer == j)
						objListInOrder.add(o);
				}
			}
		}

        long b = System.nanoTime();

        Debug.log("Sorting time: " + (b-a));

		// Prepare the camera
		dynamicCamera.update();
		staticCamera.update();

		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        Debug.log("");

        a = System.nanoTime();

		// Render the objects sprites
		batch.setProjectionMatrix(dynamicCamera.combined);
		batch.begin();

		for (GameObject obj : objListInOrder) {
			drawObject(obj);
		}

		batch.end();

        b = System.nanoTime();

        Debug.log("Rendering time: " + (b-a));

		// While debugging
		if (inDebugMode) {
			// Render the colliders shapes
			shape.setProjectionMatrix(dynamicCamera.combined);
			shape.begin(ShapeType.Line);
			shape.setColor(Color.GREEN);

			for (GameObject obj : objListInOrder) {
				if (obj.hasComponent(Collider.class)) {
					drawColliderShape(obj);
				}
			}

			shape.end();

			// Display the debugging box
			List<String> debuggingInfo = new ArrayList<>();

			WorldPosition pPos = Hill60Main.getInstance().player
					.getComponent(WorldPosition.class);
			BoardPosition pbPos = Hill60Main.getInstance().player
					.getComponent(BoardPosition.class);

			debuggingInfo.add("Player world position: x: " + pPos.x + " y: "
					+ pPos.y);
			debuggingInfo.add("Player board position: x: " + pbPos.x + " y: "
					+ pbPos.y);

			batch = new SpriteBatch();
			batch.setProjectionMatrix(staticCamera.combined);
			batch.begin();

			BitmapFont font = new BitmapFont();
			font.setColor(Color.GREEN);
			int i = 0;
			for (String str : debuggingInfo) {
				font.draw(batch, str, -SCREEN_WIDTH / 2 + 10, SCREEN_HEIGHT / 2
						- 10 - 20 * i);
				i++;
			}

			batch.end();
		}
	}

	private void drawObject(GameObject obj) {
		SpriteRenderer spriteRenderer = obj.getComponent(SpriteRenderer.class);
		Sprite sprite = spriteRenderer.sprite;		
		WorldPosition worldPosition = obj.getComponent(WorldPosition.class);

        // Scaling first so that the middle stays in the middle
        sprite.setScale(spriteRenderer.horizontalScale, spriteRenderer.verticalScale);

		float x = worldPosition.x
				- ((sprite.getWidth() - spriteRenderer.x) / 2);
		float y = worldPosition.y
				- ((sprite.getHeight() - spriteRenderer.y) / 2);

        sprite.setColor(spriteRenderer.color);
		sprite.setPosition(x, y);
		sprite.draw(batch);
	}

	private void drawColliderShape(GameObject obj) {
		Collider collider = obj.getComponent(Collider.class);

		WorldPosition worldPosition = obj.getComponent(WorldPosition.class);
		float x = worldPosition.x + collider.x - (collider.width / 2);
		float y = worldPosition.y + collider.y - (collider.height / 2);

		shape.rect(x, y, collider.width, collider.height);
	}

	// Checks if given objects contains BoardPosition, WorldPosition, Layer and
	// SpriteRenderer
	@Override
	protected boolean meetsConditions(GameObject o) {
		return o.hasComponent(BoardPosition.class)
				&& o.hasComponent(WorldPosition.class)
				&& o.hasComponent(SpriteRenderer.class);
	}

	@Override
	protected void processObject(GameObject obj) {
	}

	@Override
	public void update() {

		// Debugging handle
		inDebugMode = Gdx.input.isKeyPressed(Input.Keys.D);

		render();
	}
}
