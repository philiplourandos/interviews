package com.lore.iview.model;

import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author lore
 */
public class TotalCalcTest {
    private static final BigDecimal SUCCESS_UNIT_PRICE = new BigDecimal("80");
    private static final BigDecimal SUCCESS_VAT = new BigDecimal("22.40");
    private static final BigDecimal EXPECTED_TOTAL = new BigDecimal("182.40");
    
    @Test
    public void success() {
        ItemTotal item = new ItemTotal();
        item.setUnitPrice(SUCCESS_UNIT_PRICE);
        item.setVat(SUCCESS_VAT);
        item.add();
        item.add();
        
        Assert.assertEquals(EXPECTED_TOTAL, item.getTotalAmount());
    }
}
