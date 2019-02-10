package controllers;

import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.javache.http.HttpSession;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.Model;

@Controller
public class HomeController {
    public HomeController() {
    }

    @GetMapping(route = "/")
    public String index(HttpSoletRequest request) {
        if(request.getSession() != null) {
            return "redirect:/home";
        }

        return "template:index";
    }

    @GetMapping(route = "/home")
    public String home(HttpSoletRequest request, Model model) {
        if(request.getSession() == null) {
            return "redirect:/login";
        }

        String username = request.getSession().getAttributes().get("username").toString();

        model.addAttribute("username", username);

        return "template:home";
    }
}
