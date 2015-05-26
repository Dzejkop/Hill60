package com.hilldev.hill60.systems;

import com.badlogic.gdx.Game;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.ExplosionComponent;
import com.hilldev.hill60.components.ExplosionResistance;
import com.hilldev.hill60.objects.GameObject;

import java.util.List;

// Deals with destroying object that collide with explosions
public class ExplosionSystem extends AEntitySystem {

    public ExplosionSystem(IEngine engine) {
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
        return obj.hasComponent(ExplosionComponent.class);
    }

    @Override
    protected void processObject(GameObject obj) {

        BoardSystem boardSystem = engine.getSystem(BoardSystem.class);
        BoardPosition bPos = obj.getComponent(BoardPosition.class);

        List<GameObject> objectList = boardSystem.getObjectsAt(bPos.x, bPos.y);

        if(objectList == null) return;
        for(GameObject o : objectList) {
            if(obj != o && o.hasComponent(ExplosionResistance.class)) engine.destroyObject(o);
        }

    }
}
