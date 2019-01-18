package javache.handlers.response;

import javache.WebConstants;
import javache.http.HttpRequest;
import javache.http.HttpResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ResourceHandler extends ResponseHandler {

    public ResourceHandler(HttpRequest httpRequest, HttpResponse httpResponse) {
        super(httpRequest, httpResponse);
    }

    public byte[] handleResponse() throws IOException {
        String requestMethod = super.getHttpRequest().getMethod();
        String requestUrl = super.getHttpRequest().getRequestUrl();

        String assetPath = WebConstants.RESOURCE_FOLDER_PATH + "\\assets" + requestUrl;

        if (requestMethod.equals("GET") && checkURL(assetPath)) {
            byte[] content = null;
            try{
                content = Files.readAllBytes(Paths.get(assetPath));
            }catch (IOException e){
                return super.error_response(500);
            }

            super.getHttpResponse().setStatusCode(200);
            super.getHttpResponse().setContent(content);

            String contentType = getMimeType(requestUrl);

            super.getHttpResponse().addHeader("Date", getTimeStamp());
            super.getHttpResponse().addHeader("Server", "Javache/1.0.1");
            super.getHttpResponse().addHeader("Content-Type", contentType);
            super.getHttpResponse().addHeader("Content-Disposition", "inline");
            super.getHttpResponse().addHeader("Content-Length", content.length + "");
            super.getHttpResponse().addHeader("Connection", "Closed");

            return super.getHttpResponse().getResponseBytes();
        } else {
            return super.error_response(404);
        }
    }

    private String getMimeType(String requestUrl) {

        String fileExtension = requestUrl.split("\\.")[1];

        switch(fileExtension){
            case "html":
                return "text/html";
            case "css":
                return "text/css";
            case "png":
                return "image/png";
            case "jpeg":
                return "image/jpeg";
            case "gif":
                return "image/gif";
            case "ico":
                return "image/x-icon";
            case "mpeg":
                return "audion/mpeg";
            case "mp4":
                return "video/mp4";
            case "json":
                return "application/json";
            case "js":
                return "application/javascript";
        }

        return "text/plain";
    }
}
