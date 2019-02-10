package main.controllers;


import main.entities.Product;
import main.repositories.ProductRepository;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.Model;

import java.util.List;

@Controller
public class HomeController extends BaseController {
    private ProductRepository productRepository;

    public HomeController() {
        this.productRepository = new ProductRepository();
    }

    @GetMapping(route = "/")
    public String index(HttpSoletRequest request) {
        if (super.isLoggedIn(request)) {
            return super.redirect("home");
        }

        return super.view("guest-home");
    }

    @GetMapping(route = "/home")
    public String home(HttpSoletRequest request, Model model) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        List<Product> allProducts = this.productRepository.findAll();
        StringBuilder renderedProducts = new StringBuilder();

        renderedProducts.append("<div class=\"row d-flex justify-content-around mt-4\">");

        for (int i = 0; i < allProducts.size(); i++) {
            Product currentProduct = allProducts.get(i);

            if (i != 0 && i % 5 == 0) {
                renderedProducts.append("</div>");
                renderedProducts.append("<div class=\"row d-flex justify-content-around mt-4\">");
            }

            renderedProducts.append(currentProduct.extractHomeView());
        }

        if(!renderedProducts.toString().endsWith("</div>")) renderedProducts.append("</div>");

        model.addAttribute("username", request.getSession().getAttributes().get("username").toString());
        model.addAttribute("products", renderedProducts.toString());

        return super.view("user-home");
    }
}
