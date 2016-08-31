package output;

import model.Tweet;
import model.TwitterAccount;

/**
 *
 * @author lore
 */
public interface IOutputter {
    /**
     * 
     * @param user The user we are generating the stream for.
     */
    void write(TwitterAccount user);
    
    /**
     * 
     * @param tweet Tweet to write out
     */
    void write(Tweet tweet);
}
