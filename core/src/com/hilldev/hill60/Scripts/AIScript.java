package com.hilldev.hill60.Scripts;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ai.pfa.indexed.IndexedAStarPathFinder;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BinaryHeap;
import com.badlogic.gdx.utils.IntArray;
import com.hilldev.hill60.Debug;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.InputManager;
import com.hilldev.hill60.components.*;
import com.hilldev.hill60.objects.Enemy;
import com.hilldev.hill60.objects.Player;
import com.hilldev.hill60.systems.BoardSystem;
import com.hilldev.hill60.systems.RenderingSystem;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.ai.*;

public class AIScript implements Behaviour {

    private static final int RUN_ANIMATION_SPEED = 3;
    private static final int SNEAK_ANIMATION_SPEED = 20;

    // Ease of access
    Enemy parent;
    IEngine engine;
    BehaviourComponent parentComponent;
    AnimationController animationController;
    CharacterScript characterScript;

    // A bit of cheating
    Player player;

    // Animations
    Animation walkSidewaysAnimation;
    Animation walkBackwardAnimation;
    Animation walkForwardAnimation;

    // Perception vars
    //List<Bomb> bombs;
    boolean canSeePlayer = false;
    boolean canHearPlayer = false;

    // State vars
    enum Mode {
        Wandering,      // Wandering aimlessly in straight lines, doing a perception check once every tile
        Idle,           // Not doing anything, waiting
        HearsPlayer,    // Is aware of player position, goes into sneak mode and tries to sneak up on him
        SeesPlayer,     // Can see the player, plant bomb and run away
        HeardPlayer,    //
        RunningAway     // From a bomb
    }

    // State vars
    private boolean inSneakMode = false;

    @Override
    public void create(BehaviourComponent parentComponent) {

        this.parentComponent = parentComponent;
        parent = (Enemy) (parentComponent.getParent());
        animationController = parent.getComponent(AnimationController.class);

        walkSidewaysAnimation = new Animation(walkAnimationFrames());
        walkBackwardAnimation = new Animation(walkBackwardAnimationFrames());
        walkForwardAnimation = new Animation(walkForwardAnimationFrames());

        // Connect to the character script
        characterScript = parentComponent.get(CharacterScript.class);

        // Connect to game engine
        engine = parent.engine;

        // Find player
        player = (Player)engine.findObject("Player");

        finalTarget = new Vector2(5, 5);

        if (animationController.getCurrentAnimation() == null)
            animationController.setAnimation(walkSidewaysAnimation);
    }

    Vector2 finalTarget;
    Vector2 currentTarget;
    boolean reachedTarget = false;

    int state = 0;

    @Override
    public void run() {

        animate();
        stop();

        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            InputManager manager = engine.getInputManager();

            float x = manager.getMousePos().x;
            float y = manager.getMousePos().y;

            goTo(x, y);
        }

        if(state == 0) {
            // Find target
        }

        if(state == 1) {
            // Go to target
        }
    }

    public float distance(Vector2 a, Vector2 b) {
        return (float)Math.sqrt(Math.pow(a.x - b.x ,2) + Math.pow(a.y - b.y ,2));
    }

    // In board pos
    public boolean goTo(int x, int y) {
        return goTo((float)x*BoardPosition.TILE_SIZE, (float)y*BoardPosition.TILE_SIZE);
    }

    // World pos
    public boolean goTo(float x, float y) {
        WorldPosition bPos  = parent.getComponent(WorldPosition.class);

        // Get movement vector
        float vx = x - bPos.x;
        float vy = y - bPos.y;

        float val = 20f;

        characterScript.goingRight = vx > val;
        characterScript.goingLeft = vx < -val;
        characterScript.goingUp = vy > val;
        characterScript.goingDown = vy < -val;

        if((vx*vx) + (vy*vy) < 20)  {
            stop();
            return true;
        }
        return false;
    }

    public boolean knowsWherePlayerIs() {
        if(canSeePlayer()) return true;

        PlayerScript playerScript = player.getComponent(BehaviourComponent.class).get(PlayerScript.class);

        BoardPosition playerPos = player.getComponent(BoardPosition.class);
        BoardPosition pos = parent.getComponent(BoardPosition.class);
        Velocity playerVelocity = player.getComponent(Velocity.class);

        if(playerVelocity.isZero()) return false;

        // Calculate distance
        float dist = distance(playerPos.getVector(), pos.getVector());
        float hearingDistance = 5;
        if(dist < hearingDistance && playerScript.isSneaking() == false) {
            return true;
        }

        return false;
    }

    public boolean canSeePlayer() {
        BoardPosition playerPos = player.getComponent(BoardPosition.class);
        BoardPosition pos = parent.getComponent(BoardPosition.class);

        BoardSystem board = engine.getSystem(BoardSystem.class);

        // First confition if is in a straight line of sight
        if(pos.x != playerPos.x && pos.y != playerPos.y) return false;

        // Second condition, not blocked by a wall
        int xv = (int)Math.signum(playerPos.x - pos.x);
        int yv = (int)Math.signum(playerPos.y - pos.y);
        int x = pos.x;
        int y = pos.y;

        while(x != playerPos.x && y != playerPos.y) {
            x+=xv;
            y+=yv;
            if(board.getWallAt(x, y) != null) return false;
        }

        return true;
    }

    private void stop() {
        characterScript.goingRight = false;
        characterScript.goingLeft = false;
        characterScript.goingUp = false;
        characterScript.goingDown = false;
    }

    private void animate() {

        if (characterScript.goingRight) {
            animationController.setAnimation(walkSidewaysAnimation);
            animationController.getCurrentAnimation().isActive = true;
            parent.spriteRenderer.isFlipped = false;
        } else if (characterScript.goingLeft) {
            animationController.setAnimation(walkSidewaysAnimation);
            animationController.getCurrentAnimation().isActive = true;
            parent.spriteRenderer.isFlipped = true;
        } else if (characterScript.goingUp) {
            animationController.setAnimation(walkForwardAnimation);
            animationController.getCurrentAnimation().isActive = true;
        } else if (characterScript.goingDown) {
            animationController.setAnimation(walkBackwardAnimation);
            animationController.getCurrentAnimation().isActive = true;
        } else {
            animationController.getCurrentAnimation().isActive = false;
            animationController.getCurrentAnimation().reset();
        }

        if (inSneakMode) {
            animationController.getCurrentAnimation().stepsPerFrame = SNEAK_ANIMATION_SPEED;
        } else {
            animationController.getCurrentAnimation().stepsPerFrame = RUN_ANIMATION_SPEED;
        }
    }

    private String[] walkAnimationFrames() {
        String[] f = new String[10];

        for (int i = 1; i <= 10; i++) {
            f[i - 1] = "CharacterWalk" + (i == 10 ? i : "0" + i);
        }

        return f;
    }

    private String[] walkBackwardAnimationFrames() {
        String[] f = new String[10];

        for (int i = 0; i < 10; i++) {
            f[i] = "CharacterWalkBackward0" + i;
        }

        return f;
    }

    private String[] walkForwardAnimationFrames() {
        String[] f = new String[10];

        for (int i = 0; i < 10; i++) {
            f[i] = "CharacterWalkForward0" + i;
        }

        return f;
    }


    // For pathfinding
    public static class Path {

        int passedByIndex = 0 ;
        public Path(List<Vector2> nodes) {
            this.nodes = nodes;
        }


        // Plots a path from start to end (board positions)
        public static Path plot(final IEngine engine, Vector2 startPoint, Vector2 endPoint) {
            List<Vector2> n = new ArrayList<>();

            BoardSystem board = engine.getSystem(BoardSystem.class);

            int w = BoardSystem.BOARD_WIDTH;
            int h = BoardSystem.BOARD_HEIGHT;

            Astar astar = new Astar(w, h) {
                @Override
                protected boolean isValid(int x, int y) {
                    return engine.getSystem(BoardSystem.class).getWallAt(x, y) == null;
                }
            };

            IntArray intArray = astar.getPath((int)startPoint.x, (int)startPoint.y, (int)endPoint.x, (int)endPoint.y);

            for(int i = 0 ; i < intArray.size; i+=2) {
                n.add(new Vector2(intArray.get(i), intArray.get(i+1)));
            }

            return new Path(n);
        }

        // Plots a linear path
        public static Path linearPlot(IEngine engine, Vector2 startPoint, Vector2 endPoint) {
            List<Vector2> n = new ArrayList<>();

            int xDiff= (int)Math.abs(startPoint.x - endPoint.x);
            int yDiff= (int)Math.abs(startPoint.y - endPoint.y);

            if(xDiff > yDiff) {
                int currX = (int)startPoint.x;
                while(currX != endPoint.x) {
                    n.add(new Vector2(currX++, startPoint.y));
                }
            } else {
                int currY = (int)startPoint.y;
                while(currY != endPoint.y) {
                    n.add(new Vector2(startPoint.x, currY++));
                }
            }

            return new Path(n);
        }

        List<Vector2> nodes;    // Nodes in a path

        // Gets the next node in list based on the current position (board positions)
        public Vector2 getNext() {
            if(passedByIndex == nodes.size()) return null;
            return nodes.get(passedByIndex++);
        }
    }

    /* Aquired from gist.github.com/NathanSweet/7587981 */
    static public class Astar {
        private final int width, height;
        private final BinaryHeap<PathNode> open;
        private final PathNode[] nodes;
        int runID;
        private final IntArray path = new IntArray();
        private int targetX, targetY;

        public Astar (int width, int height) {
            this.width = width;
            this.height = height;
            open = new BinaryHeap(width * 4, false);
            nodes = new PathNode[width * height];
        }

        /** Returns x,y pairs that are the path from the target to the start. */
        public IntArray getPath (int startX, int startY, int targetX, int targetY) {
            this.targetX = targetX;
            this.targetY = targetY;

            path.clear();
            open.clear();

            runID++;
            if (runID < 0) runID = 1;

            int index = startY * width + startX;
            PathNode root = nodes[index];
            if (root == null) {
                root = new PathNode(0);
                root.x = startX;
                root.y = startY;
                nodes[index] = root;
            }
            root.parent = null;
            root.pathCost = 0;
            open.add(root, 0);

            int lastColumn = width - 1, lastRow = height - 1;
            int i = 0;
            while (open.size > 0) {
                PathNode node = open.pop();
                if (node.x == targetX && node.y == targetY) {
                    while (node != root) {
                        path.add(node.x);
                        path.add(node.y);
                        node = node.parent;
                    }
                    break;
                }
                node.closedID = runID;
                int x = node.x;
                int y = node.y;
                if (x < lastColumn) {
                    addNode(node, x + 1, y, 10);
                    if (y < lastRow) addNode(node, x + 1, y + 1, 14); // Diagonals cost more, roughly equivalent to sqrt(2).
                    if (y > 0) addNode(node, x + 1, y - 1, 14);
                }
                if (x > 0) {
                    addNode(node, x - 1, y, 10);
                    if (y < lastRow) addNode(node, x - 1, y + 1, 14);
                    if (y > 0) addNode(node, x - 1, y - 1, 14);
                }
                if (y < lastRow) addNode(node, x, y + 1, 10);
                if (y > 0) addNode(node, x, y - 1, 10);
                i++;
            }
            return path;
        }

        private void addNode (PathNode parent, int x, int y, int cost) {
            if (!isValid(x, y)) return;

            int pathCost = parent.pathCost + cost;
            float score = pathCost + Math.abs(x - targetX) + Math.abs(y - targetY);

            int index = y * width + x;
            PathNode node = nodes[index];
            if (node != null && node.runID == runID) { // Node already encountered for this run.
                if (node.closedID != runID && pathCost < node.pathCost) { // Node isn't closed and new cost is lower.
                    // Update the existing node.
                    open.setValue(node, score);
                    node.parent = parent;
                    node.pathCost = pathCost;
                }
            } else {
                // Use node from the cache or create a new one.
                if (node == null) {
                    node = new PathNode(0);
                    node.x = x;
                    node.y = y;
                    nodes[index] = node;
                }
                open.add(node, score);
                node.runID = runID;
                node.parent = parent;
                node.pathCost = pathCost;
            }
        }

        protected boolean isValid (int x, int y) {
            return true;
        }

        public int getWidth () {
            return width;
        }

        public int getHeight () {
            return height;
        }

        static private class PathNode extends BinaryHeap.Node {
            int runID, closedID, x, y, pathCost;
            PathNode parent;

            public PathNode (float value) {
                super(value);
            }
        }
    }
}
