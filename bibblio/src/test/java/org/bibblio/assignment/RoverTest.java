package org.bibblio.assignment;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import org.junit.internal.runners.JUnit4ClassRunner;

/**
 *
 * @author plourand
 */
@RunWith(JUnit4ClassRunner.class)
public class RoverTest {
    
    private final Recorder roverRecorder = new Recorder(5, 3);
    
    private static final RoverCommand[] SAMPLE_INPUTS = new RoverCommand[]{
        new RoverCommand("1 1 E", "RFRFRFRF"),
        new RoverCommand("3 2 N", "FRRFLLFFRRFLL"),
        new RoverCommand("0 3 W", "LLFFFLFLFL")
    };
    
    private static final String[] EXPECTED_OUTPUTS = {
        "1 1 E",
        "3 3 N LOST",
        "2 3 S"
    };

    @Test
    public void sampleInput() {
        for(int index = 0; index < EXPECTED_OUTPUTS.length; index++) {
            final RoverCommand cmd = SAMPLE_INPUTS[index];
            List<String> instructions = cmd.getInstructions();

            final Rover rover = new Rover(cmd.getOrientation(), roverRecorder, cmd.getStartCoordintes());

            System.out.println(rover);
            
            for(String currentInstr : instructions) {
                System.out.println(currentInstr);

                rover.executeInstruction(currentInstr);

                System.out.println(rover);
            }

            System.out.println("---------------------------------------------");
            
            assertEquals(EXPECTED_OUTPUTS[index], rover.toString().trim());
        }
    }
    
    @Test(expected = RuntimeException.class)
    public void testInvalidMarsSize() {
        App.main(new String[]{"50 60", "3 2 E", "FFFFFFFF"});
    }
    
    @Test(expected = RuntimeException.class)
    public void invalidInstructions() {
        App.main(new String[]{"4 5", "3 2 E", "dFFFLLRR"});
    }
    
    @Test(expected = RuntimeException.class)
    public void tooManyInstructions() {
        App.main(new String[]{"30 20", "3 2 E", "FFLLRRRRRRLLLLFFFFFFFFFFLLRRLRFFFLLRRRRRFFFFLLLLLLLRRRRFFFFFFFFFRRRRLLLLLRRRRRRLLLLRRRFFFFFFFFFFLLLL"});
    }
}

