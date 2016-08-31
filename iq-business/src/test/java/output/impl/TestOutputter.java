package output.impl;

import model.Tweet;
import model.TwitterAccount;
import output.IOutputter;

/**
 *
 * @author lore
 */
public class TestOutputter implements IOutputter {

    private StringBuilder output = new StringBuilder(100);
    
    public TestOutputter() {
    }

    @Override
    public void write(Tweet tweet) {
        output.append(String.format("\t@%s: %s", tweet.getOwner(), tweet.getMessage()));
        output.append('\n');
    }

    @Override
    public void write(TwitterAccount user) {
        output.append(user.getName());
        output.append('\n');
    }

    @Override
    public String toString() {
        return output.toString();
    }
    
}
