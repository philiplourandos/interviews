/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package io;

import java.io.File;
import java.io.IOException;
import java.util.List;
import model.Tweet;

/**
 *
 * @author lore
 */
public interface IProcessTweets {
    List<Tweet> process(File tweets) throws IOException;
}
