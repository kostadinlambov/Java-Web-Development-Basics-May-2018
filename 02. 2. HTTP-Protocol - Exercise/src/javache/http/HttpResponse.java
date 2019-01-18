package javache.http;

import java.util.HashMap;
import java.util.Map;

public interface HttpResponse {
    Map<String, String> getHeaders();

    int getStatusCode();

    byte[] getContentBytes();

    byte[] getResponseBytes();

    void setStatusCode(int statusCode);

    void setContent(byte[] content);

    void addHeader(String header, String value);
}

