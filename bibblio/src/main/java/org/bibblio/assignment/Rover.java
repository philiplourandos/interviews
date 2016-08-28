package org.bibblio.assignment;

import java.util.HashMap;
import java.util.List;
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
    
    private Coordinates position;
    
    private boolean lost;
    
    private Recorder dataRecorder;

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

    public Rover(String initialOrientation, Recorder dataRecorder, Coordinates start) {
        currentDir = LOOKUP_DIRECTION.get(initialOrientation);

        this.dataRecorder = dataRecorder;
        
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

        int x = newPos.getX();
        int y = newPos.getY();
        
        if (x <= dataRecorder.getMarsLength() && x >= 0 && y <= dataRecorder.getMarsBreadth() && y >= 0) {
            List<Coordinates> scents = dataRecorder.getScents();
            
            long found = scents.stream().filter(s -> s.equals(newPos)).count();
            
            if (found > 0) {
                LOG.debug("Ignoring command");
            } else {
                position = newPos;
            }
        } else {
            lost = true;
            
            dataRecorder.addScent(position);
        }
    }

    @Override
    public String toString() {
        final String lostOutput = this.lost ? "LOST" : "";

        return String.format("%d %d %s %s", position.getX(), position.getY(), currentDir, lostOutput);
    }
}
