package org.softuni.java_ee_block.servlets.home;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
//        this.getServletContext().setAttribute("cats", new CatRepository());
//        this.getServletContext().setAttribute("users", new UserRepository());
//        this.getServletContext().setAttribute("orders", new OrderRepository());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {




        req.getRequestDispatcher("home.jsp").forward(req, resp);
    }
}
