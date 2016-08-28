package org.bibblio.assignment;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author plourand
 */
@RunWith(JUnit4.class)
public class RoverTest {
    
    private static final Logger LOG = LogManager.getLogger();
    
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

            for(String currentInstr : instructions) {
                rover.executeInstruction(currentInstr);
            }

            LOG.info("{}", rover);

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
        App.main(new String[]{"30 20", "3 2 E", "FFLLRRRRRRLLLLFFFFFFFFFFLLRRLRFFFLLRRRRFFRFFFFLLLLLLLRRRRFFFFFFFFFRRRRLLLLLRRRRRRLLLLRRRFFFFFFFFFFLLLL"});
    }
}

