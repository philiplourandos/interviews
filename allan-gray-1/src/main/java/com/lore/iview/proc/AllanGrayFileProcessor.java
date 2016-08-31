package com.lore.iview.proc;

import com.lore.iview.IFileProcessor;
import com.lore.iview.model.ItemTotal;
import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author lore
 */
public class AllanGrayFileProcessor implements IFileProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(AllanGrayFileProcessor.class);
    
    private static final int INDEX_ID = 0;
    private static final int INDEX_UNIT_PRICE = 1;
    private static final int INDEX_DESCRIPTION = 2;
    
    public AllanGrayFileProcessor() {
        
    }
    
    @Override
    public Map<String, ItemTotal> process(String content) {
        LOG.info("Processing file contents.");

        Map<String, ItemTotal> processed = new TreeMap<String, ItemTotal>();

        String[] lines = content.split("\n");
        LOG.info("{} lines to process", lines.length);

        for(int currentRowIndex = 1; currentRowIndex < lines.length; currentRowIndex++) {
            String[] values = lines[currentRowIndex].split(",");
            
            ItemTotal total = null;
            
            String productId = values[INDEX_ID];
            
            if(processed.containsKey(productId)) {
                total = processed.get(productId);
            } else {
                total = new ItemTotal();
                total.setUnitPrice(new BigDecimal(values[INDEX_UNIT_PRICE].trim()));
                total.setDescription(values[INDEX_DESCRIPTION].trim());
                total.setProductId(productId);

                processed.put(productId, total);

                LOG.info("New entry added for product id: {}", productId);
            }

            total.add();
        }

        return processed;
    }
}
