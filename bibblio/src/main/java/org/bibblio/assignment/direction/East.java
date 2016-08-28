package org.bibblio.assignment.direction;

import org.bibblio.assignment.Coordinates;
import org.bibblio.assignment.Direction;

/**
 *
 * @author plourand
 */
public class East implements Direction {

    private Direction north;
    private Direction south;

    public East() {
    }

    @Override
    public Direction left() {
        return north;
    }

    @Override
    public Direction right() {
        return south;
    }
    
    public void setDirections(Direction left, Direction right) {
        this.south = right;
        this.north = left;
    }

    @Override
    public Coordinates calc(Coordinates currentPosition) {
        int x = currentPosition.getX();

        return new Coordinates(++x, currentPosition.getY());
    }

    @Override
    public String toString() {
        return Direction.E;
    }
}
