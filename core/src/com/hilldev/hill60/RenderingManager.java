package com.hilldev.hill60;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class RenderingManager extends GameObject implements Disposable {

    SpriteBatch batch;
    Camera camera;

    public RenderingManager() {

        // !!! Na próbę dałem 800 i 600 !!!
        camera = new OrthographicCamera(800, 600);
        batch = new SpriteBatch();
    }

    public void render() {
        batch.begin();

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
