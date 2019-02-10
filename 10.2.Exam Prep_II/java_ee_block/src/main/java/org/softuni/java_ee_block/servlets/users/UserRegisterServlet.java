package org.softuni.java_ee_block.servlets.users;

import org.softuni.java_ee_block.Constants;
import org.softuni.java_ee_block.data.models.User;
import org.softuni.java_ee_block.data.repos.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/register")
public class UserRegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");
        String isAdmin = req.getParameter("admin");

        UserRepository userRepo = (UserRepository)
                this.getServletContext()
                        .getAttribute("users");

        this.getServletContext().setAttribute("registerErrorMessage", null);


        if (!password.equals(confirmPassword)) {
            this.getServletContext().setAttribute("registerErrorMessage",
                    "Password does not match the confirm password.");
            resp.sendRedirect("/users/register");
            return;
        }

        for (User user : userRepo.getAllUsers()) {
            if (user.getUsername().equals(username)) {
                this.getServletContext().setAttribute("registerErrorMessage",
                        "The username already exists. Please use a different username.");
                resp.sendRedirect("/users/register");
                return;
            }
        }

        String role = isAdmin != null ? Constants.ADMIN_ROLE : Constants.USER_ROLE;

        User user = new User(username, password, role);

        ((UserRepository) this.getServletContext().getAttribute("users")).addUser(user);

        resp.sendRedirect("/");
    }
}
