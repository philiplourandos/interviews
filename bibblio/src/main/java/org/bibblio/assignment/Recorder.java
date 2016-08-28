package org.bibblio.assignment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author plourand
 */
public class Recorder {
    private List<Coordinates> scents;
    
    private int marsLength;
    private int marsBreadth;

    public Recorder(int marsLength, int marsBreadth) {
        this.marsLength = marsLength;
        this.marsBreadth = marsBreadth;
        
        scents = new ArrayList<>();
    }

    public int getMarsBreadth() {
        return marsBreadth;
    }

    public int getMarsLength() {
        return marsLength;
    }
    
    public void addScent(Coordinates newScent) {
        scents.add(newScent);
    }

    public List<Coordinates> getScents() {
        return scents;
    }
}
