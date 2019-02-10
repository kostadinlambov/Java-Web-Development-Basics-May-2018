package org.softuni.fdmc.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();

        StringBuilder content = new StringBuilder();

        content.append("<h1> Welcome to Fluffy Duffy Munchkins Cats!</h1>")
                .append("<h2>Navigate through the application using the links below!</h2>")
                .append("<hr/>")
                .append("<a href=\"/cats/create\">Create Cat</a></h3><br>")
                .append("<a href=\"/cats/all\">All Cats</a>");

        out.println(content.toString());
    }
}
