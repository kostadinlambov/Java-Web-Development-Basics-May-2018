package javache.handlers.request;

import javache.handlers.response.PagesHandler;
import javache.handlers.response.ResourceHandler;
import javache.handlers.response.ResponseHandler;
import javache.http.HttpRequest;
import javache.http.HttpRequestImpl;
import javache.http.HttpResponse;
import javache.http.HttpResponseImpl;

import java.io.IOException;

public class RequestHandler {

    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public byte[] handleRequest(String requestContent) throws IOException {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();

        byte[] result = null;

        if (this.httpRequest.getMethod().equals("GET")) {
            result = this.processGetRequest();
        }

        return result;
    }

    private byte[] processGetRequest() throws IOException {

        Boolean isResource = httpRequest.isResource();

        if (!isResource) {
            ResponseHandler pagesHandler = new PagesHandler(this.httpRequest, this.httpResponse);
            return pagesHandler.handleResponse();

        } else {
            ResponseHandler resourceHandler = new ResourceHandler(this.httpRequest, this.httpResponse);
            return resourceHandler.handleResponse();
        }
    }
}
