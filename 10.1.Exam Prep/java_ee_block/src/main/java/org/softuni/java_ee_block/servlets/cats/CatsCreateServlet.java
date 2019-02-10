package org.softuni.java_ee_block.servlets.cats;

import org.softuni.java_ee_block.data.models.Cat;
import org.softuni.java_ee_block.data.models.User;
import org.softuni.java_ee_block.data.repos.CatRepository;
import org.softuni.java_ee_block.data.repos.UserRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cats/create")
public class CatsCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String breed = req.getParameter("breed");
        String color = req.getParameter("color");
        Integer numberOfLegs = Integer.parseInt(req.getParameter("numberOfLegs"));
        User creator = ((UserRepository)this.getServletContext().getAttribute("users"))
                .getByUsername(req.getSession().getAttribute("username").toString());

        Cat cat = new Cat(name, breed, color, numberOfLegs, creator);

        CatRepository catRepository = (CatRepository)this.getServletContext().getAttribute("cats");

        this.getServletContext().setAttribute("catsCreateErrorMessage", null);

        for (Cat currentCat : catRepository.getAllCats()) {
            if(currentCat.getName().equals(name)){
                this.getServletContext().setAttribute("catsCreateErrorMessage",
                        String.format("Cat with name %s already exists. Please create new cat!", name));
                resp.sendRedirect("/cats/create");
                return;
            }
        }

        catRepository.addCat(cat);

        resp.sendRedirect("/cats/profile?catName=" + cat.getName());
    }
}
