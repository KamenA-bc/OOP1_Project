package Commands;

import Sessions.Command;
import Sessions.Session;
import Sessions.SessionManager;

import java.util.List;

public class SessionInfo implements Command {
    private final SessionManager manager = SessionManager.getInstance();

    @Override
    public void execute(String fullInput) {
        int sessionId = manager.getActiveSessionId();
        if (sessionId == -1) {
            System.out.println("No active session.");
            return;
        }

        Session session = manager.getSession(sessionId);

        if (session == null) {
            System.out.println("Session not found.");
            return;
        }

        System.out.println("Session ID: " + session.getId());

        List<Netpbm.LoadedImage> images = session.getImages();
        if (images.isEmpty()) {
            System.out.println("No images loaded in this session.");
        } else {
            System.out.println("Images in session:");
            for (Netpbm.LoadedImage image : images) {
                System.out.println(" - " + image.getFilename());
            }
        }

        List<String> alternations = session.getAlternations();
        if (alternations.isEmpty()) {
            System.out.println("No alternations will be executed.");
        } else {
            System.out.println("Alternations pending:");
            for (String alternation : alternations) {
                System.out.println(" - " + alternation);
            }
        }
    }
}
