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

@WebServlet("/cats/all")
public class AllCatsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        StringBuilder sb = new StringBuilder();
        List<Cat> catList = new ArrayList<>();

        catList = (List<Cat>) this.getServletContext().getAttribute("allCats");

        sb.append("<h1>All Cats</h1><hr>");

        if(catList == null){
            sb.append("<h2>There are no cats.<a href=\"/cats/create\">Create some!</a></h2>");
        }else{
            for (Cat cat : catList) {
                sb.append(String.format("<h3><a href=\"/cats/profile?catName=%s\">%s</a></h3>",
                        cat.getName(), cat.getName()));
            }
        }

        sb.append("<a href=\"/\">Back to Home</a>");

        out.println(sb.toString());
    }
}
