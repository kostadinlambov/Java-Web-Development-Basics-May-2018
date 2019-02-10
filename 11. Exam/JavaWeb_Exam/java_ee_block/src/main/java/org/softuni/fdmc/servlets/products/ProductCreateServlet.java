package org.softuni.fdmc.servlets.products;


import org.softuni.fdmc.data.models.Product;
import org.softuni.fdmc.util.Type;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/products/create")
public class ProductCreateServlet extends HttpServlet {

    private List<Product> products = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        PrintWriter out = resp.getWriter();
        StringBuilder content = new StringBuilder();

        content.append("<h1>Create Product</h1><hr/>")
                .append("<form method=\"post\" action=\"/products/create\">")
                .append("<label for=\"name\">Name: </label>")
                .append("<input type=\"text\" name=\"name\" placeholder=\"Name...\" required><br><hr/>")
                .append("<label for=\"description\">Description: </label>")
                .append("<input type=\"text\" name=\"description\" placeholder=\"Description...\" required><br><hr/>")
                .append(
                        "        <input type=\"radio\" id=\"fd\" name=\"type\" value=\"Food\">" +
                        "        <label for=\"fd\"> Food</label>" +
                        "        <input type=\"radio\" id=\"dm\" name=\"type\" value=\"Domestic\">" +
                        "        <label for=\"dm\"> Domestic</label>" +
                        "        <input type=\"radio\" id=\"ht\" name=\"type\" value=\"Health\">" +
                        "        <label for=\"ht\"> Health</label>" +
                        "        <input type=\"radio\" id=\"cm\" name=\"type\" value=\"Cosmetic\">" +
                        "        <label for=\"cm\"> Cosmetic</label>" +
                        "        <input type=\"radio\" id=\"ot\" name=\"type\" value=\"Other\" checked>" +
                        "        <label for=\"ot\"> Other</label><br><br>")
                .append("<input type=\"submit\" value=\"Create Product\"/><br>")
                .append("</form>");

        out.println(content.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String typeFromForm = req.getParameter("type");

        Type type = Type.OTHER;

        switch (typeFromForm){
            case "Food":
                 type = Type.FOOD;
            break;
            case "Domestic":
                 type = Type.DOMESTIC;
                break;
            case "Health":
                type = Type.HEALTH;
                break;
            case "Cosmetic":
                type = Type.COSMETIC;
                break;
        }


        Product product = new Product(name, description, type);

        products.add(product);


        this.getServletContext().setAttribute("products", products);

        resp.sendRedirect("/products/all");
    }
}
