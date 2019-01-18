package javache.handlers.response;

import javache.WebConstants;
import javache.http.HttpRequest;
import javache.http.HttpResponse;
import javache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class ResponseHandler {
    private HttpRequest httpRequest;
    private HttpResponse httpResponse;

    public ResponseHandler(HttpRequest httpRequest, HttpResponse httpResponse) {
        this.httpRequest = httpRequest;
        this.httpResponse = httpResponse;
    }

    public abstract byte[] handleResponse() throws IOException;

    HttpRequest getHttpRequest() {
        return this.httpRequest;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    HttpResponse getHttpResponse() {
        return this.httpResponse;
    }

    public void setHttpResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    byte[] error_response(int statusCode) throws IOException {
        List<String> content = Files.readAllLines(Paths.get(WebConstants.ERROR_PAGE_PATH));
        String responseContent = messageLineCreator(content, statusCode);

        this.getHttpResponse().setStatusCode(statusCode);
        this.getHttpResponse().setContent(responseContent.getBytes());

        return this.getHttpResponse().getResponseBytes();
    }

    private String messageLineCreator(List<String> content, int statusCode) {
        String contentString = String.join("", content);
        String replacementStatusLine = null;
        String replacementErrorMessage = null;

        switch (statusCode) {
            case 400:
                replacementStatusLine = String.format("<h1>%s</h1>", HttpStatus.BAD_REQUEST.getStatusPhrase());
                replacementErrorMessage = "Your browser sent a request that this server could not understand.";
                break;
            case 500:
                replacementStatusLine = String.format("<h1>%s</h1>", HttpStatus.INTERNAL_SERVER_ERROR.getStatusPhrase());
                replacementErrorMessage = "<p>The page cannot be displayed because an internal server error has occurred.</p>";
                break;
            default:
                replacementStatusLine = String.format("<h1>%s</h1>", HttpStatus.NOT_FOUND.getStatusPhrase());
                replacementErrorMessage = "The resource you are looking for might have been removed, " +
                        "had its name changed or is temporarily unavailable.";
                break;
        }

        contentString = contentString.replaceAll("<h1>Error</h1>", replacementStatusLine);
        contentString = contentString.replaceAll("<p>(.*?)</p>", replacementErrorMessage);

        return contentString;
    }

    static boolean checkURL(String serverPath) {
        File myFile = new File(serverPath);
        System.out.println();
        System.out.println("myFile exists && !myFile.isDirectory(): " + (myFile.exists() && !myFile.isDirectory()));
        System.out.println(serverPath + "\r\n");
        return myFile.exists() && !myFile.isDirectory();
    }

    static String getTimeStamp() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
        String formattedDate = sdf.format(date);
        return formattedDate;
    }
}
