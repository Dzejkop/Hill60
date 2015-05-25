package com.hilldev.hill60.systems;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.Viewer;
import com.hilldev.hill60.components.Visibility;
import com.hilldev.hill60.objects.GameObject;

import java.util.List;

public class VisibilitySystem extends AEntitySystem {

    public VisibilitySystem(IEngine engine) {
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
        return obj.hasComponent(Viewer.class);
    }

    @Override
    protected void processObject(GameObject obj) {
        BoardPosition viewerPos = obj.getComponent(BoardPosition.class);

        // Get the board
        BoardSystem board = engine.getSystem(BoardSystem.class);

        // Option 1 tiles neighbouring the player
        for(int x = -1 ; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                setVisibilityTo(board.getObjectsAt(viewerPos.x + x, viewerPos.y + y), Visibility.Type.Visible);
            }
        }

        // Option 2 tiles in straight lines
    }

    private void setVisibilityTo(List<GameObject> objects, Visibility.Type val) {
        for(GameObject o : objects) {
            if(o.hasComponent(Visibility.class)) {
                o.getComponent(Visibility.class).isVisible = val;
            }
        }
    }
}
