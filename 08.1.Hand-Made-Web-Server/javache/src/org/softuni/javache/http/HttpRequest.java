package org.softuni.javache.http;

import java.util.HashMap;

public interface HttpRequest {
    HashMap<String, String> getHeaders();

    HashMap<String, String> getQueryParameters();

    HashMap<String, String> getBodyParameters();

    HashMap<String, HttpCookie> getCookies();

    String getMethod();

    String getRequestUrl();

    HttpSession getSession();

    void setMethod(String method);

    void setRequestUrl(String requestUrl);

    void setSession(HttpSession session);

    void addHeader(String header, String value);

    void addBodyParameter(String parameter, String value);

    boolean isResource();
}
