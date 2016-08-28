package org.bibblio.assignment;

import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author plourand
 */
public class App {
    private static final Logger LOG = LogManager.getLogger();
    
    private static final int MAX_MARS_SIZE = 50;

    public static void main(String[] args) {
        String[] marsDimensions = args[0].split(" ");
        int length = Integer.valueOf(marsDimensions[0]);
        int breadth = Integer.valueOf(marsDimensions[1]);

        if (length > MAX_MARS_SIZE || breadth > MAX_MARS_SIZE) {
            LOG.error("The map size exceeds Mar's real estate");

            throw new RuntimeException("The map size exceeds Mar's real estate");
        }

        String[][] terrain = new String[length][breadth];
        
        List<RoverCommand> commands = new ArrayList<>();
        
        for(int index = 1; index < args.length; index++) {
            final String initialPositioning = args[index];
            final String cmds = args[++index];
            
            commands.add(new RoverCommand(initialPositioning, cmds));
        }
        
        for(RoverCommand currentCmd : commands) {
            final Rover curiocity = new Rover(currentCmd.getOrientation(), terrain, currentCmd.getStartCoordintes());
            
            final List<String> roverInstructions = currentCmd.getInstructions();
            
            for(String currentInstr : roverInstructions) {
                curiocity.executeInstruction(currentInstr);
            }
            
            LOG.info("{}", curiocity);
        }
    }
}
