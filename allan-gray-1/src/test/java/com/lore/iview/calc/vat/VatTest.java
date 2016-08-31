package com.lore.iview.calc.vat;

import com.lore.iview.calc.ICalc;
import com.lore.iview.model.ItemTotal;
import java.math.BigDecimal;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author lore
 */
public class VatTest {
    
    private static final BigDecimal VAT_RATE = new BigDecimal(14);
    private static final BigDecimal SUCCESS_UNIT_PRICE = new BigDecimal("800");
    private static final String SUCCESS_DESC = "Glenlivet 18 year old";
    private static final String SUCCESS_PRODUCT_ID = "8472";
    private static final BigDecimal EXPECTED_VAT_AMOUNT_1 = new BigDecimal("112");
    private static final BigDecimal EXPECTED_VAT_AMOUNT_2 = new BigDecimal("224");

    private ItemTotal item;
    
    @Before
    public void init() {
        item = new ItemTotal();
        item.setDescription(SUCCESS_DESC);
        item.setProductId(SUCCESS_PRODUCT_ID);
        item.setUnitPrice(SUCCESS_UNIT_PRICE);
    }
    
    @Test
    public void success() {
        item.add();

        ICalc vatCalc = new VatCalc(VAT_RATE);
        vatCalc.calc(item);

        Assert.assertNotNull(item.getVat());
        Assert.assertEquals(EXPECTED_VAT_AMOUNT_1, item.getVat());
        
        item.add();
        
        vatCalc.calc(item);
        Assert.assertNotNull(item.getVat());
        Assert.assertEquals(EXPECTED_VAT_AMOUNT_2, item.getVat());
    }
}
