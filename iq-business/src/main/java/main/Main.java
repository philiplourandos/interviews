package main;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import io.IProcessTweets;
import io.IProcessUsers;
import io.impl.ProcessTweets;
import io.impl.ProcessUsers;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;
import model.Tweet;
import model.TwitterAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import output.IOutputter;
import output.impl.SysOutputter;

/**
 *
 * @author lore
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final String ASCII = "ASCII";

    @Parameter(names = "--users", description = "File containing twitter users.", required = true)
    private String twitterUsersFile;
    @Parameter(names = "--tweets", description = "File containing tweets.", required = true)
    private String tweetsFile;

    @Parameter(names = "--help", help = true)
    private static boolean help;

    public static IOutputter out = new SysOutputter();

    public Main() {
    }

    private void validate() throws RuntimeException {
        File users = new File(twitterUsersFile);
        if(!users.exists()) {
            throw new RuntimeException(String.format("Unable to find twitter user file: %s", twitterUsersFile));
        }

        File tweets = new File(tweetsFile);
        if(!tweets.exists()) {
            throw new RuntimeException(String.format("Unable to find tweets file: %s", tweetsFile));
        }
    }
    
    public void execute() {
        List<Tweet> tweets = null;
        Set<TwitterAccount> users = null;

        try {
            IProcessTweets tweetProc = new ProcessTweets();
            tweets = tweetProc.process(new File(tweetsFile));

            IProcessUsers usersProc = new ProcessUsers();
            users = usersProc.process(new File(twitterUsersFile));

            for(TwitterAccount currentUser : users) {
                out.write(currentUser);

                for(Tweet currentTweet : tweets) {
                    final String owner = currentTweet.getOwner();

                    if(currentUser.getName().equals(owner) || currentUser.getFollowers().contains(owner)) {
                        out.write(currentTweet);
                    }
                }
            }
        } catch(IOException io) {
            LOG.error("Unable to process file correctly.");
        }
    }
    
    public static final void main(String[] args) {
        Main main = new Main();

        JCommander cmd = new JCommander(main, args);

        if(help) {
            cmd.usage();
        } else {
            try {
                main.validate();
                main.execute();
            } catch(RuntimeException exception) {
                LOG.error("", exception);

                throw exception;
            }
        }
    }
}
