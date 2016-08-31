package com.lore.iview.proc;

import com.lore.iview.IFileProcessor;
import com.lore.iview.model.ItemTotal;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

/**
 *
 * @author lore
 */
public class AllanGrayProcTest {
    private static final Logger LOG = LoggerFactory.getLogger(AllanGrayProcTest.class);

    private static final String PRODUCT_ID_1 = "1";
    private static final String PRODUCT_ID_2 = "2";
    private static final String PRODUCT_ID_3 = "3";
    private static final String PRODUCT_ID_4 = "4";
    private static final String PRODUCT_ID_5 = "5";
    
    private static final String[] EXPECTED_KEYS = {PRODUCT_ID_1, PRODUCT_ID_2, 
        PRODUCT_ID_3, PRODUCT_ID_4, PRODUCT_ID_5};
    private static final BigDecimal[] EXPECTED_TOTALS_SANS_VAT = {new BigDecimal("160"),
        new BigDecimal("20"), new BigDecimal("50"), new BigDecimal("40"),
        new BigDecimal("37.0")};
    private static final int EXPECTED_ITEMS_COUNT = EXPECTED_KEYS.length;

    private static final Map<String, ItemTotal> EXPECTED_ITEMS = new HashMap<String, ItemTotal>();
    static {
        EXPECTED_ITEMS.put(PRODUCT_ID_1, new ItemTotal(2, new BigDecimal("80")));
        EXPECTED_ITEMS.put(PRODUCT_ID_2, new ItemTotal(4, new BigDecimal("5")));
        EXPECTED_ITEMS.put(PRODUCT_ID_3, new ItemTotal(5, new BigDecimal("10")));
        EXPECTED_ITEMS.put(PRODUCT_ID_4, new ItemTotal(2, new BigDecimal("20")));
        EXPECTED_ITEMS.put(PRODUCT_ID_5, new ItemTotal(2, new BigDecimal("18.5")));
    }
    
    @Test
    public void success() {
        try {
            ClassPathResource successResource = new ClassPathResource("invoiceItems.txt");
            Assert.assertNotNull(successResource);

            String successFileContent = new String(FileCopyUtils.copyToByteArray(successResource.getFile()));

            IFileProcessor proc = new AllanGrayFileProcessor();
            Map<String, ItemTotal> items = proc.process(successFileContent);

            Assert.assertEquals(EXPECTED_ITEMS_COUNT, items.size());

            for(int index = 0; index < EXPECTED_KEYS.length; index++) {
                Assert.assertTrue(items.containsKey(EXPECTED_KEYS[index]));
                
                final ItemTotal processedItem = items.get(EXPECTED_KEYS[index]);
                final ItemTotal expectedItem = EXPECTED_ITEMS.get(EXPECTED_KEYS[index]);

                Assert.assertEquals(expectedItem.getCount(), processedItem.getCount());
                Assert.assertEquals(expectedItem.getUnitPrice(), processedItem.getUnitPrice());
                Assert.assertEquals(EXPECTED_TOTALS_SANS_VAT[index], processedItem.getTotalAmount());
            }
        } catch(IOException ioException) {
            LOG.error("", ioException);

            Assert.fail();
        }
    }

    @Test
    public void invalidFile() {
        try {
            ClassPathResource successResource = new ClassPathResource("invalid.txt");
            Assert.assertNotNull(successResource);

            String invalidFileContent = new String(FileCopyUtils.copyToByteArray(successResource.getFile()));
            IFileProcessor proc = new AllanGrayFileProcessor();
            proc.process(invalidFileContent);

            Assert.fail();
        } catch(IOException ioException) {
            LOG.error("", ioException);

            Assert.fail();
        } catch(RuntimeException runtime) {
            //expected
        }
    }
}
