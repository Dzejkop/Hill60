package com.hilldev.hill60;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Disposable;

import java.util.HashMap;
import java.util.Map;

public class ResourceManager implements Disposable {

    public static final String SPRITE_SHEET_FILENAME = "spriteSheet.txt";
    public static final String ASSETS_PATH = "assets/";
    public static final String[] SOUNDS_TO_LOAD = {"footstepBrick.ogg", "footstepsDirt.ogg", "Explosion.wav"};
    
    TextureAtlas textureAtlas;
    Map<String, Integer> loadedSoundNames;
    Map<Integer, Sound> loadedSounds;
    
    public ResourceManager() {
        // Init the list and the map
        loadedSoundNames = new HashMap<>();
        loadedSounds = new HashMap<>();
    }
    
    // Loads all resources
    public void loadTextures() {
        textureAtlas = new TextureAtlas(ASSETS_PATH + SPRITE_SHEET_FILENAME);
    }
    
    public void loadSounds() {
    	int i=1;
        for(String s : SOUNDS_TO_LOAD) {
        	Sound temp = Gdx.audio.newSound(Gdx.files.internal(ASSETS_PATH + s));
            loadedSounds.put(i, temp);
            loadedSoundNames.put(s, i);
            i++;
        }
    }
    
    public Sprite getSprite(String n) {
        Sprite s = textureAtlas.createSprite(n);
        s.getTexture().setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        return s;
    }
    
    public Sound getSound(int n) {
        return loadedSounds.get(n) ;
    }
    
    public Sound getSound(String n) {
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
