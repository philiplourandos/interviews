package org.bibblio.assignment.direction;

import org.bibblio.assignment.Coordinates;
import org.bibblio.assignment.Direction;

/**
 *
 * @author plourand
 */
public class West implements Direction {

    private Direction north;
    private Direction south;
    
    public West() {
    }

    @Override
    public Direction left() {
        return south;
    }

    @Override
    public Direction right() {
        return north;
    }
    
    public void setDirections(Direction left, Direction right) {
        this.north = right;
        this.south = left;
    }

    @Override
    public Coordinates calc(Coordinates currentPosition) {
        int x = currentPosition.getX();

        return new Coordinates(--x, currentPosition.getY());
    }
}
