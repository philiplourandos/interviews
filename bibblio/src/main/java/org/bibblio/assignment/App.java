package org.bibblio.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author plourand
 */
public class App {
    private static final Logger LOG = LogManager.getLogger();
    
    private static final Pattern INVALID_CMDS = Pattern.compile("[^RLF]");
    
    private static final int MAX_MARS_SIZE = 50;
    private static final int MAX_INSTRUCTIONS = 100;

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
            
            if (cmds.length() > MAX_INSTRUCTIONS) {
                throw new RuntimeException("Max instructions exceeded");
            }
            
            Matcher validation = INVALID_CMDS.matcher(cmds);
            boolean invalidsFound = validation.find();
            
            if (invalidsFound) {
                throw new RuntimeException(String.format("Input: %s contains invalid instructions", cmds));
            }
            
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
