package Commands;


import Sessions.Command;
import Sessions.SessionManager;

public class SwitchSession implements Command {
    private final SessionManager manager = SessionManager.getInstance();

    @Override
    public void execute(String fullInput) {
        String[] tokens = fullInput.trim().split("\\s+");

        if (tokens.length != 2) {
            System.out.println("Usage: switch <sessionId>");
            return;
        }

        try {
            int sessionId = Integer.parseInt(tokens[1]);
            if (manager.getAllSessions().containsKey(sessionId) && sessionId != manager.getActiveSessionId()) {
                manager.setActiveSessionId(sessionId);
                System.out.println("Switched to session " + sessionId + ".");
            }
            else if (manager.getActiveSessionId() == sessionId) {
                System.out.println("Cannot switch to " + sessionId + " because it is already active.");
            }
            else {
                System.out.println("Error: Session " + sessionId + " does not exist.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid session ID. Please enter a valid number.");
        }
    }
}