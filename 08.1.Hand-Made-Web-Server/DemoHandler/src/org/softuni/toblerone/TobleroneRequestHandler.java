package org.softuni.toblerone;


import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.http.HttpResponse;
import org.softuni.javache.http.HttpResponseImpl;
import org.softuni.javache.http.HttpStatus;
import org.softuni.javache.io.Writer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TobleroneRequestHandler implements RequestHandler {
    private boolean hasIntercepted = false;

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream) {

        try {
            HttpResponse response = new HttpResponseImpl();

            response.setStatusCode(HttpStatus.OK);
            response.addHeader("Content-Type", "text/html");
            response.setContent("<h1>Hi, I'm Toblerone </h1>".getBytes());

            hasIntercepted = true;
            Writer.writeBytes(response.getBytes(), outputStream);
        } catch (IOException e) {
            hasIntercepted = false;
            e.printStackTrace();
        }

    }

    @Override
    public boolean hasIntercepted() {
        return this.hasIntercepted;
    }
}
