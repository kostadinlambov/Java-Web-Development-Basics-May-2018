package org.softuni.fdmc.servlets;

import org.softuni.fdmc.data.Cat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cats/create")
public class CreateCatServlet extends HttpServlet {

    private List<Cat> cats = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        StringBuilder content = new StringBuilder();

        String errMessage = (String) this.getServletContext().getAttribute("errorMessage");
        if (errMessage != null) {
            content.append(String.format("<h1 style=\"color: red\">%s</h1><hr>", errMessage));
            this.getServletContext().setAttribute("errorMessage", null);
        }


        content.append("<h1>Create a Cat!</h1>")
                .append("<form method=\"post\" action=\"/cats/create\">")
                .append("<label for=\"name\">Name:</label>")
                .append("<input type=\"text\" name=\"name\" required><br>")
                .append("<label for=\"breed\">Breed:</label>")
                .append("<input type=\"text\" name=\"breed\"required><br>")
                .append("<label for=\"color\">Color:</label>")
                .append("<input type=\"text\" name=\"color\"required><br>")
                .append("<label for=\"numberOfLegs\">Number of Legs:</label>")
                .append("<input type=\"number\" name=\"numberOfLegs\" required><br>")
                .append("<input type=\"submit\" value=\"Create Cat\"/><br>")
                .append("<p><a href=\"/home\">Back to Home</a></p>")
                .append("</form>");

        out.println(content.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String name = req.getParameter("name");
        String breed = req.getParameter("breed");
        String color = req.getParameter("color");
        int numOfLegs = Integer.parseInt(req.getParameter("numberOfLegs"));

        Cat cat = new Cat(name, breed, color, numOfLegs);

        this.getServletContext().setAttribute("errorMessage", null);

        for (Cat currentCat : cats) {
            if (currentCat.getName().equals(name)) {
                this.getServletContext().setAttribute("errorMessage",
                        String.format("Cat with name %s already exists. Please create new cat!", name));
                System.out.println("Cat already exists!");
                resp.sendRedirect("/cats/create");
                return;
            }
        }

        cats.add(cat);


        this.getServletContext().setAttribute("allCats", cats);

        List<Cat> parsedCats = (List<Cat>) this.getServletContext().getAttribute("List");

        resp.sendRedirect(String.format("/cats/profile?catName=%s", name));
    }
}
