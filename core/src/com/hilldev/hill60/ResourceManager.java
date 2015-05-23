package com.hilldev.hill60;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceManager implements Disposable {

    public static final String ASSETS_PATH = "assets/";
    public static final String[] ASSETS_TO_LOAD = {"Character.png", "Player.png", "Floor.png", "Wall.png", "WhiteTile.png"};
    public static final String[] SOUNDS_TO_LOAD = {"footstepBrick.ogg", "footstepsDirt.ogg"};
    
    Map<String, Texture> loadedTextures;
    List<String> loadedTextureNames;
    Map<Integer, String> loadedSoundNames;
    Map<Integer, Music> loadedSounds;    

    public ResourceManager() {
        // Init the list and the map
        loadedTextures = new HashMap<>();
        loadedTextureNames = new ArrayList<>();
        loadedSoundNames = new HashMap<>();
        loadedSounds = new HashMap<>();
    }

    // Loads all resources
    public void loadTextures() {
        for(String s : ASSETS_TO_LOAD) {
            loadedTextures.put(s, new Texture(ASSETS_PATH + s));
            loadedTextureNames.add(s);
        }
    }
    
    public void loadSounds() {
    	int i=1;
        for(String s : SOUNDS_TO_LOAD) {
        	Music temp=Gdx.audio.newMusic(Gdx.files.internal(ASSETS_PATH + s));
        	temp.setLooping(false);
            loadedSounds.put(i,temp);
            loadedSoundNames.put(i,s);
            i++;
        }
    }
    
    public Sprite getSprite(String n) {
        return new Sprite(loadedTextures.get(n));
    }
    
    public Music getSound(int n) {
        return loadedSounds.get(n) ;
    }
    
    public Music getSound(String n) {
        return loadedSounds.get(loadedSoundNames.get(n)) ;
    }

    @Override
    public void dispose() {
        for(String key : loadedTextureNames) {
            loadedTextures.get(key).dispose();
        }
        for (int i=1;i<loadedSoundNames.size();i++){
            loadedSounds.get(i).dispose();
        }
    }
}
