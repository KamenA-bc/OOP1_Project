import java.util.*;

public class SessionManager {

    private static class Session {
        private final int sessionId;
        private final List<String> images;

        Session(int sessionId, List<String> images) {
            this.sessionId = sessionId;
            this.images = images;
        }

        public int getSessionId() {
            return sessionId;
        }

        public List<String> getImages() {
            return images;
        }
    }

    private final Map<Integer, Session> sessions = new HashMap<>();
    private Session currentSession;
    private int nextSessionId = 1;

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter command: ");
            String inputLine = scanner.nextLine().trim();
            String[] parts = inputLine.split("\\s+");
            String command = parts[0];

            switch (command) {
                case "load":
                    if (parts.length < 2) {
                        System.out.println("You must specify at least one image.");
                        break;
                    }

                    List<String> images = new ArrayList<>(Arrays.asList(parts).subList(1, parts.length));
                    currentSession = new Session(nextSessionId++, images);
                    sessions.put(currentSession.getSessionId(), currentSession);

                    System.out.println("Session started with ID: " + currentSession.getSessionId());
                    System.out.println("Loaded images: " + currentSession.getImages());
                    break;

                case "save":
                    // Implement save logic
                    break;

                case "saveas":
                    // Implement save as logic
                    break;

                case "close":
                    currentSession = null;
                    System.out.println("Session closed.");
                    break;

                case "help":
                    // Provide help details
                    break;

                case "exit":
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }

    public static void main(String[] args) {
        SessionManager manager = new SessionManager();
        manager.start();
    }
}