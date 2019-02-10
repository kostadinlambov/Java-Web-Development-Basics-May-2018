package main.controllers;

import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.Model;

public class BaseController {

    protected boolean isLoggedIn(HttpSoletRequest request) {
        return request.getSession() != null
                && request.getSession().getAttributes().containsKey("user-id");
    }

    protected String view(HttpSoletRequest request, Model model, String view) {
        return "template:" + view;
    }

    protected String redirect(HttpSoletRequest request, Model model, String view) {
        return "redirect:/" + view;
    }
}
