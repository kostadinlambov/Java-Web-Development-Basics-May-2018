package org.softuni.javache.http;

import java.util.*;

public class HttpSessionStorageImpl implements  HttpSessionStorage{
    private Map<String, HttpSession> allSessions;

    public HttpSessionStorageImpl() {
        this.allSessions = new HashMap<>();
    }

    @Override
    public Map<String, HttpSession> getAllSessions(){
        return Collections.unmodifiableMap(this.allSessions);
    }


    @Override
    public HttpSession getById(String sessionId) {
        if(!this.allSessions.containsKey(sessionId)) {
            return null;
        }

        return this.allSessions.get(sessionId);
    }

    @Override
    public void addSession(HttpSession session) {
        this.allSessions.putIfAbsent(session.getId(), session);
    }

    @Override
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
