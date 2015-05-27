package com.hilldev.hill60.systems;

import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.Viewer;
import com.hilldev.hill60.components.Visibility;
import com.hilldev.hill60.objects.GameObject;

public class VisibilitySystem extends AEntitySystem {

    public VisibilitySystem(IEngine engine) {
        super(engine);
    }

    GameObject viewer = null;

    @Override
    public void update() {

        if(viewer == null) {
            for(GameObject o : engine.getObjectList()) {
                if(o.hasComponent(Viewer.class)) {
                    viewer = o;
                    break;
                }
            }
        }

        for(GameObject o : engine.getObjectList()) {
            if(meetsConditions(o)) processObject(o);
        }
    }

    @Override
    protected boolean meetsConditions(GameObject obj) {
        return obj.hasComponent(Visibility.class);
    }

    @Override
    protected void processObject(GameObject obj) {
        BoardPosition viewerPos = viewer.getComponent(BoardPosition.class);
        BoardPosition objectPos = obj.getComponent(BoardPosition.class);

        BoardSystem board = engine.getSystem(BoardSystem.class);

        Visibility v = obj.getComponent(Visibility.class);

        // Find taxi cab distance
        int xDist  = Math.abs(viewerPos.x - objectPos.x);
        int yDist = Math.abs(viewerPos.y - objectPos.y);
        int taxiDist = xDist + yDist;

        Visibility.Type isVisible = Visibility.Type.Invisible;

        // If neighbouring player
        if(taxiDist == 0 || taxiDist == 1 || (taxiDist == 2 && (xDist == 1 && yDist == 1))) {
            isVisible = Visibility.Type.Visible;
        }
        // Is away from the viewer but in a straight line
        else if((xDist != 0 && yDist == 0) || (yDist != 0 && xDist == 0)) {
            if(isBlockedByWall(board, objectPos, viewerPos) == false) isVisible = Visibility.Type.Visible;
        }

        v.isVisible = isVisible;
    }

    private boolean isBlockedByWall(BoardSystem board, BoardPosition objectPos, BoardPosition viewerPos) {

        int xV = (int)Math.signum(viewerPos.x - objectPos.x);
        int yV = (int)Math.signum(viewerPos.y - objectPos.y);

        if(yV == 0) {
            for (int x = objectPos.x + xV; x != viewerPos.x; x += xV) {
                if (board.getWallAt(x, objectPos.y) != null) return true;
            }
        } else {
            for (int y = objectPos.y + yV; y != viewerPos.y; y += yV) {
                if (board.getWallAt(objectPos.x, y) != null) return true;
            }
        }

        return false;
    }
}
