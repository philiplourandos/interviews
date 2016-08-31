package com.lore.iview;

import com.lore.iview.model.ItemTotal;
import java.util.Map;

/**
 *
 * @author lore
 */
public interface IFileProcessor {
    Map<String, ItemTotal> process(String content);
}
