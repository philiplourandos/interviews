package com.lore.iview.model;

import java.math.BigDecimal;

/**
 *
 * @author lore
 */
public class ItemTotal {
    private String productId;
    private String description;
    private Integer count;
    private BigDecimal unitPrice;
    private BigDecimal vat = BigDecimal.ZERO;

    public ItemTotal() {
    }

    public ItemTotal(Integer count, BigDecimal unitPrice) {
        this.count = count;
        this.unitPrice = unitPrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCount() {
        return count;
    }

    /**
     * Add 1 to the number of items found in input.
     */
    public void add() {
        if(count == null) {
            count = new Integer(0);
        }

        count++;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    } 

    public BigDecimal getTotalAmount() {
        return unitPrice.multiply(BigDecimal.valueOf(count)).add(vat);
    }
}
