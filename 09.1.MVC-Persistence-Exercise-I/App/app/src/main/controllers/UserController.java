package controllers;

import entities.User;
import models.binding.UserLoginBindingModel;
import models.binding.UserRegisterBindingModel;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.javache.http.HttpSession;
import org.softuni.javache.http.HttpSessionImpl;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.PostMapping;
import repositories.UserRepository;

@Controller
public class UserController {
    private UserRepository userRepository;

    public UserController() { this.userRepository = new UserRepository(); }

    @GetMapping(route = "/login")
    public String login() {
        return "template:login";
    }

    @PostMapping(route = "/login")
    public String loginConfirm(HttpSoletRequest request, UserLoginBindingModel userLoginBindingModel) {
        if(request.getSession() != null) {
            return "redirect:/home";
        }

        User user = this.userRepository.findByUsername(userLoginBindingModel.getUsername());

        if(user == null || !user.getPassword().equals(userLoginBindingModel.getPassword())) {
            return "redirect:/login";
        }

        request.setSession(new HttpSessionImpl());

        request.getSession().addAttribute("user-id", user.getId());
        request.getSession().addAttribute("username", user.getUsername());

        return "redirect:/home";
    }

    @GetMapping(route = "/register")
    public String register() {
        return "template:register";
    }

    @PostMapping(route = "/register")
    public String registerConfirm(UserRegisterBindingModel userRegisterBindingModel) {
        if(!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            return "redirect:/register";
        }

        User user = new User();

        user.setUsername(userRegisterBindingModel.getUsername());
        user.setPassword(userRegisterBindingModel.getPassword());

        this.userRepository.saveUser(user);

        return "redirect:/login";
    }

    @GetMapping(route = "/logout")
    public String logout(HttpSoletRequest request) {
        if(request.getSession() == null) {
            return "redirect:/login";
        }

        request.getSession().invalidate();

        return "redirect:/";
    }
}
