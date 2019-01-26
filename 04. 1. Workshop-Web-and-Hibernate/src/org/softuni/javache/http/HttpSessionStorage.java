package org.softuni.javache.http;

import java.util.Map;

public interface HttpSessionStorage {
    Map<String, HttpSession> getAllSessions();

    HttpSession getById(String sessionId);

    void addSession(HttpSession session);

    void refreshSessions();
}
