package org.softuni.javache.http;

public class HttpCookieImpl implements HttpCookie {
    private String name;

    private String value;

    public HttpCookieImpl(String name, String value) {
        this.setName(name);
        this.setValue(value);
    }

    @Override
    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    private void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.getName() + "=" + this.getValue();
    }
}
