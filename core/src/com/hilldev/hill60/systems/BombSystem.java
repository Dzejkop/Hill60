package com.hilldev.hill60.systems;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.ExplosionSpawn;
import com.hilldev.hill60.objects.GameObject;

/*
    Spawns explosions, executes countdown on bombs and such
 */
public class BombSystem extends AEntitySystem {

    public BombSystem(IEngine engine) {
        super(engine);
    }

    @Override
    public void update() {
        for(GameObject o : engine.getObjectList()) {
            if(meetsConditions(o)) processObject(o);
        }
    }

    @Override
    protected boolean meetsConditions(GameObject obj) {
        return obj.hasComponent(ExplosionSpawn.class);
    }

    @Override
    protected void processObject(GameObject obj) {

        ExplosionSpawn spawn = obj.getComponent(ExplosionSpawn.class);

        spawn.countdown--;
        if(spawn.countdown <= 0) {
            explode(obj);
        }

    }

    private void explode(GameObject center) {
        // Get components
        ExplosionSpawn spawn = center.getComponent(ExplosionSpawn.class);
        BoardPosition boardPosition = center.getComponent(BoardPosition.class);

        // Connect to the board system
        BoardSystem boardSystem = engine.getSystem(BoardSystem.class);

        // Ease of access
        int centerX = boardPosition.x;
        int centerY = boardPosition.y;
        int basePotential = spawn.bombPower;

        // First spawn the 4 corners
        for(int x = -1; x <= 1; x+=2) {
            for(int y = -1; y <= 1; y+=2) {
                spawnExplosion(x, y, basePotential - 1);
            }
        }
    }

    private void spawnExplosion(int x, int y, int power) {
        /*
        TO BE FILLED, HAS TO SPAWN AN EXPLOSION OBJECT
         */
    }
}
