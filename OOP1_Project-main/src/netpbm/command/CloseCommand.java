package netpbm.command;

import netpbm.session.Session;
import netpbm.session.SessionManager;

/**
 * Command that removes a specific image from the active session by its file name.
 * If the image is not found in the current session, an appropriate message is displayed.
 */
public class CloseCommand implements Command {

    /**
     * Attempts to remove the specified image (by file name) from the currently active session.
     * If no session is active or the image is not present, an error message is shown.
     *
     * @param args Command-line arguments. Expects the file name of the image to close.
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: close <filename>");
            return;
        }

        Session session = SessionManager.getActiveSession();
        if (session == null) {
            System.out.println("No active session.");
            return;
        }

        boolean removed = session.removeImageByName(args[1]);
        if (removed) {
            System.out.println("Removed " + args[1] + " from the active session.");
        } else {
            System.out.println("Image not found in the active session: " + args[1]);
        }
    }
}
