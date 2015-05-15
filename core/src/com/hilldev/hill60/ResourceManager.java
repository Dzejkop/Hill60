package com.hilldev.hill60;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceManager implements Disposable {

    public static final String ASSETS_PATH = "assets/";
    public static final String[] ASSETS_TO_LOAD = {"Player.png", "Floor.png", "Wall.png"};

    Map<String, Texture> loadedTextures;
    List<String> loadedTextureNames;

    public ResourceManager() {
        // Init the list and the map
        loadedTextures = new HashMap<>();
        loadedTextureNames = new ArrayList<>();
    }

    // Loads all resources
    public void loadTextures() {
        for(String s : ASSETS_TO_LOAD) {
            loadedTextures.put(s, new Texture(ASSETS_PATH + s));
            loadedTextureNames.add(s);
        }
    }

    Sprite getSprite(String n) {
        return new Sprite(loadedTextures.get(n));
    }

    @Override
    public void dispose() {
        for(String key : loadedTextureNames) {
            loadedTextures.get(key).dispose();
        }
    }
}
