package io.impl;

import io.IProcessUsers;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import main.Const;
import model.TwitterAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author lore
 */
public class ProcessUsers implements IProcessUsers {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessUsers.class);
    
    private static final String KEYWORD_FOLLOWS = "follows";
    
    public ProcessUsers() {
    }

    /**
     *
     * @param users
     * @return
     * @throws IOException
     */
    @Override
    public Set<TwitterAccount> process(File users) throws IOException {
        Map<String, TwitterAccount> tmpAccs = new HashMap<>();

        InputStreamReader isReader = new InputStreamReader(
                new FileInputStream(users), 
                Charset.forName(Const.FILE_ENCODING_TYPE));
        try (LineNumberReader reader = new LineNumberReader(isReader)) {
            String line;
            
            LOG.info("Starting to process twitter user file: {}", users.getPath());
            
            while((line = reader.readLine()) != null) {
                int keywordIdx = line.indexOf(KEYWORD_FOLLOWS);
                if (keywordIdx<0) {
                    throw new RuntimeException(String.format("Failed processing user file at line: %d (syntax error)", reader.getLineNumber()));
                }
                String user = line.substring(0, keywordIdx).trim();
                
                if(line.contains(KEYWORD_FOLLOWS)) {
                    String followerCheck = line.substring(keywordIdx + KEYWORD_FOLLOWS.length()).trim();
                    
                    if(followerCheck == null || followerCheck.isEmpty()) {
                        throw new RuntimeException(String.format("Failed processing user file at line: %d (syntax error)", reader.getLineNumber()));
                    }
                }
                String[] rawFollowers = line.substring(keywordIdx + KEYWORD_FOLLOWS.length()).split(",");
                
                TwitterAccount acc;
                
                if(!tmpAccs.containsKey(user)) {
                    acc = new TwitterAccount();
                    acc.setName(user);
                    acc.setFollowers(new HashSet<String>());
                    
                    tmpAccs.put(user, acc);
                } else {
                    acc = tmpAccs.get(user);
                }
                
                Set<String> followers = acc.getFollowers();
                for(String currentFollower : rawFollowers) {
                    String follower = currentFollower.trim();
                    followers.add(follower);
                    
                    TwitterAccount followerAcc;
                    if(!tmpAccs.containsKey(follower)) {
                        followerAcc = new TwitterAccount();
                        followerAcc.setName(follower);
                        followerAcc.setFollowers(new HashSet<String>());
                        
                        tmpAccs.put(follower, followerAcc);
                    }
                }
            }
        }

        LOG.info(String.format("%d users created.", tmpAccs.size()));

        Set<TwitterAccount> accounts = new TreeSet<>();
        accounts.addAll(tmpAccs.values());

        return accounts;
    }    

}
