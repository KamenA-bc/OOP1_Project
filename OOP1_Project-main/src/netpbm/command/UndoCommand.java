package netpbm.command;

import netpbm.session.Session;
import netpbm.session.SessionManager;

/**
 * Command that reverts the last image modification in the active session.
 * <p>
 * Uses the session's saved history to restore the previous state of all images.
 * If no prior state is available, the command does nothing.
 */
public class UndoCommand implements Command {

    /**
     * Executes the undo command.
     * <p>
     * Restores the previous state of all images in the active session by popping
     * a snapshot from the session's internal history stack. This snapshot contains
     * deep clones of the images from before the last operation. If the stack is empty,
     * the command does nothing and notifies the user.
     */
    @Override
    public void execute(String[] args) {
        Session session = SessionManager.getActiveSession();
        if (session == null) {
            System.out.println("No active session.");
            return;
        }

        boolean success = session.undo();
        if (success) {
            System.out.println("Undo successful.");
        } else {
            System.out.println("Nothing to undo.");
        }
    }
}
