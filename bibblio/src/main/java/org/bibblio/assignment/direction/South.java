package org.bibblio.assignment.direction;

import org.bibblio.assignment.Coordinates;
import org.bibblio.assignment.Direction;

/**
 *
 * @author plourand
 */
public class South implements Direction {

    private Direction left;
    private Direction right;
    
    public South() {
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
        int y = currentPosition.getY();
        y--;
        
        return new Coordinates(currentPosition.getX(), y);
    }

    @Override
    public String toString() {
        return Direction.S;
    }
}
