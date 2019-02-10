package org.softuni.broccolina.solet;

import org.softuni.javache.http.HttpStatus;
import org.softuni.javache.io.Writer;

import java.io.IOException;


public abstract class BaseHttpSolet implements HttpSolet {
    private boolean isInitialized;

    private SoletConfig soletConfig;

    protected BaseHttpSolet() {
        this.isInitialized = false;
    }

    private void configureNotFound(HttpSoletRequest request, HttpSoletResponse response) {
        response.setStatusCode(HttpStatus.NOT_FOUND);

        response.addHeader("Content-Type", "text/html");
    }

    protected void doGet(HttpSoletRequest request, HttpSoletResponse response) {
        this.configureNotFound(request, response);

        response.setContent((
                "<h1>[ERROR] GET "
                        + request.getRequestUrl()
                        + "</h1><br/>"
                        + "<h3>[MESSAGE] The page or functionality you are looking for is not found.</h3>"
        ).getBytes());
    }

    protected void doPost(HttpSoletRequest request, HttpSoletResponse response) {
        this.configureNotFound(request, response);

        response.setContent((
                "<h1>[ERROR] POST "
                        + request.getRequestUrl()
                        + "</h1><br/>"
                        + "<h3>[MESSAGE] The page or functionality you are looking for is not found.</h3>"
        ).getBytes());
    }

    protected void doPut(HttpSoletRequest request, HttpSoletResponse response) {
        this.configureNotFound(request, response);

        response.setContent((
                "<h1>[ERROR] PUT "
                        + request.getRequestUrl()
                        + "</h1><br/>"
                        + "<h3>[MESSAGE] The page or functionality you are looking for is not found.</h3>"
        ).getBytes());
    }

    protected void doDelete(HttpSoletRequest request, HttpSoletResponse response) {
        this.configureNotFound(request, response);

        response.setContent((
                "<h1>[ERROR] DELETE "
                        + request.getRequestUrl()
                        + "</h1><br/>"
                        + "<h3>[MESSAGE] The page or functionality you are looking for is not found.</h3>"
        ).getBytes());
    }

    @Override
    public void init(SoletConfig soletConfig) {
        this.isInitialized = true;
        this.soletConfig = soletConfig;
    }

    @Override
    public boolean isInitialized() {
        return this.isInitialized;
    }

    @Override
    public SoletConfig getSoletConfig() {
        return this.soletConfig;
    }

    @Override
    public void service(HttpSoletRequest request, HttpSoletResponse response) {
        if (request.getMethod().equals("GET")) {
            this.doGet(request, response);
        } else if (request.getMethod().equals("POST")) {
            this.doPost(request, response);
        } else if (request.getMethod().equals("PUT")) {
            this.doPut(request, response);
        } else if (request.getMethod().equals("DELETE")) {
            this.doDelete(request, response);
        }
    }
}
