package com.lore.iview.output.impl;

import com.lore.iview.IFileProcessor;
import com.lore.iview.model.ItemTotal;
import com.lore.iview.output.IOutput;
import com.lore.iview.proc.AllanGrayFileProcessor;
import java.io.IOException;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author lore
 */
public class LogOutputTest {

    private static final Logger LOG = LoggerFactory.getLogger(LogOutputTest.class);

    @Test
    public void success() {
        try {
            ClassPathResource successResource = new ClassPathResource("invoiceItems.txt");
            Assert.assertNotNull(successResource);

            String successFileContent = new String(FileCopyUtils.copyToByteArray(successResource.getFile()));

            IFileProcessor proc = new AllanGrayFileProcessor();
            Map<String, ItemTotal> items = proc.process(successFileContent);

            IOutput out = new LogOutput();
            out.write(items);
        } catch(IOException io) {
            LOG.error("", io);
            
            Assert.fail();
        } catch(Exception e) {
            LOG.error("", e);

            Assert.fail();
        }
    }
}
