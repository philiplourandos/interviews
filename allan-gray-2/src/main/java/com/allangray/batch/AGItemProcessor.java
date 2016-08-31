package com.allangray.batch;

import com.allangray.model.Inventory;
import com.allangray.model.Product;
import java.util.Map;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;


/**
 *
 * @author lore
 */
public class AGItemProcessor implements ItemProcessor<Product, Product> {

    @Value("#{items}")
    private Map<String, Inventory> items;
    
    public AGItemProcessor() {
    }

    @Override
    public Product process(Product product) throws Exception {
        Inventory inventory = null;

        if(!items.containsKey(product.getId())) {
            inventory = new Inventory();
            inventory.setProduct(product);

            items.put(product.getId(), inventory);
        } else {
            inventory = (Inventory) items.get(product.getId());
        }

        inventory.addItem();

        return product;
    }

    public void setItems(Map items) {
        this.items = items;
    }
}
