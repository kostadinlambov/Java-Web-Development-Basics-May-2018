package javache.http;

import java.util.*;

public class HttpRequestImpl implements HttpRequest {
    private String requestContent;
    private String method;
    private String requestUrl;
    private Map<String, String> headers;
    private Map<String, String> bodyParameters;

    public HttpRequestImpl(String requestContent) {
        this.requestContent = requestContent;
        this.setMethod(this.requestContent);
        this.setRequestUrl(this.requestContent);
        this.initHeaders();
        this.initBodyParameters();
    }

    private void initHeaders() {
        this.headers = new HashMap<>();
        String tokens = this.requestContent.split("\\r\\n\\r\\n")[0];
        String[] headersArray = tokens.split("\\r\\n");

        Arrays.stream(headersArray).skip(1L).forEach(header -> {
            String[] kvp = header.split(": ");
            this.addHeader(kvp[0], kvp[1]);
        });
    }

    private void initBodyParameters() {
        this.bodyParameters = new HashMap<>();

        if (this.requestContent.split("\\r\\n\\r\\n").length > 1) {
            String requestBody = this.requestContent.split("\\r\\n\\r\\n")[1];
            if (requestBody != null) {
                String[] requestBodyParameters = requestBody.split("&");
                for (String parameter : requestBodyParameters) {
                    String[] kvp = parameter.split("=");
                    this.bodyParameters.putIfAbsent(kvp[0], kvp[1]);
                }
            }
        }
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public Map<String, String> getBodyParameters() {
        return this.bodyParameters;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String method) {
        this.method = method.split("\\s+")[0];
    }

    @Override
    public String getRequestUrl() {
        return this.requestUrl;
    }

    @Override
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl.split("\\s+")[1];
    }

    @Override
    public void addHeader(String parameter, String value) {
        this.headers.putIfAbsent(parameter, value);

    }

    @Override
    public boolean isResource() {
        return this.getRequestUrl().contains(".");
    }
}
