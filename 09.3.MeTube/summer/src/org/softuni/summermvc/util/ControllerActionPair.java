package org.softuni.summermvc.util;

import java.lang.reflect.Method;
import java.util.*;

public class ControllerActionPair {
    private Object controller;

    private Method action;

    private Set<Object> parameters;

    public ControllerActionPair(Object controller, Method action) {
        this.setController(controller);
        this.setAction(action);
        this.parameters = new LinkedHashSet<>();
    }

    public Object getController() {
        return this.controller;
    }

    private void setController(Object controller) {
        this.controller = controller;
    }

    public Method getAction() {
        return this.action;
    }

    private void setAction(Method action) {
        this.action = action;
    }

    public Set<Object> getParameters() {
        return Collections.unmodifiableSet(this.parameters);
    }

    public void addParameter(Object parameter) {
        this.parameters.add(parameter);
    }

    public void clearParameters() {
        this.parameters.clear();
    }
}
