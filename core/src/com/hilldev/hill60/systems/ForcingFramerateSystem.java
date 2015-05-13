package com.hilldev.hill60.systems;

import com.hilldev.hill60.GameObject;
import com.hilldev.hill60.IEngine;

public class ForcingFramerateSystem extends AEntitySystem {
	final int FPS_MAX = 60;
	int fpsMax = 0;
	long lastFrameTime = 0;
	long thisFrameTime = 0;

	public ForcingFramerateSystem(IEngine engine) {
		super(engine);
	}
	/**
	 * This function froze Thread till average time between 2 frames runs
	 */
	@Override
	public void update() {
		thisFrameTime = System.currentTimeMillis();
		if (lastFrameTime + (1000 / fpsMax) > thisFrameTime) {
			try {
				do
				{
					Thread.sleep((long) 1);
					thisFrameTime = System.currentTimeMillis();
				}
				while (lastFrameTime + (1000 / fpsMax) > thisFrameTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println(1000/(thisFrameTime-lastFrameTime));
	}
	
	@Override
	public void start(){
		lastFrameTime = System.currentTimeMillis();
		fpsMax = FPS_MAX;
	}

	@Override
	protected boolean meetsConditions(GameObject obj) {
		return false;
	}

	@Override
	protected void processObject(GameObject obj) {

	}

}
