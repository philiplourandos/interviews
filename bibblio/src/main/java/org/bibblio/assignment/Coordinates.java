package org.bibblio.assignment;

/**
 *
 * @author plourand
 */
public class Coordinates {
    private int x;
    private int y;

    public Coordinates() {
    }

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (getClass().isAssignableFrom(obj.getClass())) {
            Coordinates comp = (Coordinates) obj;

            return comp.x == this.x && comp.y == this.y;
        } else {
            return false;
        }
    }
}