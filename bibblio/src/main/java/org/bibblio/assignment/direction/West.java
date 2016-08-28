package org.bibblio.assignment.direction;

import org.bibblio.assignment.Coordinates;
import org.bibblio.assignment.Direction;

/**
 *
 * @author plourand
 */
public class West implements Direction {

    private Direction left;
    private Direction right;
    
    public West() {
    }

    @Override
    public Direction left() {
        return left;
    }

    @Override
    public Direction right() {
        return right;
    }
    
    public void setDirections(Direction left, Direction right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Coordinates calc(Coordinates currentPosition) {
        int x = currentPosition.getX();

        return new Coordinates(--x, currentPosition.getY());
    }

    @Override
    public String toString() {
        return Direction.W;
    }
}
