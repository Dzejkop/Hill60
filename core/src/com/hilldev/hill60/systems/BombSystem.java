package com.hilldev.hill60.systems;

import com.hilldev.hill60.Debug;
import com.hilldev.hill60.Hill60Main;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.ExplosionResistance;
import com.hilldev.hill60.components.ExplosionSpawn;
import com.hilldev.hill60.objects.Bomb;
import com.hilldev.hill60.objects.Explosion;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.objects.Wall;

import java.util.ArrayList;
import java.util.List;

/*
 * Spawns explosions, executes countdown on bombs and such
 */
public class BombSystem extends AEntitySystem {

    private static final int SIDE_NODE_SPAWN_COST = 2;
    private static final int SINGLE_TRANSITION_COST = 1;
    private static final int DESTROY_WALL_BASE_COST = 0;

    public BombSystem(IEngine engine) {
        super(engine);
    }

    @Override
    public void update() {

        List<GameObject> toProcess = new ArrayList<>();

        for(GameObject o : engine.getObjectList()) {
            if(meetsConditions(o)) toProcess.add(o);
        }

        for(GameObject o : toProcess) {
            processObject(o);
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

            // Destroy the bomb
            engine.destroyObject(obj);
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

        // First spawn the center
        spawnExplosion(centerX, centerY, basePotential);

        /*// Next spawn the 4 corners
        for(int x = -1; x <= 1; x+=2) {
            for(int y = -1; y <= 1; y+=2) {
                if(canBeSpawned(centerX + x, centerY + y, basePotential-1)) spawnExplosion(centerX + x, centerY + y, 0);
            }
        }*/

        // Next spawn directional explosions on edges
        for(int x = -1; x <= 1; x+=2) {
            spawnExplosionNode(centerX + x, centerY, basePotential, x, 0);
        }
        for(int y = -1; y <= 1; y+=2) {
            spawnExplosionNode(centerX, centerY + y, basePotential, 0, y);
        }

        // And that's about it

    }

    private void spawnExplosion(int x, int y, int power) {
        Hill60Main main = ((Hill60Main)engine);

        main.createObject(new Explosion(engine, x, y ,power));
    }

    /*
        The x and y describe the first explosion of the node
        NOT THE CENTER OF THE EXPLOSION
     */
    private void spawnExplosionNode(int x, int y, int power, int xDir, int yDir) {
        int currentX = x;
        int currentY = y;
        int explosionPotential = power;

        while(explosionPotential > 0) {
            // Now, go forward
            int res = getResistanceAt(currentX, currentY);

            // Subtract one power for moving a single space


            if(explosionPotential > res) {

                // Try spawning side nodes with reduced power
                /**
                 * ENG
                 * I'm using a trick here, by switching y with x and x with y
                 * that way I'm essentially rotating everything by 90 degrees
                 *
                 * PL
                 * Taki trik, zamieniająć x z y oraz y z x
                 * właściwie wykonuje obrót o 90 stopni
                 */

                // First one side
                if(canBeSpawned(currentX + yDir, currentY + xDir, explosionPotential - SIDE_NODE_SPAWN_COST)) {
                    spawnExplosionNode(currentX + yDir, currentY + xDir, explosionPotential - SIDE_NODE_SPAWN_COST, yDir, xDir);
                }
                // And the other side
                if(canBeSpawned(currentX - yDir, currentY - xDir, explosionPotential - SIDE_NODE_SPAWN_COST)) {
                    spawnExplosionNode(currentX - yDir, currentY - xDir, explosionPotential - SIDE_NODE_SPAWN_COST, -yDir, -xDir);
                }

                // Spawn the explosion, subtract from power
                explosionPotential-=res;
                explosionPotential-=SINGLE_TRANSITION_COST;

                if(res != 0) explosionPotential-=DESTROY_WALL_BASE_COST;

                spawnExplosion(currentX, currentY, explosionPotential);

                // Move forward
                currentX += xDir;
                currentY += yDir;
            } else {
                // Finish spawning
                break;
            }

        }
    }

    private boolean canBeSpawned(int x, int y, int power) {
        if(getResistanceAt(x, y) <= power) return true;
        return false;
    }

    private int getResistanceAt(int x, int y) {
        BoardSystem boardSystem = engine.getSystem(BoardSystem.class);

        Wall wall = boardSystem.getWallAt(x, y);

        if(wall == null) return 0;

        ExplosionResistance explosionResistance = wall.getComponent(ExplosionResistance.class);

        return explosionResistance.resistancePoints;
    }
}
