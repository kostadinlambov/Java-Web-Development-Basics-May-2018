package main.controllers;

import main.entities.Tube;
import main.repositories.TubeRepository;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.Model;

import java.util.List;

@Controller
public class HomeController extends BaseController {
    private TubeRepository tubeRepository;

    public HomeController() {
        this.tubeRepository = new TubeRepository();
    }

    @GetMapping(route = "/")
    public String index(HttpSoletRequest request, Model model) {
        if(super.isLoggedIn(request)){
            return super.redirect(request, model, "home");
        }
        return super.view(request, model, "index");
    }

    @GetMapping(route = "/home")
    public String home(HttpSoletRequest request, Model model) {
        if(!super.isLoggedIn(request)){
            return super.redirect(request, model, "login");
        }

        String username = request.getSession().getAttributes().get("username").toString();

        StringBuilder result = new StringBuilder();
        List<Tube> allTubes = this.tubeRepository.findAllTubes();

        for (int i = 0; i < allTubes.size(); i++) {
            if (i % 3 == 0) {
                result.append("<div class=\"row d-flex justify-content-around\">");

            }

            result.append(allTubes.get(i).extractTubeThumbnailView());

            if (i % 3 == 2) {
                result.append("</div>");
            }
        }

        model.addAttribute("username", username);
        model.addAttribute("allTubes", result.toString());

        return super.view(request, model, "home");
    }
}
