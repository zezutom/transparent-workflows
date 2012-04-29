package org.zezutom.crashtracker.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: tom
 * Date: 29/04/2012
 * Time: 21:15
 *
 * Captures the process inputs and turns them into the model provided to the process.
 */
public class ModelBuilder {

    private Map<String, Object> model = new HashMap<String, Object>();

    public ModelBuilder add(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public Map<String, Object> build() {
        return model;
    }

}
