package netpbm.command;

import netpbm.image.NetpbmImage;
import netpbm.session.Session;
import netpbm.session.SessionManager;

/**
 * Command that prints information about the currently active image editing session.
 * <p>
 * This includes the session's unique ID, a list of all image file names currently loaded,
 * and a list of all image operations ("alternations") that have been applied in sequence.
 */
public class SessionInfoCommand implements Command {

    /**
     * Retrieves the active session using {@link SessionManager}. If a valid session exists and contains
     * at least one image, the method prints: The sessions ID, All currently loaded images in the active session, and all
     * that will be applied after using {@link SaveCommand}.
     * If there is no active session or if the session is empty, a warning is printed instead.
     */
    @Override
    public void execute(String[] args) {
        Session session = SessionManager.getActiveSession();

        if (session == null || session.getImages().isEmpty()) {
            System.out.println("No active session or no images loaded.");
            return;
        }

        System.out.println("Session ID: " + session.getId());
        System.out.println("Images in session:");
        for (NetpbmImage image : session.getImages()) {
            System.out.println("  - " + image.getFileName());
        }

        System.out.println("Alternations:");
        if (session.getAlternations().isEmpty()) {
            System.out.println("  (none)");
        } else {
            for (String alt : session.getAlternations()) {
                System.out.println("  - " + alt);
            }
        }
    }
}
