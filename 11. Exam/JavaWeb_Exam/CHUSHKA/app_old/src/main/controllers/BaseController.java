package main.controllers;

import main.entities.Role;
import org.softuni.broccolina.solet.HttpSoletRequest;

import org.softuni.summermvc.api.Model;

public class BaseController {
    protected boolean isLoggedIn(HttpSoletRequest request) {
        return request.getSession() != null
                && request.getSession().getAttributes().containsKey("user-id");
    }

    protected Role getRole(HttpSoletRequest request) {
        return Role.valueOf(request.getSession().getAttributes().get("user-role").toString());
    }

    protected String view(String view) {
        return "template:" + view;
    }

    protected String error(Model model, BindingResult bindingResult) {
        StringBuilder errorsResult = new StringBuilder();

        for (String error : bindingResult.getErrors()) {
            errorsResult
                    .append("<li>")
                    .append(error)
                    .append("</li>");
        }

        model.addAttribute("errors", errorsResult.toString());

        System.out.println("asd");

        return "template:error";
    }

    protected String redirect(String view) {
        return "redirect:/" + view;
    }
}
