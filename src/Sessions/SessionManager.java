package Sessions;

import Netpbm.LoadedImage;

import java.util.*;

public class SessionManager {
    private static SessionManager instance = null;

    private final Map<Integer, List<LoadedImage>> sessions = new HashMap<>();
    private int currentSessionId = 1;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public int createNewSession() {
        int newId = currentSessionId++;
        sessions.put(newId, new ArrayList<>());
        return newId;
    }

    public void addImageToSession(int sessionId, LoadedImage image) {
        List<LoadedImage> sessionImages = sessions.get(sessionId);
        if (sessionImages != null) {
            sessionImages.add(image);
        }
    }

    public List<LoadedImage> getSessionImages(int sessionId) {
        return sessions.getOrDefault(sessionId, Collections.emptyList());
    }

    public Map<Integer, List<LoadedImage>> getAllSessions() {
        return Collections.unmodifiableMap(sessions);
    }
}