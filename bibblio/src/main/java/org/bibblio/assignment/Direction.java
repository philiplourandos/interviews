package org.bibblio.assignment;

/**
 *
 * @author plourand
 */
public interface Direction {
    public static final String S = "S";
    public static final String E = "E";
    public static final String W = "W";
    public static final String N = "N";
    
    Direction right();
    Direction left();

    Coordinates calc(Coordinates currentPosition);
}
