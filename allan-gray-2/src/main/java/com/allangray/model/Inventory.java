package com.allangray.model;

import java.math.BigDecimal;

/**
 *
 * @author lore
 */
public class Inventory {
    private Product product;

    private Integer totalItems;

    private BigDecimal vat;
    
    public Inventory() {
        
    }

    public Product getProduct() {
        return product;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public BigDecimal getVat() {
        return vat;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setVat(BigDecimal vat) {
        this.vat = vat;
    }

    public void addItem() {
        totalItems++;
    }
    
    public BigDecimal getTotalAmount() {
        return product.getUnitPrice().multiply(BigDecimal.valueOf(totalItems)).add(vat);
    }
}
