package Sessions;

import Netpbm.LoadedImage;
import java.util.*;

public class SessionManager {
    private static SessionManager instance = null;

    private final Map<Integer, Session> sessions = new HashMap<>();
    private int activeSessionId = -1;
    private int nextSessionId = 1;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public int createNewSession() {
        int id = nextSessionId++;
        Session newSession = new Session(id);
        sessions.put(id, newSession);
        activeSessionId = id;
        return id;
    }

    public void addImageToSession(int sessionId, LoadedImage image) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            session.addImage(image);
        }
    }

    public void addAlternationToSession(int sessionId, String alternation) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            session.addAlternation(alternation);
        }
    }

    public List<LoadedImage> getSessionImages(int sessionId) {
        Session session = sessions.get(sessionId);
        return (session != null) ? session.getImages() : new ArrayList<>();
    }

    public List<String> getSessionAlternations(int sessionId) {
        Session session = sessions.get(sessionId);
        return (session != null) ? session.getAlternations() : new ArrayList<>();
    }

    public Session getSession(int sessionId) {
        return sessions.get(sessionId);
    }

    public int getActiveSessionId() {
        return activeSessionId;
    }

    public void setActiveSessionId(int sessionId) {
        if (sessions.containsKey(sessionId)) {
            activeSessionId = sessionId;
        } else {
            System.out.println("Session " + sessionId + " does not exist.");
        }
    }

    public Map<Integer, Session> getAllSessions() {
        return Collections.unmodifiableMap(sessions);
    }


    public void saveImageBackup(int sessionId) {
        Session session = sessions.get(sessionId);
        if (session != null) {
            List<int[][][]> backup = new ArrayList<>();
            for (LoadedImage img : session.getImages()) {
                backup.add(deepCopyPixelData(img.getPixelData()));
            }
            session.getImageBackups().add(backup);

            if (!session.getImages().isEmpty()) {
                session.getWidthBackups().add(session.getImages().getFirst().getWidth());
                session.getHeightBackups().add(session.getImages().getFirst().getHeight());
            }
        }
    }

    private int[][][] deepCopyPixelData(int[][][] original) {
        int height = original.length;
        int width = original[0].length;
        int channels = original[0][0].length;

        int[][][] copy = new int[height][width][channels];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                for (int c = 0; c < channels; c++) {
                    copy[i][j][c] = original[i][j][c];
                }
            }
        }
        return copy;
    }
}


