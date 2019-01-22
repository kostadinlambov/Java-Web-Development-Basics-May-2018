package javache.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HttpSessionStorage {
    private HashMap<String, HttpSession> allSessions;

    public HttpSessionStorage() {
        this.allSessions = new HashMap<>();
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
