package output.impl;

import model.Tweet;
import model.TwitterAccount;
import output.IOutputter;

/**
 *
 * @author lore
 */
public class SysOutputter implements IOutputter {

    public SysOutputter() {
    }

    @Override
    public void write(Tweet tweet) {
        System.out.println(String.format("\t@%s: %s", tweet.getOwner(), tweet.getMessage()));
    }

    @Override
    public void write(TwitterAccount user) {
        System.out.println(user.getName());
    }
}
