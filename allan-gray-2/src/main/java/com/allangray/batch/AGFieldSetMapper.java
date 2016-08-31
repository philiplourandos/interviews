package com.allangray.batch;

import com.allangray.model.Product;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

/**
 *
 * @author lore
 */
public class AGFieldSetMapper implements FieldSetMapper<Product> {

    private static final Logger LOG = LoggerFactory.getLogger(AGFieldSetMapper.class);

    public static final String ID = "id";
    public static final String DESC = "description";
    public static final String PRICE = "unitPrice";

    public AGFieldSetMapper() {
    }

    @Override
    public Product mapFieldSet(FieldSet fields) throws BindException {
        Product product = new Product();
        product.setDecription(fields.readString(DESC));
        product.setId(fields.readString(ID));
        product.setUnitPrice(fields.readBigDecimal(PRICE));

        if(StringUtils.isEmpty(product.getId())) {
            LOG.error("File is malformed. A row has no product id.");

            throw new BindException(product, ID);
        } else if (StringUtils.isEmpty(product.getDecription())) {
            LOG.error("File is malformed. A row does not have a description.");

            throw new BindException(product, DESC);
        } else if (product.getUnitPrice() == null) {
            LOG.error("Fail is malformed. A row does not have a unit price.");

            throw new BindException(product, PRICE);
        }
        
        return product;
    }
}
