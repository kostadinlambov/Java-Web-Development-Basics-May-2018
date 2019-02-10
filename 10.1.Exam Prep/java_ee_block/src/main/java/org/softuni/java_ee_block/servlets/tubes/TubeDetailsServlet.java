package org.softuni.java_ee_block.servlets.tubes;

import org.softuni.java_ee_block.data.models.Tube;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/tubes/details")
public class TubeDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();

        String title = req.getParameter("title");

        List<Tube> tubes = (List<Tube>) this.getServletContext().getAttribute("tubes");

        Tube tube = tubes
                .stream()
                .filter(x -> x.getTitle().equals(title))
                .findFirst()
                .orElse(null);

        StringBuilder sb = new StringBuilder();

        sb.append(String.format("<h1>Successfully created Tube - %s</h1>" +
                "<hr>" +
                "<h3>Descripption:</h3>" +
                "<p>%s</p>" +
                "<hr>" +
                "<h3>Video Link:</h3>" +
                "<p><a href=%s>%s</a></p>" +
                "<hr>" +
                "<a href=\"/tubes/create\">Back to Create Tube</a>",
                tube.getTitle(),
                tube.getDescription(),
                tube.getVideoLink(),
                tube.getVideoLink()));

        out.println(sb.toString());


    }
}
