package com.allangray.biz.impl;

import com.allangray.biz.ICalc;
import com.allangray.model.Product;
import java.math.BigDecimal;

/**
 *
 * @author lore
 */
public class VatCalc implements ICalc {

    private static final BigDecimal HUNDRED = new BigDecimal(100);

    private BigDecimal vatRate;

    public VatCalc() {
    }

    @Override
    public BigDecimal calc(Product product, Integer total) {
        return product.getUnitPrice().multiply(
                BigDecimal.valueOf(total)).multiply(vatRate).divide(HUNDRED);
    }

    public BigDecimal getVatRate() {
        return vatRate;
    }

    public void setVatRate(BigDecimal vatRate) {
        this.vatRate = vatRate;
    }
}
