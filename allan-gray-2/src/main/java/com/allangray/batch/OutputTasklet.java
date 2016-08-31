package com.allangray.batch;

import com.allangray.model.Inventory;
import com.allangray.model.Product;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;

/**
 *
 * @author lore
 */
public class OutputTasklet extends TaskletStep {

    private static final Logger LOG = LoggerFactory.getLogger(OutputTasklet.class);
    
    private static final String MSG_HEADING_KEY = "header";
    private static final String MSG_TOTAL_KEY = "total";
    private static final String[] NO_ARGS = new String[]{};
    
    @Value("#{msgs}")
    private MessageSource resourceBundle;
    
    @Value("#{items}")
    private Map inventory;
    
    public OutputTasklet() {
    }

    @Override
    protected void doExecute(StepExecution stepExecution) throws Exception {
        LOG.info("{}\n", resourceBundle.getMessage(MSG_HEADING_KEY, NO_ARGS, Locale.getDefault()));
        
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        
        Iterator<Map.Entry<String, Inventory>> inventoryIt = inventory.entrySet().iterator();
        while(inventoryIt.hasNext()) {
            final Map.Entry<String, Inventory> entry = inventoryIt.next();
            final Inventory current = entry.getValue();
            final Product product = current.getProduct();

            LOG.info("{}, {}, {}, {}, {}\n", product.getId(), product.getDecription(),
                    current.getTotalItems(), formatter.format(product.getUnitPrice()), 
                    formatter.format(current.getVat()), formatter.format(current.getTotalAmount()));
        }

        LOG.info("{},\n", resourceBundle.getMessage(MSG_TOTAL_KEY, NO_ARGS, Locale.getDefault()));
    }    
}
