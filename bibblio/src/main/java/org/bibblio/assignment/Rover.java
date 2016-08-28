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
    private static final String INSTRUCTION_FORWARD = "F";
    private static final String INSTRUCTION_RIGHT = "R";
    private static final String INSTRUCTION_LEFT = "L";

    private static final North NORTH = new North();
    private static final South SOUTH = new South();
    private static final East EAST = new East();
    private static final West WEST = new West();
    
    private static final Map<String, Direction> LOOKUP_DIRECTION = new HashMap<>();

    private Direction currentDir;
    
    private String[][] terrain;
    
    private Coordinates position;
    
    private boolean lost;
    
    static {
        NORTH.setDirections(WEST, EAST);
        SOUTH.setDirections(EAST, WEST);
        EAST.setDirections(NORTH, SOUTH);
        WEST.setDirections(SOUTH, NORTH);

        LOOKUP_DIRECTION.put(Direction.E, EAST);
        LOOKUP_DIRECTION.put(Direction.N, NORTH);
        LOOKUP_DIRECTION.put(Direction.S, SOUTH);
        LOOKUP_DIRECTION.put(Direction.W, WEST);
    }

    public Rover(String initialOrientation, String[][] terrain, Coordinates start) {
        currentDir = LOOKUP_DIRECTION.get(initialOrientation);

        this.terrain = terrain;

        this.position = start;
    }
    
    public void executeInstruction(String orientation) {
        if (!lost) {
            switch(orientation) {
                case INSTRUCTION_LEFT : 
                            currentDir = currentDir.left();
                            break;
                case INSTRUCTION_RIGHT:  
                            currentDir = currentDir.right();
                            break;
                case INSTRUCTION_FORWARD:  
                            move();
                            break;
            }
        }
    }

    private void move() {
        Coordinates newPos = currentDir.calc(position);

        int terrainX = newPos.getX();
        int terrainY = (terrain[terrainX].length - 1)- newPos.getY();

        System.out.println("terrain X: " + terrainX);
        System.out.println("terrain Y: " + terrainY);
        
        if ((terrainX < 0 || terrainX >= terrain.length) || (terrainY < 0 || terrainY >= terrain[0].length)) {
            int currentX = position.getX();
            int currentY = terrain[currentX].length - position.getY();

            terrain[currentX][currentY] = NO_GO_AREA;

            lost = true;

            LOG.trace("New position invalid");
        } else if (terrain[terrainX][terrainY] != null && terrain[terrainX][terrainY].equals(NO_GO_AREA)) {
            LOG.trace("Scent detected, ignoring");
        } else {
            LOG.trace("Moving to new grid co-ordinate - X: {}, Y: {}", newPos.getX(), newPos.getY());
            
            position = newPos;
        }
    }
    
    @Override
    public String toString() {
        final String lost = this.lost ? "LOST" : "";

        return String.format("%d %d %s %s", position.getX(), position.getY(), currentDir, lost);
    }
}
