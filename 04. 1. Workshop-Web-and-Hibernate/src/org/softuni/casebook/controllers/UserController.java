package org.softuni.casebook.controllers;

import org.softuni.casebook.CasebookWebConstans;
import org.softuni.javache.http.HttpRequest;
import org.softuni.javache.http.HttpResponse;

public class UserController extends BaseController {

    public byte[] login(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getCookies().containsKey(CasebookWebConstans.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/home", httpResponse);
        }
        return this.processPageRequest("/login",httpResponse);
    }

    public byte[] register(HttpRequest httpRequest, HttpResponse httpResponse) {
        if (httpRequest.getCookies().containsKey(CasebookWebConstans.CASEBOOK_SESSION_KEY)) {
            return this.redirect(new byte[0], "/home", httpResponse);
        }
        return this.processPageRequest("/register",httpResponse);
    }
}
