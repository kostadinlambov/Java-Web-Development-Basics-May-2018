package javache.http;

import javache.WebConstants;

import java.util.*;

public class HttpResponseImpl implements HttpResponse {
    private int statusCode;
    private Map<String, String> headers;
    private byte[] content;

    public HttpResponseImpl() {
        this.headers = new HashMap<>();
        this.content = new byte[0];
    }

    @Override
    public Map<String, String> getHeaders() {
        return this.headers;
    }

    @Override
    public int getStatusCode() {
        return this.statusCode;
    }

    @Override
    public byte[] getContentBytes() {
        return this.content;
    }

    @Override
    public byte[] getResponseBytes() {
        String statusLine = getStatusLine();

        String headersString = this.createHeaders();
        byte[] headersBytesArr = headersString.getBytes();
        int headersLength = headersString.length();

        int responseMessageLength = statusLine.length() + headersLength + this.content.length;

        byte[] bytes = new byte[responseMessageLength];

        System.arraycopy(statusLine.getBytes(), 0, bytes, 0, statusLine.length());

        System.arraycopy(
                headersBytesArr, 0,
                bytes, statusLine.length(),
                headersLength);
        System.arraycopy(this.content, 0, bytes, statusLine.length() + headersLength, this.content.length);

        return bytes;
    }

    @Override
    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public void addHeader(String header, String value) {
        this.headers.putIfAbsent(header, value);
    }

    private String createHeaders() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, String> header : headers.entrySet()) {
            sb.append(header.getKey() + ": " + header.getValue() + "\r\n");
        }
        sb.append("\r\n");

        return sb.toString();
    }

    private String getStatusLine() {
        switch (this.statusCode) {
            case 200:
                return WebConstants.PROTOCOL_VERSION + " " + HttpStatus.OK.getStatusPhrase() + "\r\n";
            case 201:
                return WebConstants.PROTOCOL_VERSION + " " + HttpStatus.CREATED.getStatusPhrase() + "\r\n";
            case 204:
                return WebConstants.PROTOCOL_VERSION + " " + HttpStatus.NO_CONTENT.getStatusPhrase() + "\r\n";
            case 301:
                return WebConstants.PROTOCOL_VERSION + " " + HttpStatus.MOVED_PERMANENTLY.getStatusPhrase() + "\r\n";
            case 303:
                return WebConstants.PROTOCOL_VERSION + " " + HttpStatus.SEE_OTHER.getStatusPhrase() + "\r\n";
            case 400:
                return WebConstants.PROTOCOL_VERSION + " " + HttpStatus.BAD_REQUEST.getStatusPhrase() + "\r\n";
            case 401:
                return WebConstants.PROTOCOL_VERSION + " " + HttpStatus.UNAUTHORIZED.getStatusPhrase() + "\r\n";
            case 403:
                return WebConstants.PROTOCOL_VERSION + " " + HttpStatus.FORBIDDEN.getStatusPhrase() + "\r\n";
            case 500:
                return WebConstants.PROTOCOL_VERSION + " " + HttpStatus.INTERNAL_SERVER_ERROR.getStatusPhrase() + "\r\n";
        }

        return WebConstants.PROTOCOL_VERSION + " " + HttpStatus.NOT_FOUND.getStatusPhrase() + "\r\n";

    }
}
