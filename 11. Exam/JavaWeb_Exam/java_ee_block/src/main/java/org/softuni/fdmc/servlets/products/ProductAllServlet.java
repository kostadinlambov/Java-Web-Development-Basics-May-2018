package org.softuni.fdmc.servlets.products;

import org.softuni.fdmc.data.models.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/products/all")
public class ProductAllServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        PrintWriter out = resp.getWriter();
        StringBuilder sb = new StringBuilder();
        List<Product> productList = new ArrayList<>();

        productList = (List<Product>) this.getServletContext().getAttribute("products");

        sb.append("<h1>All Products</h1><hr>");

        if (productList == null) {
            sb.append("<h2>There are no products.<a href=\"/products/create\">Create some!</a></h2>");
        } else {
            for (Product product : productList) {
                sb.append(String.format("<h3><a href=\"/products/details?productName=%s\">%s</a></h3>",
                        product.getName(), product.getName()));
            }
        }

        sb.append("<a href=\"/products/create\">Back to Create Product</a>");

        out.println(sb.toString());

    }


}
