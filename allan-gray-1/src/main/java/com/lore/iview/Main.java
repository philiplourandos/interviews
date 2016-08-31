package com.lore.iview;

import com.lore.iview.calc.ICalc;
import com.lore.iview.calc.vat.VatCalc;
import com.lore.iview.model.ItemTotal;
import com.lore.iview.output.IOutput;
import com.lore.iview.output.impl.LogOutput;
import com.lore.iview.proc.AllanGrayFileProcessor;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

/**
 *
 * @author lore
 */
public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private static final BigDecimal VAT_PERCENTAGE = new BigDecimal(14);
    private static final ICalc VAT_CALC = new VatCalc(VAT_PERCENTAGE);
    private static final IOutput OUTPUTTER = new LogOutput();

    private final String[] files;

    public Main(String[] files) {
        this.files = files;
    }

    public void run() throws RuntimeException {
        for(String currentFile : files) {
            File inputFile = new File(currentFile);
            if(!inputFile.exists()) {
                LOG.error("Specified file: '{}' does not exist", currentFile);

                throw new RuntimeException();
            }

            String fileContent = null;
            try {
                fileContent = new String(FileCopyUtils.copyToByteArray(inputFile));
            } catch (IOException ioException) {
                LOG.error("Unable to read from file: {}", currentFile);

                throw new RuntimeException(ioException);
            }

            if(StringUtils.isEmpty(fileContent)) {
                LOG.error("File: {} has not content in it.", currentFile);

                throw new RuntimeException();
            }

            LOG.info("Processing file: {}", currentFile);
            IFileProcessor processor = new AllanGrayFileProcessor();
            Map<String, ItemTotal> items = processor.process(fileContent);

            LOG.info("Calculating VAT.");
            Iterator<Map.Entry<String, ItemTotal>> itemsItr = items.entrySet().iterator();
            while(itemsItr.hasNext()) {
                final Map.Entry<String, ItemTotal> currentEntry = itemsItr.next();
                
                final ItemTotal currentItem = currentEntry.getValue();
                VAT_CALC.calc(currentItem);
            }

            OUTPUTTER.write(items);
        }
    }

    public static void main(String[] args) {
        Main app = new Main(args);
        app.run();
    }
}
