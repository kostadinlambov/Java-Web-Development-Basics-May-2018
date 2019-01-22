package javache;

import db.entities.User;
import db.repositories.EntityRepository;
import javache.http.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class RequestHandler {
    private static final String HTML_EXTENSION_AND_SEPARATOR = ".html";

    private HttpRequest httpRequest;

    private HttpResponse httpResponse;

    private HttpSessionStorage sessionStorage;

    private EntityRepository<User> entityRepository;

    public RequestHandler(HttpSessionStorage sessionStorage) {
        this.sessionStorage = sessionStorage;
    }

    public byte[] handleRequest(String requestContent) throws IOException {
        this.httpRequest = new HttpRequestImpl(requestContent);
        this.httpResponse = new HttpResponseImpl();
        this.entityRepository = new EntityRepository<>();

        byte[] result = null;

        if (this.httpRequest.getMethod().equals("GET")) {
            result = this.processGetRequest();
        } else if (this.httpRequest.getMethod().equals("POST")) {
            result = this.processPostRequest();
        }

        this.sessionStorage.refreshSessions();
        return result;
    }

    private byte[] ok(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.Ok);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] badRequest(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.BadRequest);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] notFound(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.NotFound);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] redirect(byte[] content, String location) {
        this.httpResponse.setStatusCode(HttpStatus.SeeOther);
        this.httpResponse.addHeader("Location", location);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private byte[] internalServerError(byte[] content) {
        this.httpResponse.setStatusCode(HttpStatus.InternalServerError);
        this.httpResponse.setContent(content);
        return this.httpResponse.getBytes();
    }

    private String getMimeType(File file) {
        String fileName = file.getName();

        if (fileName.endsWith("css")) {
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

        if (!file.exists() || file.isDirectory()) {
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

        if (!file.exists() || file.isDirectory()) {
            return this.notFound(("Page not found!").getBytes());
        }

        byte[] result;
        try {
            result = getPageContent(pagePath, page);
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong!").getBytes());
        }

        this.httpResponse.addHeader("Content-Type", this.getMimeType(file));

        return this.ok(result);
    }

    private byte[] getPageContent(String pagePath, String urlPath) throws IOException {
        String content = null;
        try {
            content = String.join("", Files.readAllLines(Paths.get(pagePath)));
        } catch (IOException e) {
            return this.internalServerError(("Something went wrong!").getBytes());
        }


        switch (urlPath) {
            case "/users/profile":

                String sessionId = this.httpRequest.getCookies().get("Javache").getValue();
                HttpSession session = this.sessionStorage.getById(sessionId);
                String email = session.getAttributes().get("email").toString();
                String password = session.getAttributes().get("password").toString();

                content = content.replaceAll("\\$\\{username\\}", email);
                content = content.replaceAll("\\$\\{password\\}", password);
                return content.getBytes();
            case "/home":

                sessionId = this.httpRequest.getCookies().get("Javache").getValue();
                session = this.sessionStorage.getById(sessionId);
                email = session.getAttributes().get("email").toString();
                password = session.getAttributes().get("password").toString();

                List<String> allUsers = this.entityRepository.findAll(
                        WebConstants.USERS_REPO_FILE_PATH);

                StringBuilder sb = new StringBuilder();
                for (String currentUser : allUsers) {
                    String currentEmail = currentUser.split(",")[1];
                    if (!currentEmail.equals(email)) {
                        sb.append(String.format("<h3>%s</h3>", currentEmail));
                    }
                }

                content = String.format(content, sb.toString());
                return (content.getBytes());
//                return this.notFound(("Page not found!").getBytes());
        }

//        return this.notFound(("Page not found!").getBytes());
        return content.getBytes();
    }

    private byte[] processGetRequest() {
        switch (this.httpRequest.getRequestUrl()) {
            case "/":
                //INDEX

                httpRequest.setRequestUrl("/html/index.html");
                //return this.processPageRequest("/index.html");

                break;
            case "/home":
                return this.processPageRequest(this.httpRequest.getRequestUrl());
            case "/users/profile":
                return this.processPageRequest(this.httpRequest.getRequestUrl());
            case "/login":
                //LOGIN
                return this.redirect("User don't exists!".getBytes(), "/html/login.html");
            case "/logout": {
                //LOGOUT

                if (!this.httpRequest.getCookies().containsKey("Javache")) {
                    return this.redirect(("You must login to access this route!").getBytes()
                            , "/");
                }

                String sessionId = this.httpRequest.getCookies().get("Javache").getValue();
                this.sessionStorage.getById(sessionId).invalidate();

                this.httpResponse.addCookie("Javache", "deleted; expires=Thu, 01 Jan 1970 00:00:00 GMT;");

                return this.ok(("Successfully expired").getBytes());
            }
            case "/forbidden": {
                //FORBIDDEN
                if (!this.httpRequest.getCookies().containsKey("Javache")) {
                    return this.redirect(("You must login to access this route!").getBytes()
                            , "/");
                }

                String sessionId = this.httpRequest.getCookies().get("Javache").getValue();
                HttpSession session = this.sessionStorage.getById(sessionId);
                String username = session.getAttributes().get("username").toString();

                return this.ok(("HELLO " + username + "!!!").getBytes());
            }
        }

        return this.processResourceRequest();
    }

    private byte[] processPostRequest() throws IOException {
        if (this.httpRequest.getRequestUrl().equals("/html/register.html")) {
            String email = this.httpRequest.getBodyParameters().get("email");
            String password = this.httpRequest.getBodyParameters().get("password");
            String confirmPassword = this.httpRequest.getBodyParameters().get("confirmPassword");

            if (!password.equals(confirmPassword)) {
                return redirect("Password must match confirm password".getBytes(), "/html/register.html");
            }

            String existingUser = this.entityRepository.findByUsername(email, WebConstants.USERS_REPO_FILE_PATH);

            if (existingUser == null) {
                User user = new User(email, password);
                entityRepository.save(user, WebConstants.USERS_REPO_FILE_PATH);
                return this.redirect("Redirect to login page!".getBytes(), "/html/login.html");
            }

            return this.redirect("User already exists".getBytes(), "/html/login.html");

        } else if (this.httpRequest.getRequestUrl().equals("/html/login.html")) {

            String email = this.httpRequest.getBodyParameters().get("email");
            String password = this.httpRequest.getBodyParameters().get("password");

            String userParameters = this.entityRepository.findByUsername(email, WebConstants.USERS_REPO_FILE_PATH);


            if (userParameters != null) {
                String passwordFromLoginForm = userParameters.split(",")[2];
                String id = userParameters.split(",")[0];
                if (password.equals(passwordFromLoginForm)) {

                    HttpSession session = new HttpSessionImpl();
                    session.addAttribute("email", email);
                    session.addAttribute("password", password);


                    String cookieValue = String.format("%s; Path=/", session.getId());

                    this.sessionStorage.addSession(session);
                    this.httpResponse.addCookie("Javache", cookieValue);

//                    String cookieValue = String.format("%s; Path=/", id);
//                    this.httpResponse.addCookie("id", cookieValue);

                    return this.redirect("Login successfully!".getBytes(), "/users/profile");

                }
//                 else{
//                     return this.redirect("User don't exists!".getBytes(), "/html/login.html" );
//                 }
//                 return this.redirect("Redirect to profile page!".getBytes(), "/users/profile" );
            }
            return this.redirect("User don't exists!".getBytes(), "/html/login.html");
        }

        return this.processResourceRequest();
    }
}
