package org.softuni.broccolina.solet;

import org.softuni.javache.http.HttpRequest;

import java.io.InputStream;

public interface HttpSoletRequest extends HttpRequest {
    InputStream getInputStream();
}
