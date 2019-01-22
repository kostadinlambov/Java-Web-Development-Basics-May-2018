package javache.http;

import javache.WebConstants;

import java.util.Arrays;
import java.util.HashMap;

public interface HttpResponse {
    enum ResponseLines {
        Ok (WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.OK.getStatusPhrase()),
        Created (WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.CREATED.getStatusPhrase()),
        NoContent (WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.NO_CONTENT.getStatusPhrase()),
        SeeOther (WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.SEE_OTHER.getStatusPhrase()),
        BadRequest (WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.BAD_REQUEST.getStatusPhrase()),
        Unauthorized (WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.UNAUTHORIZED.getStatusPhrase()),
        Forbidden (WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.FORBIDDEN.getStatusPhrase()),
        NotFound (WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.NOT_FOUND.getStatusPhrase()),
        InternalServerError (WebConstants.SERVER_HTTP_VERSION + " " + HttpStatus.INTERNAL_SERVER_ERROR.getStatusPhrase());

        private String value;

        ResponseLines(String responseLine) {
            this.value = responseLine;
        }

        static String getResponseLine(int statusCode) {
            return ((ResponseLines) Arrays.stream(values()).filter((x) -> x.value.contains("" + statusCode)).toArray()[0]).value;
        }
    }

    HashMap<String, String> getHeaders();

    HttpStatus getStatusCode();

    byte[] getContent();

    byte[] getBytes();

    void setStatusCode(HttpStatus statusCode);

    void setContent(byte[] content);

    void addHeader(String header, String value);

    void addCookie(String name, String value);
}
