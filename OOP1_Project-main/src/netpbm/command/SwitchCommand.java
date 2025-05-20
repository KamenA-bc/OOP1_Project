package netpbm.command;

import netpbm.session.SessionManager;

/**
 * Command that switches the active session to a specified session ID.
 */
public class SwitchCommand implements Command {

    /**
     * Executes the switch command with a given session ID.
     * <p>
     * If the session exists, it becomes the new active session.
     *
     * @param args Command-line arguments. Expects exactly one session ID after the command name.
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: switch <session_id>");
            return;
        }

        try {
            int id = Integer.parseInt(args[1]);
            if (SessionManager.switchTo(id)) {
                System.out.println("Switched to session " + id);
            } else {
                System.out.println("Session ID " + id + " does not exist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Session ID must be a number.");
        }
    }
}
