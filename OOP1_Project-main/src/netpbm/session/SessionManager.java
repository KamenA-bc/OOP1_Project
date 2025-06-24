package netpbm.session;

import netpbm.image.NetPBMImages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * Central manager for all image editing sessions in the application.
 * <p>
 * Each session represents a group of images loaded and manipulated together.
 * This class handles creating new sessions, switching between them, and
 * tracking the currently active session. All sessions are assigned unique IDs
 * and stored in memory for the duration of the program.
 */

public class SessionManager {

    private static final Map<Integer, Session> sessions = new HashMap<>();
    private static int nextSessionId = 1;
    private static int activeSessionId = -1;

    /**
     * Creates a new session containing all provided images.
     * Assigns a unique session ID, adds all images to the session,
     * registers it in the global session map, and marks it as the active session.
     * A message is printed to indicate successful session creation.
     *
     * @param images A list of NetPBMImages objects to include in the session.
     */
    public static void createSessionWithImages(List<NetPBMImages> images) {
        int id = nextSessionId++;
        Session session = new Session(id);

        for (NetPBMImages image : images) {
            session.addImage(image);
        }

        sessions.put(id, session);
        activeSessionId = id;
        System.out.println("New session started. Session ID: " + id);
    }

    /**
     * Returns the currently active session, or {@code null} if none is active.
     *
     * @return The active {@code Session}, or {@code null} if not set.
     */
    public static Session getActiveSession() {
        return sessions.get(activeSessionId);
    }

    /**
     * Attempts to switch the active session to the one with the given ID.
     *
     * @param id The session ID to switch to.
     * @return {@code true} if the switch was successful, {@code false} if the ID does not exist.
     */
    public static boolean switchTo(int id) {
        if (sessions.containsKey(id)) {
            activeSessionId = id;
            return true;
        }
        return false;
    }

    public static int getActiveSessionId() {
        return activeSessionId;
    }

    /**
     * Checks if there is a valid active session.
     *
     * @return {@code true} if an active session exists, {@code false} otherwise.
     */
    public static boolean hasActiveSession() {
        return activeSessionId != -1 && sessions.containsKey(activeSessionId);
    }

    /**
     * Returns all currently managed sessions.
     *
     * @return A collection of all sessions.
     */
    public static Collection<Session> getAllSessions() {
        return sessions.values();
    }
}




