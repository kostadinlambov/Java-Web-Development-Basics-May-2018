package org.softuni.javache.http;

import java.util.HashMap;

public interface HttpSession {
    String getId();

    boolean isValid();

    HashMap<String, Object> getAttributes();

    void addAttribute(String name, Object attribute);

    void invalidate();
}
