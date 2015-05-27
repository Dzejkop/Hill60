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
import com.hilldev.hill60.GameScreen;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.ResourceManager;
import com.hilldev.hill60.components.*;
import com.hilldev.hill60.objects.GameObject;

public class RenderingSystem extends AEntitySystem {

	public static int SCREEN_WIDTH = 800;
	public static int SCREEN_HEIGHT = 600;
	boolean inDebugMode = false;
	OrthographicCamera dynamicCamera;
	OrthographicCamera staticCamera;
	SpriteBatch batch;
	ShapeRenderer shape;

    // Shadows
    Sprite shadow;

	public RenderingSystem(IEngine engine) {
		super(engine);

		dynamicCamera = new OrthographicCamera(800, 600);
		staticCamera = new OrthographicCamera(800, 600);

		// Initialize batchers
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
	}

    @Override
    public void start() {
        super.start();

        ResourceManager manager = ((GameScreen)engine).resourceManager;

        shadow = manager.getSprite("X.png");
    }

    private void insertToList(GameObject obj, List<List<GameObject>> list) {

        BoardPosition bPos = obj.getComponent(BoardPosition.class);
        SpriteRenderer sp = obj.getComponent(SpriteRenderer.class);
        WorldPosition wPos = obj.getComponent(WorldPosition.class);
        int layer = sp.layer;
        int yPos = bPos.y;


        float paddingMultiplier = 1.2f;
        float w = dynamicCamera.viewportWidth * dynamicCamera.zoom * paddingMultiplier;
        float h = dynamicCamera.viewportHeight * dynamicCamera.zoom * paddingMultiplier;
        float minX = dynamicCamera.position.x - (w/2);
        float minY = dynamicCamera.position.y - (h/2);
        float maxX = minX + w;
        float maxY = minY + h;

        if(wPos.x < minX || wPos.x > maxX || wPos.y < minY || wPos.y > maxY) return;

        boolean added = false;

        List<GameObject> layerList = list.get(layer);

        for(int i = 0 ; i < layerList.size(); i++) {
            if(yPos >= layerList.get(i).getComponent(BoardPosition.class).y) {

                layerList.add(i, obj);
                added = true;
                break;
            }
        }

        if(added == false) {
            layerList.add(obj);
        }
    }

	// Renders all objects with required components
	private void render() {
		// Reorder the objects
		List<GameObject> objList = engine.getObjectList();
		List<List<GameObject>> objectsToRender = new ArrayList<>();

		int lastLayer = 6;

        for(int i = 0 ; i < lastLayer; i++) {
            objectsToRender.add(new ArrayList<GameObject>());
        }

        long a = System.nanoTime();

        // Render an object...
        for (GameObject o : objList) {

            // If the conditions are met
            if (meetsConditions(o)) {
                insertToList(o, objectsToRender);
            }
        }

        long b = System.nanoTime();
        long sortingTime = b-a;

		// Prepare the camera
		dynamicCamera.update();
		staticCamera.update();

		// Clear the screen
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        a = System.nanoTime();

		// Render the objects sprites
		batch.setProjectionMatrix(dynamicCamera.combined);
		batch.begin();

		for (List<GameObject> l : objectsToRender) {
			for(GameObject o : l) {
                drawObject(o);
            }
		}

		batch.end();

        b = System.nanoTime();
        long renderingTime = b-a;

		// While debugging
		if (inDebugMode) {
			// Render the colliders shapes
			shape.setProjectionMatrix(dynamicCamera.combined);
			shape.begin(ShapeType.Line);
			shape.setColor(Color.GREEN);

            for(List<GameObject> l : objectsToRender) {
                for (GameObject obj : l) {
                    if (obj.hasComponent(Collider.class)) {
                        drawColliderShape(obj);
                    }
                }
            }

			shape.end();

			// Display the debugging box
			List<String> debuggingInfo = new ArrayList<>();

			WorldPosition pPos = ((GameScreen)engine).player
					.getComponent(WorldPosition.class);
			BoardPosition pbPos = ((GameScreen)engine).player
					.getComponent(BoardPosition.class);

            debuggingInfo.add("Sorting time: " + sortingTime);
            debuggingInfo.add("Rendering time: " + renderingTime);
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

        /*if(obj.hasComponent(Visibility.class) && obj.getComponent(Visibility.class).isVisible == Visibility.Type.Invisible) {
            return;
        }*/

		SpriteRenderer spriteRenderer = obj.getComponent(SpriteRenderer.class);
		Sprite sprite = spriteRenderer.sprite;		
		WorldPosition worldPosition = obj.getComponent(WorldPosition.class);

        // Scaling first so that the middle stays in the middle
        sprite.setScale(spriteRenderer.horizontalScale, spriteRenderer.verticalScale);

		float x = worldPosition.x - (sprite.getWidth()  / 2) + spriteRenderer.x;
		float y = worldPosition.y - (sprite.getHeight() / 2) + spriteRenderer.y;

        sprite.setColor(spriteRenderer.color);
		sprite.setPosition(x, y);
		sprite.draw(batch);

        /*if(obj.hasComponent(Visibility.class) && obj.getComponent(Visibility.class).isVisible == Visibility.Type.HalfVisible) {
            shadow.setPosition(x, y);
            shadow.draw(batch);
        }*/
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
