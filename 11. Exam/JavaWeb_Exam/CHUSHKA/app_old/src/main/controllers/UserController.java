package main.controllers;



import main.entities.Role;
import main.entities.User;
import main.models.binding.UserLoginBindingModel;
import main.models.binding.UserRegisterBindingModel;
import main.repositories.UserRepository;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.javache.http.HttpSessionImpl;
import org.softuni.summermvc.api.*;


@Controller
public class UserController extends BaseController{
    private UserRepository userRepository;

    public UserController() {
        this.userRepository = new UserRepository();
    }

    @GetMapping(route = "/login")
    public String login(HttpSoletRequest request, Model model) {
        if(super.isLoggedIn(request)) {
            return super.redirect("");
        }

        return super.view("login");
    }

    @PostMapping(route = "/login")
    public String loginConfirm(HttpSoletRequest request, UserLoginBindingModel userLoginBindingModel, Model model) {
        if(super.isLoggedIn(request)) {
            return super.redirect("");
        }

        if(userLoginBindingModel == null || userLoginBindingModel.getUsername() == null || userLoginBindingModel.getPassword() == null ) {
            return super.view("login");
        }

        User registeredUser = this.userRepository.findByUsername(userLoginBindingModel.getUsername());

        if(registeredUser == null ||
                !registeredUser.getPassword().equals(userLoginBindingModel.getPassword())) {
            return super.view("login");
        }

        request.setSession(new HttpSessionImpl());

        request.getSession().addAttribute("user-id", registeredUser.getId());
        request.getSession().addAttribute("username", registeredUser.getUsername());
        request.getSession().addAttribute("user-role", registeredUser.getRole().toString());

        return super.redirect("home");
    }

    @GetMapping(route = "/register")
    public String register(HttpSoletRequest request, Model model) {
        if(super.isLoggedIn(request)) {
            return super.redirect("");
        }

        return super.view("register");
    }

    @PostMapping(route = "/register")
    public String registerConfirm(UserRegisterBindingModel userRegisterBindingModel, HttpSoletRequest request, Model model, BindingResult bindingResult) {
        if(super.isLoggedIn(request)) {
            return super.redirect("");
        }

        if(bindingResult.hasErrors()) {
            return super.error(model, bindingResult);
        }

        String email = userRegisterBindingModel.getEmail();

        if(!userRegisterBindingModel.getPassword()
                .equals(userRegisterBindingModel.getConfirmPassword())) {
            return super.view("register");
        }

        if(this.userRepository
                .findByUsername(userRegisterBindingModel.getUsername())
                != null) {
            return super.view("register");
        }

        User user = new User();

        user.setUsername(userRegisterBindingModel.getUsername());
        user.setPassword(userRegisterBindingModel.getPassword());
        user.setFullName(userRegisterBindingModel.getFullName());
        user.setEmail(userRegisterBindingModel.getEmail());
        user.setRole(Role.USER);

        this.userRepository.createUser(user);

        return super.redirect("login");
    }

    @GetMapping(route = "/logout")
    public String logout(HttpSoletRequest request, Model model) {
        if(!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        request.getSession().invalidate();

        return super.redirect("");
    }
}
