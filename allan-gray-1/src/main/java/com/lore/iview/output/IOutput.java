package com.lore.iview.output;

import com.lore.iview.model.ItemTotal;
import java.util.Map;

/**
 *
 * @author lore
 */
public interface IOutput {
    String HEADER = "header";
    String TOTAL = "total";
    
    void write(Map<String, ItemTotal> items);
}
