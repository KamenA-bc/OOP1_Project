package Commands;

import Sessions.Command;
import Sessions.Session;
import Sessions.SessionManager;

import java.util.List;

public class Undo implements Command {
    private final SessionManager manager = SessionManager.getInstance();

    @Override
    public void execute(String fullInput) {
        int sessionId = manager.getActiveSessionId();
        if (sessionId == -1) {
            System.out.println("No active session to undo an alternation.");
            return;
        }

        Session session = manager.getSession(sessionId);
        if (session == null) {
            System.out.println("No active session to undo.");
            return;
        }

        List<String> alternations = session.getAlternations();
        List<List<int[][][]>> backups = session.getImageBackups();
        List<Integer> widthBackups = session.getWidthBackups();
        List<Integer> heightBackups = session.getHeightBackups();

        if (alternations.isEmpty() || backups.isEmpty() || widthBackups.isEmpty() || heightBackups.isEmpty()) {
            System.out.println("No alternations to undo.");
            return;
        }

        // Remove last alternation
        String removed = alternations.removeLast();

        // Restore last backup
        List<int[][][]> lastBackup = backups.removeLast();
        int restoredWidth = widthBackups.removeLast();
        int restoredHeight = heightBackups.removeLast();

        List<Netpbm.LoadedImage> images = session.getImages();
        for (int i = 0; i < images.size(); i++) {
            images.get(i).setPixelData(lastBackup.get(i));
            images.get(i).setWidth(restoredWidth);
            images.get(i).setHeight(restoredHeight);
        }

        System.out.println("Undid alternation: " + removed);
    }
}

