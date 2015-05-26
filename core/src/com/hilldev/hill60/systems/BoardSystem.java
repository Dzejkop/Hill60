package com.hilldev.hill60.systems;

import java.util.ArrayList;
import java.util.List;

import com.hilldev.hill60.Debug;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.BoardPosition;
import com.hilldev.hill60.components.WorldPosition;
import com.hilldev.hill60.objects.GameObject;
import com.hilldev.hill60.objects.Wall;

public class BoardSystem extends AEntitySystem {

    class Tile {
        public List<GameObject> objects;

        public Tile() {
            objects = new ArrayList<>();
        }

        public void insert(GameObject obj) {
            objects.add(obj);
        }

        public void remove(GameObject obj) {
            objects.remove(obj);
        }
    }

    Tile[][] board;

    public BoardSystem(IEngine engine) {
		super(engine);

        // HARDCODED CREATION
        create(100, 100);
	}
    
    @Override
    public void update() {
        for(GameObject o : engine.getObjectList()) {
            if(meetsConditions(o)) processObject(o);
        }
    }

    public void create(int w, int h) {
        board = new Tile[w][];
        for(int i = 0 ; i < w; i++) {
            board[i] = new Tile[h];

            for(int q = 0; q < h; q++) {
                board[i][q] = new Tile();
            }
        }
    }
    
    @Override
    protected boolean meetsConditions(GameObject obj) {
        return obj.hasComponent(BoardPosition.class) && obj.hasComponent(WorldPosition.class);
    }

    @Override
    protected void processObject(GameObject obj) {
    	
    	float tileSize = BoardPosition.TILE_SIZE;
		BoardPosition boardPos = obj.getComponent(BoardPosition.class);
		WorldPosition worldPos = obj.getComponent(WorldPosition.class);

        int newX = boardPos.x;
        int newY = boardPos.y;

		if(worldPos.boardDependent) {
			worldPos.x = boardPos.x*tileSize;
			worldPos.y = boardPos.y*tileSize;
		} else {
			if(worldPos.x>0)
				newX = (int) ((worldPos.x+50)/tileSize);
			else
				newX = (int) ((worldPos.x-50)/tileSize);
			if(worldPos.y>0)
				newY = (int) ((worldPos.y+50)/tileSize);
			else
				newY = (int) ((worldPos.y-50)/tileSize);
		}

        if(newX != boardPos.x || newY != boardPos.y || boardPos.placedOnBoard == false) {
            // Object is switching position or is not on board

            if(boardPos.placedOnBoard == false) {
                if(isOnBoard(newX, newY)) board[newX][newY].insert(obj);

                boardPos.x = newX;
                boardPos.y = newY;
                boardPos.placedOnBoard = true;
            } else {
                board[boardPos.x][boardPos.y].remove(obj);

                if(isOnBoard(newX, newY)) board[newX][newY].insert(obj);

                boardPos.x = newX;
                boardPos.y = newY;
            }
        }
    }

    private boolean isOnBoard(int x, int y) {
        return !(x < 0 || x >= board.length || y < 0 || y >= board[0].length);
    }

    public List<GameObject> getObjectsAt(int x, int y) {

        /*List<GameObject> objects = new ArrayList<>();

        BoardPosition boardPosition;
        for(GameObject o : engine.getObjectList()) {
            if(meetsConditions(o)) {
                boardPosition = o.getComponent(BoardPosition.class);
                if(boardPosition.x == x && boardPosition.y == y) objects.add(o);
            }
        }*/

        if(isOnBoard(x, y) == false) return null;

        return board[x][y].objects;
    }

    public Wall getWallAt(int x, int y) {
    	
        BoardPosition boardPosition;

        if(isOnBoard(x, y) == false) return null;

        for(GameObject o : board[x][y].objects) {
            if(meetsConditions(o) && o.tag.equals("Wall")) {
                boardPosition = o.getComponent(BoardPosition.class);
                if(boardPosition.x == x && boardPosition.y == y) return (Wall)o;
            }
        }
        
        return null;
    }
}
