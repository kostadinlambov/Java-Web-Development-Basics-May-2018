package javache;

import javache.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RequestHandler {
    private static final String HTML_EXTENSION_AND_SEPARATOR = ".html";

    private HttpRequest httpRequest;

    private HttpResponse httpResponse;

    private HttpSessionStorage sessionStorage;

    public RequestHandler(HttpSessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public byte[] handleRequest(String requestContent) {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();

        byte[] result = null;

        if(this.httpRequest.getMethod().equals("GET")) {
            result = this.processGetRequest();
        }

        this.sessionStorage.refreshSessions();

        return result;
    }

    private byte[] ok(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.OK);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] badRequest(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.BAD_REQUEST);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] notFound(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.NOT_FOUND);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] redirect(byte[] content, String location) {
        this.httpResponse.setStatusCode(HttpStatus.SEE_OTHER);
        this.httpResponse.addHeader("Location", location);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] internalServerError(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private String getMimeType(File file) {
        String fileName = file.getName();

        if(fileName.endsWith("css")) {
            return "text/css";
        } else if (fileName.endsWith("html")) {
            return "text/html";
        } else if (fileName.endsWith("jpg") || fileName.endsWith("jpeg")) {
            return "image/jpeg";
        } else if (fileName.endsWith("png")) {
            return "image/png";
        }

        return "text/plain";
    }

    private byte[] processResourceRequest() {
        String assetPath = WebConstants.ASSETS_FOLDER_PATH +
                this.httpRequest.getRequestUrl();

        File file = new File(assetPath);

        if(!file.exists() || file.isDirectory()) {
            return this.notFound(("Asset not found!").getBytes());
        }

        byte[] result = null;

        try {
            result = Files.readAllBytes(Paths.get(assetPath));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong!").getBytes());
        }

        this.httpResponse.addHeader("Content-Type", this.getMimeType(file));
        this.httpResponse.addHeader("Content-Length", result.length + "");
        this.httpResponse.addHeader("Content-Disposition", "inline");

        return this.ok(result);
    }

    private byte[] processPageRequest(String page) {
        String pagePath = WebConstants.PAGES_FOLDER_PATH +
                page
                + HTML_EXTENSION_AND_SEPARATOR;

        File file = new File(pagePath);

        if(!file.exists() || file.isDirectory()) {
            return this.notFound(("Page not found!").getBytes());
        }

        byte[] result = null;

        try {
            result = Files.readAllBytes(Paths.get(pagePath));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong!").getBytes());
        }

        this.httpResponse.addHeader("Content-Type", this.getMimeType(file));

        return this.ok(result);
    }

    private byte[] processGetRequest() {
        if(this.httpRequest.getRequestUrl().equals("/")) {
            //INDEX

            return this.processPageRequest("/index");


        } else if (this.httpRequest.getRequestUrl().equals("/login")) {
            //LOGIN
            HttpSession session = new HttpSessionImpl();
            session.addAttribute("username", "Pesho");

            this.sessionStorage.addSession(session);

            this.httpResponse.addCookie("Javache", session.getId());
            return this.processPageRequest(this.httpRequest.getRequestUrl());


        } else if (this.httpRequest.getRequestUrl().equals("/logout")) {
            //LOGOUT

            if(!this.httpRequest.getCookies().containsKey("Javache")) {
                return this.redirect(("You must login to access this route!").getBytes()
                        , "/");
            }

            String sessionId = this.httpRequest.getCookies().get("Javache").getValue();
            this.sessionStorage.getById(sessionId).invalidate();

            this.httpResponse.addCookie("Javache", "deleted; expires=Thu, 01 Jan 1970 00:00:00 GMT;");

            return this.ok(("Successfully expired").getBytes());


        } else if (this.httpRequest.getRequestUrl().equals("/forbidden")) {
            //FORBIDDEN

            if(!this.httpRequest.getCookies().containsKey("Javache")) {
                return this.redirect(("You must login to access this route!").getBytes()
                        , "/");
            }

            String sessionId = this.httpRequest.getCookies().get("Javache").getValue();
            HttpSession session = this.sessionStorage.getById(sessionId);
            String username = session.getAttributes().get("username").toString();

            return this.ok(("HELLO " + username + "!!!").getBytes());
        }

        return this.processResourceRequest();
    }
}
