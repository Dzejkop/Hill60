package com.hilldev.hill60.components;

public class SoundTrigger extends AComponent {

    public SoundTrigger(String sound) {
        this.sound = sound;
    }

	public boolean triggered = false;   // When this var is set to true, the sound system will play a sound and spawn a wave object
    public String sound;
}
