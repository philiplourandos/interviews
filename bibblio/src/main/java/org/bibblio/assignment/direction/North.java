package org.bibblio.assignment.direction;

import org.bibblio.assignment.Coordinates;
import org.bibblio.assignment.Direction;

/**
 *
 * @author plourand
 */
public class North implements Direction {

    private Direction east;
    private Direction west;
    
    public North() {
    }

    @Override
    public Direction left() {
        return west;
    }

    @Override
    public Direction right() {
        return east;
    }
    
    public void setDirections(Direction left, Direction right) {
        this.west = left;
        this.east = right;
    }

    @Override
    public Coordinates calc(Coordinates currentPosition) {
        int y = currentPosition.getY();
        y++;

        return new Coordinates(currentPosition.getX(), y);
    }

    @Override
    public String toString() {
        return Direction.N;
    }
}
