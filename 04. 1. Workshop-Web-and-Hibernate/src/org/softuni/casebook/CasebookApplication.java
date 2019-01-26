package org.softuni.casebook;

import org.softuni.casebook.controllers.BaseController;
import org.softuni.casebook.controllers.HomeController;
import org.softuni.casebook.controllers.ResourceController;
import org.softuni.casebook.controllers.UserController;
import org.softuni.casebook.util.ControllerActionPair;
import org.softuni.javache.WebConstants;
import org.softuni.javache.api.RequestHandler;
import org.softuni.javache.http.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class CasebookApplication implements RequestHandler {

    private boolean intercepted;

    private HttpRequest httpRequest;

    private HttpResponse httpResponse;

    private HttpSessionStorage sessionStorage;

    private Map<String, ControllerActionPair> getRequestRoutingMap;


    public CasebookApplication(HttpSessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
        this.intercepted = false;

        this.initializeGetRequestRoutingMap();
    }

    private void initializeGetRequestRoutingMap() {
        try {
            this.getRequestRoutingMap = new HashMap<String, ControllerActionPair>() {{
                put("/", new ControllerActionPair(
                        new HomeController(),
                        HomeController.class.getDeclaredMethod("index",
                                HttpRequest.class, HttpResponse.class)
                ));
                put("/login", new ControllerActionPair(
                        new UserController(),
                        UserController.class.getDeclaredMethod("login",
                                HttpRequest.class, HttpResponse.class)
                ));
                put("/register", new ControllerActionPair(
                        new UserController(),
                        UserController.class.getDeclaredMethod("register",
                                HttpRequest.class, HttpResponse.class)
                ));
            }};
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

//        {{
//            put("/", "/index");
//            put("/login", "/login");
//            put("/register", "/register");
//            put("/home", "/home");
//        }};


    @Override
    public byte[] handleRequest(String requestContent) {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();

        byte[] result = null;

        if (this.httpRequest.getMethod().equals("GET")) {
            result = this.processGetRequest();
        } else if (this.httpRequest.getMethod().equals("POST")) {
            result = this.processPostRequest();
        }

        this.sessionStorage.refreshSessions();

        this.intercepted = true;

        return result;
    }


    @Override
    public boolean hasIntercepted() {
        return this.intercepted;
    }


    private byte[] processGetRequest() {
        String requestUrl = this.httpRequest.getRequestUrl();

//        if (this.httpRequest.getRequestUrl().equals("/")) {
        if (this.getRequestRoutingMap.containsKey(requestUrl)) {
            ControllerActionPair cap =
                    this.getRequestRoutingMap.get(requestUrl);

            try {
                return (byte[]) cap
                        .getAction()
                        .invoke(cap.getController(),
                                this.httpRequest,
                                this.httpResponse);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }

//            if(this.httpRequest.getCookies().containsKey(CasebookWebConstans.CASEBOOK_SESSION_KEY)){
//                return this.redirect(new byte[0], "/home");
//            }
//            return this.processPageRequest("/index");
//        }else if(this.httpRequest.getRequestUrl().equals("/login")) {
//            if(this.httpRequest.getCookies().containsKey(CasebookWebConstans.CASEBOOK_SESSION_KEY)){
//                return this.redirect(new byte[0], "/home");
//            }
//            return this.processPageRequest("/login");
//        }else if(this.httpRequest.getRequestUrl().equals("/register")){
//            if(this.httpRequest.getCookies().containsKey(CasebookWebConstans.CASEBOOK_SESSION_KEY)){
//                return this.redirect(new byte[0], "/home");
//            }
//            return this.processPageRequest("/register");
//        }else if(this.httpRequest.getRequestUrl().equals("/home")) {
//            if(!this.httpRequest.getCookies().containsKey(CasebookWebConstans.CASEBOOK_SESSION_KEY)){
//                return this.redirect(new byte[0], "/");
//            }
//            return this.processPageRequest("/home");
//        }
//
//        return this.processResourceRequest();
        }
        return new ResourceController().processResourceRequest(this.httpRequest, this.httpResponse);
    }


    private byte[] processPostRequest() {


        return new byte[0];
    }
}
