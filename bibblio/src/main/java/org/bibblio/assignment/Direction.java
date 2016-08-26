package org.bibblio.assignment;

/**
 *
 * @author plourand
 */
public interface Direction {
    Direction right();
    Direction left();

    Coordinates calc(Coordinates currentPosition);
}
