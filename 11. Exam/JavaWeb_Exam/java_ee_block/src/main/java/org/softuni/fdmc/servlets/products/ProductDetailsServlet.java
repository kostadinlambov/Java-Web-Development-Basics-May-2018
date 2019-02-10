package org.softuni.fdmc.servlets.products;

import org.softuni.fdmc.data.models.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/products/details")
public class ProductDetailsServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        String productName = req.getParameter("productName");
        List<Product> products = (List<Product>) this.getServletContext().getAttribute("products");

        Product product = products.stream().filter(x -> x.getName().equals(productName)).findFirst().orElse(null);

        String type = product.getType().toString().substring(0,1) + product.getType().toString().substring(1).toLowerCase();

        StringBuilder sb = new StringBuilder();

            sb
                    .append(String.format("<h1>Product Details</h1><hr/>"))
                    .append(String.format("<h3>Name - %s</h3>", product.getName()))
                    .append(String.format("<h3>Description: %s</h3>", product.getDescription()))
                    .append(String.format("<h3>Type: %s</h3>", type))
                    .append("<a href=\"/products/all\">Back to All Products </a>");

        out.println(sb.toString());
    }

}
