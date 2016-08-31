package com.allangray.batch;

import com.allangray.biz.ICalc;
import com.allangray.model.Inventory;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author lore
 */
public class VatCalcTasklet extends TaskletStep {

    private static final Logger LOG = LoggerFactory.getLogger(VatCalcTasklet.class);

    @Value("#{items}")
    private Map<String, Inventory> inventory;

    @Autowired
    private ICalc vatCalc;
    
    public VatCalcTasklet() {
    }

    @Override
    protected void doExecute(StepExecution stepExecution) throws Exception {
        LOG.info("Calculating VAT.");

        Iterator<Map.Entry<String, Inventory>> inventoryItr = inventory.entrySet().iterator();
        while(inventoryItr.hasNext()) {
            final Map.Entry<String, Inventory> entry = inventoryItr.next();
            final Inventory currentInventory= entry.getValue();

            final BigDecimal vatAmount = vatCalc.calc(currentInventory.getProduct(), currentInventory.getTotalItems());
            currentInventory.setVat(vatAmount);
        }
        
        LOG.info("Completed calculating VAT.");
    }
}
