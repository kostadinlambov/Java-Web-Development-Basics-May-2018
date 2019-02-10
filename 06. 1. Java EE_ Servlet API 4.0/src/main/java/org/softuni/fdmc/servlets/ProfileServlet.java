package org.softuni.fdmc.servlets;

import org.softuni.fdmc.data.Cat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/cats/profile")
public class ProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        String catName = req.getParameter("catName");
        List<Cat> cats = (List<Cat>) this.getServletContext().getAttribute("allCats");

        boolean exists = false;
        Cat cat = null;

        for (Cat currentCat : cats) {
            if (currentCat.getName().equals(catName)) {
                cat = currentCat;
                exists = true;
            }
        }

//        Cat cat = (Cat) this.getServletContext().getAttribute(catName);
        StringBuilder sb = new StringBuilder();

        if (!exists) {
            sb.append(String.format("<h1>Cat, with name - %s was not found.</h1>", catName));
        } else {
            sb.append(String.format("<h1>Cat - %s</h1><hr/>", cat.getName()))
                    .append(String.format("<h2>Breed: %s</h2>", cat.getBreed()))
                    .append(String.format("<h2>Color: %s</h2>", cat.getColor()))
                    .append(String.format("<h2>Number of legs: %d</h2><hr/>", cat.getNumberOfLegs()))
                    .append("<a href=\"/cats/all\">Back</a>");
        }

        out.println(sb.toString());
    }
}
