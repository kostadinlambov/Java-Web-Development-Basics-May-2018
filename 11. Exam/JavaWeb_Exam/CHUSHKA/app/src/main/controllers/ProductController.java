package main.controllers;


import main.entities.Product;
import main.repositories.OrderRepository;
import main.repositories.ProductRepository;
import main.repositories.UserRepository;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.Model;
import org.softuni.summermvc.api.PathVariable;

import java.time.LocalDateTime;

@Controller
public class ProductController extends BaseController {
    private UserRepository userRepository;

    private ProductRepository productRepository;

    private OrderRepository orderRepository;

    public ProductController() {
        this.userRepository = new UserRepository();
        this.productRepository = new ProductRepository();
        this.orderRepository = new OrderRepository();
    }

    @GetMapping(route = "/products/details/{id}")
    public String details(@PathVariable String id, HttpSoletRequest request, Model model) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        Product foundProduct = this.productRepository.findById(id);

        if(foundProduct == null) {
            return super.redirect("home");
        }

        model.addAttribute("id", foundProduct.getId());
        model.addAttribute("name", foundProduct.getName());
        model.addAttribute("price", foundProduct.getPrice());
        model.addAttribute("type", foundProduct.getType().toString());
        model.addAttribute("description", foundProduct.getDescription());

        return super.view("product-details");
    }

    @GetMapping(route = "/products/order/{id}")
    public String order(@PathVariable String id, HttpSoletRequest request) {
        if (!super.isLoggedIn(request)) {
            return super.redirect("login");
        }

        String currentUserId = request.getSession().getAttributes().get("user-id").toString();

        User currentUser = this.userRepository.findById(currentUserId);
        Product currentProduct = this.productRepository.findById(id);

        if(currentUser == null || currentProduct == null) {
            return super.redirect("home");
        }

        Order order = new Order();

        order.setClient(currentUser);
        order.setProduct(currentProduct);
        order.setOrderedOn(LocalDateTime.now());

        this.orderRepository.createOrder(order);

        return super.redirect("home");
    }

}
