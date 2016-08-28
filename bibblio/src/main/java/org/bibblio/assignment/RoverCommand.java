package org.bibblio.assignment;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author plourand
 */
public class RoverCommand {

    private static final int INDEX_X_COORD = 0;
    private static final int INDEX_Y_COORD = 1;
    private static final int INDEX_ORIENTATION = 2;
    private static final int POSITION_VALUES_COUNT = 3;
    
    
    private Coordinates startCoordinates;
    private String orientation;
    private List<String> roverInstructions;
    
    public RoverCommand(String position, String instructions) {
        String[] positionVals = position.split(" ");
        
        if (positionVals.length != POSITION_VALUES_COUNT) {
            throw new RuntimeException("Invalid input for starting position of rover");
        }
        startCoordinates = new Coordinates(Integer.valueOf(positionVals[INDEX_X_COORD]), Integer.valueOf(positionVals[INDEX_Y_COORD]));
        this.orientation = positionVals[INDEX_ORIENTATION];
        roverInstructions = instructions.chars().mapToObj(val -> new Character((char) val).toString()).collect(Collectors.toList());
    }
    
    public Coordinates getStartCoordintes() {
        return startCoordinates;
    }

    public String getOrientation() {
        return orientation;
    }

    public List<String> getInstructions() {
        return roverInstructions;
    }

}
