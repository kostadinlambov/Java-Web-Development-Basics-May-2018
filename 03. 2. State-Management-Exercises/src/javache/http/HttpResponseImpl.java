package javache.http;

import java.util.HashMap;
import java.util.Map;

public class HttpResponseImpl implements HttpResponse {
    private HttpStatus statusCode;

    private HashMap<String, String> headers;

    private HashMap<String, HttpCookie> cookies;

    private byte[] content;

    public HttpResponseImpl() {
        this.setContent(new byte[0]);
        this.headers = new HashMap<>();
        this.cookies = new HashMap<>();
    }

    private byte[] getHeadersBytes() {
        StringBuilder result = new StringBuilder()
                .append(ResponseLines.getResponseLine(this.getStatusCode().getStatusCode())).append(System.lineSeparator());

        for (Map.Entry<String,String> header : this.getHeaders().entrySet()) {
            result.append(header.getKey()).append(": ").append(header.getValue()).append(System.lineSeparator());
        }

        if(!this.cookies.isEmpty()) {
            result.append("Set-Cookie: ");

            for (HttpCookie cookie : this.cookies.values()) {
                result.append(cookie.toString()).append("; ");
            }

            result.replace(result.length() - 2, result.length(), "");
            result.append(System.lineSeparator());
        }

        result.append(System.lineSeparator());

//        addCookiesToHeaders();

        return result.toString().getBytes();
    }

//    @Override
//    public void addCookie(String cookie, String value) {
//        String cookieString = cookie + "=" + value;
//        if (!this.headers.containsKey("Set-Cookie")) {
//            this.headers.put("Set-Cookie", cookieString);
//        } else {
//            this.headers.put("Set-Cookie", this.headers.get("Set-Cookie") + "; " + cookie);
//        }
//    }

//    private void addCookiesToHeaders(){
//        StringBuilder result = new StringBuilder();
//
//        for (HttpCookie cookie : this.cookies.values()) {
//            this.addHeader("Set-Cookie: ", cookie.toString());
////            result.append(cookie.toString()).append(System.lineSeparator());
//        }
//
//    }


    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public HashMap<String, HttpCookie> getCookies() {
        return this.cookies;
    }

    public void setCookies(HashMap<String, HttpCookie> cookies) {
        this.cookies = cookies;
    }

    @Override
    public HashMap<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

    @Override
    public void setStatusCode(HttpStatus status) {
        this.statusCode = status;
    }

    @Override
    public byte[] getContent() {
        return this.content;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.putIfAbsent(header, value);
    }

    @Override
    public void addCookie(String name, String value) {
        this.cookies.putIfAbsent(name, new HttpCookieImpl(name, value));
    }

    @Override
    public byte[] getBytes() {
        byte[] headersBytes = this.getHeadersBytes();
        byte[] bodyBytes = this.getContent();

        byte[] fullResponse = new byte[headersBytes.length + bodyBytes.length];

        System.arraycopy(headersBytes, 0, fullResponse, 0, headersBytes.length);
        System.arraycopy(bodyBytes, 0, fullResponse, headersBytes.length, bodyBytes.length);

        return fullResponse;
    }
}
