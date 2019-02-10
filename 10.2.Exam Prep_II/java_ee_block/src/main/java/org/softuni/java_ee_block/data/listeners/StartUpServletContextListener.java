package org.softuni.java_ee_block.data.listeners;

import org.softuni.java_ee_block.data.repos.CatRepository;
import org.softuni.java_ee_block.data.repos.OrderRepository;
import org.softuni.java_ee_block.data.repos.UserRepository;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartUpServletContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setAttribute("cats", new CatRepository());
        sce.getServletContext().setAttribute("users", new UserRepository());
        sce.getServletContext().setAttribute("orders", new OrderRepository());

        System.out.println("Servlet Context Initialized Successfully!" );
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Servlet Context Destroyed Successfully!" );
    }
}
