package javache.handlers.response;

import javache.WebConstants;
import javache.http.HttpRequest;
import javache.http.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PagesHandler extends ResponseHandler {

    public PagesHandler(HttpRequest httpRequest, HttpResponse httpResponse) {
        super(httpRequest, httpResponse);
    }

    @Override
    public byte[] handleResponse() throws IOException {

        String requestMethod = super.getHttpRequest().getMethod();
        String requestUrl = super.getHttpRequest().getRequestUrl();

        if("/".equals(requestUrl)){
            requestUrl = "/index";
        }

        if (!super.getHttpRequest().isResource()) {
            String pagePath = WebConstants.RESOURCE_FOLDER_PATH + "\\pages" + requestUrl + ".html";

            super.getHttpResponse().addHeader("Date", getTimeStamp());
            super.getHttpResponse().addHeader("Server", "Javache/1.0.1");
            super.getHttpResponse().addHeader("Content-Type", "text/html");
            super.getHttpResponse().addHeader("Connection", "Closed");

            if (requestMethod.equals("GET") && checkURL(pagePath)) {
                byte[] content = null;
                try {
                    content = Files.readAllBytes(Paths.get(pagePath));
                } catch (IOException e) {
                    return super.error_response(500);
                }

                super.getHttpResponse().setStatusCode(200);
                super.getHttpResponse().setContent(content);
                return super.getHttpResponse().getResponseBytes();
            } else {
                return super.error_response(404);
            }
        }

        return null;
    }
}
