package com.allangray.biz;

import com.allangray.model.Product;
import java.math.BigDecimal;

/**
 *
 * @author lore
 */
public interface ICalc {
    BigDecimal calc(Product product, Integer total);
}
