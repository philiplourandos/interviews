package io.impl;

import io.IProcessTweets;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import main.Const;
import model.Tweet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author lore
 */
public class ProcessTweets implements IProcessTweets {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessTweets.class);
    
    private static final String SEPARATOR = "> ";

    public ProcessTweets() {
    }

    /**
     *
     * @param tweetsFile
     * @return
     * @throws IOException
     */
    @Override
    public List<Tweet> process(File tweetsFile) throws IOException {
        List<Tweet> tweets = new ArrayList<>(400);

        try (InputStreamReader isReader = new InputStreamReader(
                new FileInputStream(tweetsFile),
                Charset.forName(Const.FILE_ENCODING_TYPE))) {
            
            try (LineNumberReader reader = new LineNumberReader(isReader)) {
                String line;
                
                LOG.info("Starting to process tweet file: {}", tweetsFile.getPath());
                
                while((line = reader.readLine()) != null) {
                    final String[] values = line.split(SEPARATOR);
                    final String user = values[0];
                    final String message = values[1];
                    
                    Tweet tweet = new Tweet(user, message);
                    tweets.add(tweet);
                }
            }
            
            LOG.info("Completed processing tweet file.");
            
            isReader.close();
        }
        
        return tweets;
    }
}
