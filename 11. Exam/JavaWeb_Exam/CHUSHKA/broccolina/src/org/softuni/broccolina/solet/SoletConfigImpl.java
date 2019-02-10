package org.softuni.broccolina.solet;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SoletConfigImpl implements SoletConfig {
    private Map<String, Object> attributes;

    public SoletConfigImpl() {
        this.attributes = new HashMap<String, Object>();
    }

    @Override
    public Map<String, Object> getAllAttributes() {
        return Collections.unmodifiableMap(this.attributes);
    }

    @Override
    public Object getAttribute(String name) {
        if(this.attributes.containsKey(name)) {
            return this.attributes.get(name);
        }

        return null;
    }

    @Override
    public void setAttribute(String name, Object attribute) {
        this.attributes.put(name, attribute);
    }

    @Override
    public void deleteAttribute(String name) {
        this.attributes.remove(name);
    }
}
