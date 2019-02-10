package controllers;

import entities.Tube;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.Model;
import repositories.TubeRepository;

import javax.persistence.PersistenceException;
import java.util.List;

@Controller
public class HomeController extends BaseController {
    private TubeRepository tubeRepository;

    public HomeController() {
        this.tubeRepository = new TubeRepository();
    }

    @GetMapping(route = "/")
    public String index(HttpSoletRequest request, Model model) {
        return super.view(request,model, "index");
    }

    @GetMapping(route = "/home")
    public String home(HttpSoletRequest request, Model model) {
        //TODO: Implement me...
        return null;
    }
}
