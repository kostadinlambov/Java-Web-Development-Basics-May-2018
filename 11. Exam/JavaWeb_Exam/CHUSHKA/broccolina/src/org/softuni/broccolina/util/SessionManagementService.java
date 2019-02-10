package org.softuni.broccolina.util;

import org.softuni.javache.http.*;

import java.util.Date;

public class SessionManagementService {
    private static final String SESSION_KEY = "Javache";

    private HttpSessionStorage sessionStorage;

    public SessionManagementService() {
        this.sessionStorage = new HttpSessionStorageImpl();
    }

    public HttpSessionStorage getSessionStorage() {
        return this.sessionStorage;
    }

    public void initSessionIfExistent(HttpRequest request) {
        if (request.getCookies().containsKey(SESSION_KEY)) {
            HttpCookie sessionCookie = request.getCookies().get(SESSION_KEY);

            String sessionId = sessionCookie.getValue();
            HttpSession session = this.sessionStorage.getById(sessionId);

            if (session != null
                    && session.isValid()) {
                request.setSession(session);
            } else {
                request.getCookies().remove(SESSION_KEY);
            }
        }
    }

    public void sendSessionIfExistent(HttpRequest request, HttpResponse response) {
        if (request.getSession() != null) {
            if (this.sessionStorage.getById(request.getSession().getId()) == null) {
                this.sessionStorage.addSession(request.getSession());
                response.addCookie(SESSION_KEY, request.getSession().getId());
            }

            if (!request.getSession().isValid()) {
                response.addCookie(SESSION_KEY, "removed; expires=" + new Date(0).toString());
            }
        }
    }

    public void clearInvalidSessions() {
        this.sessionStorage.refreshSessions();
    }
}
