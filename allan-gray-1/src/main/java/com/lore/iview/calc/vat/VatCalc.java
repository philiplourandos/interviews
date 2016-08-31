package com.lore.iview.calc.vat;

import com.lore.iview.calc.ICalc;
import com.lore.iview.model.ItemTotal;
import java.math.BigDecimal;

/**
 *
 * @author lore
 */
public class VatCalc implements ICalc {

    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100L);

    private final BigDecimal vatPercentage;

    public VatCalc(BigDecimal vatPercentage) {
        this.vatPercentage = vatPercentage;
    }

    @Override
    public void calc(ItemTotal item) {
        Integer itemCount = item.getCount();
        BigDecimal unitPrice = item.getUnitPrice();
        BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(itemCount));
        BigDecimal vatAmount = totalPrice.multiply(vatPercentage).divide(HUNDRED);

        item.setVat(vatAmount);
    }
}
