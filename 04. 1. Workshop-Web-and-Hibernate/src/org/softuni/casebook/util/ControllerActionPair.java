package org.softuni.casebook.util;

import org.softuni.casebook.controllers.BaseController;

import java.lang.reflect.Method;

public class ControllerActionPair {

    private BaseController controller;

    private Method action;

    public ControllerActionPair(BaseController controller, Method action){
        this.setController(controller);
        this.setAction(action);

    }

    public BaseController getController() {
        return this.controller;
    }

    private void setController(BaseController controller) {
        this.controller = controller;
    }

    public Method getAction() {
        return this.action;
    }

    public void setAction(Method action) {
        this.action = action;
    }
}
