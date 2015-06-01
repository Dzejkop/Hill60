package com.hilldev.hill60.components;

public class SoundTrigger extends AComponent {

    public SoundTrigger(String sound, float volume ) {
        this.sound = sound;
        this.volume = volume;
    }

	public boolean triggered = false;   // When this var is set to true, the sound system will play a sound and spawn a wave object
    public String sound;
    public float volume = 1;
    public int soundID=-1;
}
