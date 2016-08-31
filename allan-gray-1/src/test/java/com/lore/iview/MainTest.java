package com.lore.iview;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author lore
 */
public class MainTest {

    private static final Logger LOG = LoggerFactory.getLogger(MainTest.class);

    @Test
    public void success() {
        try {
            ClassPathResource inputFile = new ClassPathResource("invoiceItems.txt");

            Main.main(new String[]{inputFile.getFile().getPath()});
        } catch (IOException io) {
            LOG.error("", io);
            
            Assert.fail();
        }
    }
    
    @Test
    public void failInvalidFile() {
        try {
            Main.main(new String[]{"invalid.txt"});

            Assert.fail();
        } catch(RuntimeException runtime) {
            //Expected
        }
    }

    @Test
    public void failEmptyFile() {
        try {
            ClassPathResource inputFile = new ClassPathResource("nocontent.txt");

            Main.main(new String[]{inputFile.getFile().getPath()});
        } catch(IOException io) {
            LOG.error("", io);
            
            Assert.fail();
        } catch(RuntimeException runtime) {
            // Expected
        }
    }
}
