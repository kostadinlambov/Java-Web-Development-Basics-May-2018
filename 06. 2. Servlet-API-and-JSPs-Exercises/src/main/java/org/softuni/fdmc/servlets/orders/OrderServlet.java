package org.softuni.fdmc.servlets.orders;

import org.softuni.fdmc.data.models.Cat;
import org.softuni.fdmc.data.models.Order;
import org.softuni.fdmc.data.models.User;
import org.softuni.fdmc.data.repos.CatRepository;
import org.softuni.fdmc.data.repos.OrderRepository;
import org.softuni.fdmc.data.repos.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/orders/order")
public class OrderServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String catName = req.getParameter("catName");

        User user = ((UserRepository)this.getServletContext().getAttribute("users"))
                .getByUsername(userName);
        Cat cat = ((CatRepository)this.getServletContext().getAttribute("cats"))
                .getByName(catName);

        Order order = new Order(user, cat);

        ((OrderRepository)this.getServletContext().getAttribute("orders")).addOrder(order);

        resp.sendRedirect("/cats/all");
    }
}
