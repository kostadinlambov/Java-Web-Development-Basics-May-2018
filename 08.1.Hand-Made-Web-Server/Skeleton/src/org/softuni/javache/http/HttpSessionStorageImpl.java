package org.softuni.javache.http;

import java.util.*;

public class HttpSessionStorageImpl implements HttpSessionStorage {
    private Map<String, HttpSession> allSessions;

    public HttpSessionStorageImpl() {
        this.allSessions = new HashMap<>();
    }

    public Map<String, HttpSession> getAllSessions() {
        return Collections.unmodifiableMap(this.allSessions);
    }

    public HttpSession getById(String sessionId) {
        if(!this.allSessions.containsKey(sessionId)) {
            return null;
        }

        return this.allSessions.get(sessionId);
    }

    public void addSession(HttpSession session) {
        this.allSessions.putIfAbsent(session.getId(), session);
    }

    public void refreshSessions() {
        List<String> idsToRemove = new ArrayList<>();

        for (HttpSession session : this.allSessions.values()) {
            if(!session.isValid()) {
                idsToRemove.add(session.getId());
            }
        }

        for (String id : idsToRemove) {
            this.allSessions.remove(id);
        }
    }
}
