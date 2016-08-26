package org.bibblio.assignment;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bibblio.assignment.direction.East;
import org.bibblio.assignment.direction.North;
import org.bibblio.assignment.direction.South;
import org.bibblio.assignment.direction.West;

/**
 *
 * @author plourand
 */
public class Rover {
    private static final Logger LOG = LogManager.getLogger();
    
    private static final String NO_GO_AREA = "X";
    
    public static final String S = "S";
    public static final String E = "E";
    public static final String W = "W";
    public static final String N = "N";

    private static final North NORTH = new North();
    private static final South SOUTH = new South();
    private static final East EAST = new East();
    private static final West WEST = new West();
    
    private static final Map<String, Direction> lookup = new HashMap<>();

    private Direction currentDir;
    
    private String[][] terrain;
    
    private Coordinates position;
    
    static {
        NORTH.setDirections(WEST, EAST);
        SOUTH.setDirections(EAST, WEST);
        EAST.setDirections(NORTH, SOUTH);
        WEST.setDirections(SOUTH, NORTH);

        lookup.put(E, EAST);
        lookup.put(N, NORTH);
        lookup.put(S, SOUTH);
        lookup.put(W, WEST);
    }

    public Rover(String initialOrientation, String[][] terrain, Coordinates start) {
        currentDir = lookup.get(initialOrientation);

        this.terrain = terrain;

        this.position = start;
    }
    
    public boolean executeCommand(String orientation) {
        boolean isGreatSuccess = true;
        
        switch(orientation) {
            case "L" : currentDir = currentDir.left();
                       break;
            case "R":  currentDir = currentDir.right();
                       break;
            case "F":  move();
                       break;
            default:   isGreatSuccess = false;
                       break;
        }

        return isGreatSuccess;
    }

    private boolean move() {
        Coordinates newPos = currentDir.calc(position);
        
        
    }
}
