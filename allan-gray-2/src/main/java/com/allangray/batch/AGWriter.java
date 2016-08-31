package com.allangray.batch;

import java.util.List;
import org.springframework.batch.item.ItemWriter;

/**
 *
 * @author lore
 */
public class AGWriter implements ItemWriter<Object>{

    public AGWriter() {
    }

    @Override
    public void write(List<? extends Object> list) throws Exception {
        
    }
}
