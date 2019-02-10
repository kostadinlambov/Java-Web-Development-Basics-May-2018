package org.softuni.broccolina.solet;

import org.softuni.javache.http.HttpResponseImpl;

import java.io.OutputStream;

public class HttpSoletResponseImpl
        extends HttpResponseImpl
        implements HttpSoletResponse {

    private OutputStream outputStream;

    public HttpSoletResponseImpl(OutputStream outputStream) {
        super();
        this.setOutputStream(outputStream);
    }

    @Override
    public OutputStream getOutputStream() {
        return this.outputStream;
    }

    private void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
}
