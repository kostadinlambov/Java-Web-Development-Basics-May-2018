package org.softuni.java_ee_block.servlets.tubes;

import org.softuni.java_ee_block.data.models.Tube;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/tubes/create")
public class TubeCreateServlet extends HttpServlet {

    private List<Tube> tubes = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        PrintWriter out = resp.getWriter();
        StringBuilder content = new StringBuilder();

        content.append("<h1>Create Tube</h1><hr/>")
                .append("<form method=\"post\" action=\"/tubes/create\">")
                .append("<label for=\"title\">Title: </label>")
                .append("<input type=\"text\" name=\"title\" placeholder=\"Title...\" required><br><hr/>")
                .append("<label for=\"description\">Description: </label>")
                .append("<input type=\"text\" name=\"description\" placeholder=\"Description...\" required><br><hr/>")
                .append("<label for=\"videoLink\">Video Link: </label>")
                .append("<input type=\"text\" name=\"videoLink\" placeholder=\"Video Link...\" required><br><hr/>")
                .append("<input type=\"submit\" value=\"Create Tube\"/><br>")
                .append("</form>");

        out.println(content.toString());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String videoLink = req.getParameter("videoLink");


        Tube tube = new Tube(title, description, videoLink);

        this.getServletContext().setAttribute("errorMessage", null);



        tubes.add(tube);


        this.getServletContext().setAttribute("tubes", tubes);

        resp.sendRedirect(String.format("/tubes/details?title=%s", title));
    }
}
