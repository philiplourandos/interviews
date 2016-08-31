package com.lore.iview.output.impl;

import com.lore.iview.model.ItemTotal;
import com.lore.iview.output.IOutput;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author lore
 */
public class LogOutput implements IOutput {

    private static final Logger LOG = LoggerFactory.getLogger(LogOutput.class);

    private static final ResourceBundle I18N = ResourceBundle.getBundle("com.lore.iview.output.impl.sysout", Locale.getDefault());

    public LogOutput() {
    }

    @Override
    public void write(Map<String, ItemTotal> items) {
        LOG.info(I18N.getString(IOutput.HEADER));

        BigDecimal finalAmount = BigDecimal.ZERO;

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        
        Iterator<Map.Entry<String, ItemTotal>> itemsItr = items.entrySet().iterator();
        while(itemsItr.hasNext()) {
            final Map.Entry<String, ItemTotal> currentEntry = itemsItr.next();
            final ItemTotal item = currentEntry.getValue();

            finalAmount = finalAmount.add(item.getTotalAmount());

            LOG.info("{},{},{},{},{},{}", item.getProductId(), 
                    item.getDescription(), item.getCount(), formatter.format(item.getUnitPrice()), 
                    formatter.format(item.getVat()), formatter.format(item.getTotalAmount()));
        }

        LOG.info("{},,,,,,,{}", I18N.getString(IOutput.TOTAL), formatter.format(finalAmount));
        LOG.info("");
    }
}
