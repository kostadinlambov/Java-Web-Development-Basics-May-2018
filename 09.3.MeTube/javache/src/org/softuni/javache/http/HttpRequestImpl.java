package org.softuni.javache.http;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HttpRequestImpl implements HttpRequest {
    private String method;

    private String requestUrl;

    private HashMap<String, String> headers;

    private HashMap<String, String> queryParameters;

    private HashMap<String, String> bodyParameters;

    private HashMap<String, HttpCookie> cookies;

    private HttpSession session;

    public HttpRequestImpl(String requestContent) {
        this.setSession(null);
        this.initMethod(requestContent);
        this.initRequestUrl(requestContent);
        this.initHeaders(requestContent);
        this.initQueryParameters(requestContent);
        this.initBodyParameters(requestContent);
        this.initCookies();
    }

    private void initMethod(String requestContent) {
        this.setMethod(requestContent.split("\\s")[0]);
    }

    private void initRequestUrl(String requestContent) {
        this.setRequestUrl(requestContent.split("[\\s\\?]")[1]);
    }

    private void initHeaders(String requestContent) {
        this.headers = new HashMap<>();

        List<String> requestParams = Arrays.asList(
                requestContent.split("\\r\\n"));

        int i = 1;

        while (i < requestParams.size() && requestParams.get(i).length() > 0) {
            String[] headerKeyValuePair = requestParams.get(i).split("\\:\\s");

            this.addHeader(headerKeyValuePair[0], headerKeyValuePair[1]);

            i++;
        }
    }

    private void initQueryParameters(String requestContent) {
        this.queryParameters = new HashMap<>();

        String fullRequestUrl = requestContent.split("[\\s]")[1];

        if (fullRequestUrl.split("\\?").length < 2) {
            return;
        }

        String queryString = fullRequestUrl.split("\\?")[1];
        String[] queryKeyValuePairs = queryString.split("\\&");

        for (int i = 0; i < queryKeyValuePairs.length; i++) {
            String[] queryKeyValuePair = queryKeyValuePairs[i].split("\\=");

            String queryParameterKey = queryKeyValuePair[0];
            String queryParameterValue = queryKeyValuePair[1];

            this.queryParameters.putIfAbsent(queryParameterKey, queryParameterValue);
        }
    }

    private void initBodyParameters(String requestContent) {
        if (this.getMethod().equals("POST")) {
            this.bodyParameters = new HashMap<>();

            List<String> requestParams = Arrays.asList(requestContent.split("\\r\\n"));

            if (requestParams.size() > this.headers.size() + 2) {
                List<String> bodyParams = Arrays.asList(requestParams.get(this.headers.size() + 2).split("\\&"));

                for (int i = 0; i < bodyParams.size(); i++) {
                    String[] bodyKeyValuePair = bodyParams.get(i).split("\\=");

                    this.addBodyParameter(bodyKeyValuePair[0], bodyKeyValuePair[1]);
                }
            }
        }
    }

    private void initCookies() {
        this.cookies = new HashMap<>();

        if (!this.headers.containsKey("Cookie")) {
            return;
        }

        String cookiesHeader = this.headers.get("Cookie");
        String[] allCookies = cookiesHeader.split("\\;\\s");

        for (int i = 0; i < allCookies.length; i++) {
            String[] cookieNameValuePair = allCookies[i].split("\\=");

            this.cookies.putIfAbsent(cookieNameValuePair[0], new HttpCookieImpl(cookieNameValuePair[0], cookieNameValuePair[1]));
        }
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public HashMap<String, String> getBodyParameters() {
        return this.bodyParameters;
    }

    @Override
    public HashMap<String, String> getQueryParameters() {
        return this.queryParameters;
    }

    @Override
    public HashMap<String, HttpCookie> getCookies() {
        return this.cookies;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    @Override
    public HttpSession getSession() {
        return this.session;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    @Override
    public void setSession(HttpSession session) {
        this.session = session;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.putIfAbsent(header, value);
    }

    @Override
    public void addBodyParameter(String parameter, String value) {
        this.bodyParameters.putIfAbsent(parameter, value);
    }

    @Override
    public boolean isResource() {
        return this.getRequestUrl().contains(".");
    }
}
