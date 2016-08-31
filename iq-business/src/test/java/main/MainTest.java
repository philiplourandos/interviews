package main;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import output.IOutputter;
import output.impl.TestOutputter;

/**
 *
 * @author lore
 */
public class MainTest {
    private static final Logger LOG = LoggerFactory.getLogger(MainTest.class);

    private static final String EXPECTED_OUTPUT_TWEET_FOR_NON_EXISTANT_USER = "Alan\nMartin\nWard\n";
    
    @Test
    public void success() {
        try {
            ClassPathResource tweetFileRes = new ClassPathResource("valid-tweets.txt");
            ClassPathResource usersFileRes = new ClassPathResource("valid-users.txt");

            Main.main(new String[] {"--tweets", tweetFileRes.getFile().getPath(), 
                "--users" , usersFileRes.getFile().getPath()});
        } catch(IOException ioException) {
            LOG.error("", ioException);
            
            Assert.fail();
        } catch(RuntimeException re) {
            LOG.error("", re);
            
            Assert.fail();
        }
    }

    @Test(expected = RuntimeException.class)
    public void failNonExistantTweetFile() {
        try {
            ClassPathResource usersFileRes = new ClassPathResource("valid-users.txt");

            Main.main(new String[] {"--tweets", "non-existant-tweet-file.txt", 
                "--users" , usersFileRes.getFile().getPath()});
            
            Assert.fail();
        } catch(IOException ioException) {
            LOG.error("", ioException);

            Assert.fail();
        }
    }

    @Test(expected = RuntimeException.class)
    public void failNonExistantUserFile() {
        try {
            ClassPathResource tweetFileRes = new ClassPathResource("valid-tweets.txt");

            Main.main(new String[] {"--tweets", tweetFileRes.getFile().getPath(), 
                "--users" , "non-existant-user-file.txt"});

            Assert.fail();
        } catch(IOException ioException) {
            LOG.error("", ioException);

            Assert.fail();
        }
    }
    
    @Test(expected = RuntimeException.class)
    public void failMalformedTweetFile() {
        try {
            ClassPathResource tweetFileRes = new ClassPathResource("malformed-tweet-file.txt");
            ClassPathResource usersFileRes = new ClassPathResource("valid-users.txt");

            Main.main(new String[] {"--tweets", tweetFileRes.getFile().getPath(), 
                "--users" , usersFileRes.getFile().getPath()});

            Assert.fail();
        } catch(IOException ioException) {
            LOG.error("", ioException);
            
            Assert.fail();
        }
    }
    
    @Test(expected = RuntimeException.class)
    public void failMalformedUserFile() {
        try {
            ClassPathResource tweetFileRes = new ClassPathResource("valid-tweets.txt");
            ClassPathResource usersFileRes = new ClassPathResource("malformed-user-file.txt");

            Main.main(new String[] {"--tweets", tweetFileRes.getFile().getPath(), 
                "--users" , usersFileRes.getFile().getPath()});

            Assert.fail();
        } catch(IOException ioException) {
            LOG.error("", ioException);
            
            Assert.fail();
        }
    }

    @Test
    public void failInvalidEncodingFormat() {
        try {
            ClassPathResource tweetFileRes = new ClassPathResource("tweets-with-non-existant-user.txt");
            ClassPathResource usersFileRes = new ClassPathResource("valid-users.txt");

            IOutputter capturer = new TestOutputter();
            Main.out = capturer;

            Main.main(new String[] {"--tweets", tweetFileRes.getFile().getPath(), 
                "--users" , usersFileRes.getFile().getPath()});

            Assert.assertEquals(EXPECTED_OUTPUT_TWEET_FOR_NON_EXISTANT_USER, capturer.toString());
        } catch(IOException ioException) {
            LOG.error("", ioException);
            
            Assert.fail();
        }
    }
    
    @Test(expected = RuntimeException.class)
    public void failInvalidFileEncoding() {
        try {
            ClassPathResource tweetFileRes = new ClassPathResource("invalid-file-encoding.txt");
            ClassPathResource usersFileRes = new ClassPathResource("valid-users.txt");

            IOutputter capturer = new TestOutputter();
            Main.out = capturer;

            Main.main(new String[] {"--tweets", tweetFileRes.getFile().getPath(), 
                "--users" , usersFileRes.getFile().getPath()});

            Assert.fail();
        } catch(IOException ioException) {
            LOG.error("", ioException);
            
            Assert.fail();
        }
    }
}
