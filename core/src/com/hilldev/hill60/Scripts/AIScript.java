package com.hilldev.hill60.Scripts;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.BinaryHeap;
import com.badlogic.gdx.utils.IntArray;
import com.hilldev.hill60.IEngine;
import com.hilldev.hill60.components.*;
import com.hilldev.hill60.objects.Enemy;
import com.hilldev.hill60.objects.Player;
import com.hilldev.hill60.systems.BoardSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIScript implements Behaviour {

    private static final int RUN_ANIMATION_SPEED = 3;
    private static final int SNEAK_ANIMATION_SPEED = 20;
    private static final int MAX_POSITIONS_LOGGED = 5;

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

    Vector2 rememberedPosition;

    // State vars
    enum Mode {
        Wandering,      // Wandering aimlessly in straight lines, doing a perception check once every tile
        Idle,           // Not doing anything, waiting
        HearsPlayer,    // Is aware of player position, goes into sneak mode and tries to sneak up on him
        HeardPlayer,    // Remebers a position and sneaks toward it
        SeesPlayer,     // Can see the player, plant bomb and run away
        RunningAway,    // From a bomb
        DEBUG
    }
    Mode currentMode = Mode.DEBUG;

    // State vars
    private boolean inSneakMode = false;

    // Position log
    List<Vector2> positionLog;

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

        // Initiate lists
        positionLog = new ArrayList<>();
        for(int i = 0 ; i < MAX_POSITIONS_LOGGED; i++) {
            positionLog.add(parent.getComponent(BoardPosition.class).getVector());
        }

        // Find player
        player = (Player)engine.findObject("Player");

        if (animationController.getCurrentAnimation() == null)
            animationController.setAnimation(walkSidewaysAnimation);
    }

    int sinceLastPerceptionCheck = 0;
    int perceptionCheckLimit = 50;

    String wanderingDirection = "Left";

    @Override
    public void run() {

        animate();
        stop();
        logPosition();

        // Perform a perception check every now and then
        sinceLastPerceptionCheck++;
        if(sinceLastPerceptionCheck >= perceptionCheckLimit) {
            perceptionCheck();
        }

        characterScript.inSneakMode = false;
        if(currentMode == Mode.Idle) {

        }
        else if(currentMode == Mode.Wandering) {
            wander();
        }
        else if(currentMode == Mode.HearsPlayer) {
            characterScript.inSneakMode = true;

            // Maybe not the best option
            /*if(distanceToPlayer() < 4) {
                putBigBomb();
            }*/

            walkThePath(player.getComponent(BoardPosition.class).getVector());
        }
        else if(currentMode == Mode.HeardPlayer) {
            characterScript.inSneakMode = true;
            goTo((int)rememberedPosition.x, (int)rememberedPosition.y);
        }
        else if(currentMode == Mode.SeesPlayer) {
            if(distanceToPlayer() < 4) {
                putBomb();
                switchMode(Mode.RunningAway);
            } else {
                walkThePath(player.getComponent(BoardPosition.class).getVector());
            }
        }
        else if(currentMode == Mode.RunningAway) {

        }

    }

    // Movement vars
    Vector2 node;
    boolean nodeSet = false;
    Path path;

    private void decideBehaviour() {

        if(currentMode == Mode.HeardPlayer) {
            // Only change if reached the target position
            howLongInHeardState++;

            if(howLongInHeardState > maxHeardStateLength) {
                howLongInHeardState = 0;
                switchMode(Mode.Wandering);
            }

            Vector2 currentPos = parent.getComponent(BoardPosition.class).getVector();
            if(rememberedPosition.x == currentPos.x && rememberedPosition.y == currentPos.y) {
                switchMode(Mode.Wandering);
            }
        }

        if(canHearPlayer && !canSeePlayer) {
            switchMode(Mode.HearsPlayer);
        }

        if(canSeePlayer) {
            switchMode(Mode.SeesPlayer);
        }

        if(!canSeePlayer && !canHearPlayer && currentMode != Mode.HeardPlayer) {
            switchMode(Mode.Wandering);
        }
    }

    int howLongInHeardState = 0;
    int maxHeardStateLength = 100;

    int sinceLastChangeInDirection = 0;
    int changeInDirectionInterval = 100;
    private void wander() {

        sinceLastChangeInDirection++;
        if(sinceLastChangeInDirection >= changeInDirectionInterval) {
            sinceLastChangeInDirection = 0;
            wanderingDirection = chooseWanderingDirection();
        }

        BoardPosition bPos = parent.getComponent(BoardPosition.class);
        if(wanderingDirection.equals("Left")) {
            goLeft();
            characterScript.getItem("Shovel").use("left", bPos.x, bPos.y, engine);
        } else if(wanderingDirection.equals("Right")) {
            goRight();
            characterScript.getItem("Shovel").use("right", bPos.x, bPos.y, engine);
        } else if(wanderingDirection.equals("Up")) {
            goUp();
            characterScript.getItem("Shovel").use("up", bPos.x, bPos.y, engine);
        } else if(wanderingDirection.equals("Down")) {
            goDown();
            characterScript.getItem("Shovel").use("down", bPos.x, bPos.y, engine);
        } else {
            // Linger around, do nothing
        }
    }

    private String chooseWanderingDirection() {
        Random random = new Random();

        int n = random.nextInt()%101;

        if(n < 20) {
            return "Left";
        } else if(n < 40) {
            return "Right";
        } else if(n < 60) {
            return "Up";
        } else if(n < 80){
            return "Down";
        } else {
            return "None";
        }
    }

    private void onTileChange() {
        perceptionCheck();

        decideBehaviour();
    }

    private void putBomb() {
        Item bigBomb = characterScript.getItem("BigBomb");
        Item smallBomb = characterScript.getItem("SmallBomb");
        Item mediumBomb = characterScript.getItem("MediumBomb");

        BoardPosition bPos = parent.getComponent(BoardPosition.class);

        if(bigBomb.isReady()) bigBomb.use("forward", bPos.x, bPos.y, engine);
        else if(mediumBomb.isReady()) mediumBomb.use("forward", bPos.x, bPos.y, engine);
        else if(smallBomb.isReady()) smallBomb.use("forward", bPos.x, bPos.y, engine);
    }

    private void switchMode(Mode newMode) {

        terminateMovement();

        if(currentMode == Mode.HearsPlayer && newMode != Mode.SeesPlayer) {
            rememberedPosition = player.getComponent(BoardPosition.class).getVector();
            currentMode = Mode.HeardPlayer;
            howLongInHeardState = 0;
        } else {
            currentMode = newMode;
        }
    }

    private void logPosition() {
        positionLog.remove(0);  // Remove the oldest element
        positionLog.add(parent.getComponent(BoardPosition.class).getVector());  // Log a new one

        // Check if changed tile
        Vector2 a = positionLog.get(MAX_POSITIONS_LOGGED-1);
        Vector2 b = positionLog.get(MAX_POSITIONS_LOGGED-2);
        if(a.x != b.x && a.y != b.y) onTileChange();
    }

    private void perceptionCheck() {
        //Debug.log("Perception check");
        sinceLastPerceptionCheck = 0;
        canSeePlayer = canSeePlayer();
        canHearPlayer = canHearPlayer();

        decideBehaviour();

        //Debug.log("Can see player? " + canSeePlayer);
        //Debug.log("Can hear player? " + canHearPlayer);
    }

    private void terminateMovement() {
        nodeSet = false;
        path = null;
        node = null;
    }

    private boolean walkThePath(Vector2 target) {
        Vector2 cPos = parent.getComponent(BoardPosition.class).getVector();
        if(target.x == cPos.x && target.y == cPos.y) return true; // Reached destination

        if(nodeSet) {
            if (node == null) {
                nodeSet = false;
            } else {
                goTo((int) node.x, (int) node.y);
                if(node.x == cPos.x && node.y == cPos.y) nodeSet = false;
            }
        } else {
            // Get path
            path = Path.plot(engine,
                    parent.getComponent(BoardPosition.class).getVector(),
                    target
            );

            // Follow the path
            node = path.getNext();
            nodeSet = true;
        }
        return false;
    }

    public float distance(Vector2 a, Vector2 b) {
        return (float)Math.sqrt(Math.pow(a.x - b.x ,2) + Math.pow(a.y - b.y ,2));
    }

    public float distanceToPlayer() {
        Vector2 currPos = parent.getComponent(BoardPosition.class).getVector();

        return distance(currPos, player.getComponent(BoardPosition.class).getVector());
    }

    // Simple movement functions
    public void goLeft() {
        characterScript.goingLeft = true;
    }
    public void goRight() {
        characterScript.goingRight = true;
    }
    public void goUp() {
        characterScript.goingUp = true;
    }
    public void goDown() {
        characterScript.goingDown = true;
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

    public boolean canHearPlayer() {
        PlayerScript playerScript = player.getComponent(BehaviourComponent.class).get(PlayerScript.class);

        BoardPosition playerPos = player.getComponent(BoardPosition.class);
        BoardPosition pos = parent.getComponent(BoardPosition.class);
        Velocity playerVelocity = player.getComponent(Velocity.class);

        if(playerVelocity.isZero()) return false;

        // Calculate distance
        float dist = distance(playerPos.getVector(), pos.getVector());
        float hearingDistance = 9;
        if(dist < hearingDistance && playerScript.isSneaking() == false) {
            return true;
        }

        return false;
    }

    public boolean canSeePlayer() {
        BoardPosition playerPos = player.getComponent(BoardPosition.class);
        BoardPosition pos = parent.getComponent(BoardPosition.class);

        BoardSystem board = engine.getSystem(BoardSystem.class);

        //Make sure the distance is right
        if(distanceToPlayer() > 5) return false;

        // First condition if is in a straight line of sight
        if(pos.x != playerPos.x && pos.y != playerPos.y) return false;

        // Second condition, not blocked by a wall
        int xv = (int)Math.signum(playerPos.x - pos.x);
        int yv = (int)Math.signum(playerPos.y - pos.y);
        int x = pos.x;
        int y = pos.y;

        while(!(x == playerPos.x && y == playerPos.y)) {
            if(board.getWallAt(x, y) != null) return false;
            x+=xv;
            y+=yv;
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

            //BoardSystem board = engine.getSystem(BoardSystem.class);

            int w = BoardSystem.BOARD_WIDTH;
            int h = BoardSystem.BOARD_HEIGHT;

            Astar astar = new Astar(w, h) {
                @Override
                protected boolean isValid(int x, int y) {
                    return engine.getSystem(BoardSystem.class).getWallAt(x, y) == null;
                }
            };

            IntArray intArray = astar.getPath((int)startPoint.x, (int)startPoint.y, (int)endPoint.x, (int)endPoint.y);

            for(int i = intArray.size-1 ; i >= 0 ; i-=2) {
                n.add(new Vector2(intArray.get(i-1), intArray.get(i)));
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

    /* Aquired from gist.github.com/NathanSweet/7587981 , modified slightly to block diagonal movement*/
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
            open = new BinaryHeap<PathNode>(width * 4, false);
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
            //int i = 0;
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
                }
                if (x > 0) {
                    addNode(node, x - 1, y, 10);
                }
                if (y < lastRow) addNode(node, x, y + 1, 10);
                if (y > 0) addNode(node, x, y - 1, 10);
                //i++;
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

        public static class PathNode extends BinaryHeap.Node {
            int runID, closedID, x, y, pathCost;
            PathNode parent;

            public PathNode (float value) {
                super(value);
            }
        }
    }
}
