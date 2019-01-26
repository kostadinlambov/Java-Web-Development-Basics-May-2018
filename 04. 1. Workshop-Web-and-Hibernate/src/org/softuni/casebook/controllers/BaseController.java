package org.softuni.casebook.controllers;

import org.softuni.casebook.CasebookWebConstans;
import org.softuni.javache.WebConstants;
import org.softuni.javache.http.HttpRequest;
import org.softuni.javache.http.HttpResponse;
import org.softuni.javache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class BaseController {

    private static final String PAGE_MIME_TYPE ="text/html";

    protected BaseController() { }

    protected byte[] ok(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.Ok);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] badRequest(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.BadRequest);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }


    byte[] notFound(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.NotFound);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }


    protected byte[] redirect(byte[] content, String location, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.SeeOther);
        httpResponse.addHeader("Location", location);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }

    protected byte[] internalServerError(byte[] content, HttpResponse httpResponse) {
        httpResponse.setStatusCode(HttpStatus.InternalServerError);
        httpResponse.setContent(content);
        return httpResponse.getBytes();
    }



    protected byte[] processPageRequest(String page, HttpResponse httpResponse) {
        String pagePath = WebConstants.PAGES_FOLDER_PATH +
                page
                + CasebookWebConstans.HTML_EXTENSION_AND_SEPARATOR;

        File file = new File(pagePath);

        if (!file.exists() || file.isDirectory()) {
            return this.notFound(("Page not found!").getBytes(), httpResponse);
        }

        byte[] result = null;

        try {
            result = Files.readAllBytes(Paths.get(pagePath));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong!").getBytes(), httpResponse);
        }

        httpResponse.addHeader("Content-Type", PAGE_MIME_TYPE);

        return this.ok(result, httpResponse);
    }
}
