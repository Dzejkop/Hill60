package com.hilldev.hill60;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager implements Disposable {

    public static final String SPRITESHEET_FILENAME = "spriteSheet.txt";
    public static final String ASSETS_PATH = "assets/";
    public static final String[] ASSETS_TO_LOAD = {"Character.png", "Player.png", "Floor.png", "Wall.png", "WhiteTile.png", "Ring.png", "X.png"};
    public static final String[] SOUNDS_TO_LOAD = {"footstepBrick.ogg", "footstepsDirt.ogg"};

    TextureAtlas textureAtlas;
    Map<String, Integer> loadedSoundNames;
    Map<Integer, Music> loadedSounds;    

    public ResourceManager() {
        // Init the list and the map
        loadedSoundNames = new HashMap<>();
        loadedSounds = new HashMap<>();
    }

    // Loads all resources
    public void loadTextures() {
        textureAtlas = new TextureAtlas(ASSETS_PATH + SPRITESHEET_FILENAME);

        Debug.log("");
    }
    
    public void loadSounds() {
    	int i=1;
        for(String s : SOUNDS_TO_LOAD) {
        	Music temp=Gdx.audio.newMusic(Gdx.files.internal(ASSETS_PATH + s));
        	temp.setLooping(false);
            loadedSounds.put(i,temp);
            loadedSoundNames.put(s, i);
            i++;
        }
    }
    
    public Sprite getSprite(String n) {

        return textureAtlas.createSprite(n);
    }
    
    public Music getSound(int n) {
        return loadedSounds.get(n) ;
    }
    
    public Music getSound(String n) {
        return loadedSounds.get(loadedSoundNames.get(n)) ;
    }

    @Override
    public void dispose() {
        textureAtlas.dispose();
        for (int i=1;i<loadedSoundNames.size();i++){
            loadedSounds.get(i).dispose();
        }
    }
}
