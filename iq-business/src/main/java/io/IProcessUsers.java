package io;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import model.TwitterAccount;

/**
 *
 * @author lore
 */
public interface IProcessUsers {
    Set<TwitterAccount> process(File users) throws IOException;
}
