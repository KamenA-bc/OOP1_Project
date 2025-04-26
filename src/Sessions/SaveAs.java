package Sessions;

import Netpbm.LoadedImage;
import Netpbm.NetpbmImageSaver;

import java.io.IOException;
import java.util.List;

public class SaveAs implements Command {
    private final SessionManager manager = SessionManager.getInstance();

    @Override
    public void execute(String fullInput) {
        String[] tokens = fullInput.trim().split("\\s+");
        if (tokens.length != 2) {
            System.out.println("Usage: saveas <new_filename>");
            return;
        }

        int sessionId = manager.getActiveSessionId();
        List<LoadedImage> images = manager.getSessionImages(sessionId);

        if (images.isEmpty()) {
            System.out.println("No images to save in the current session.");
            return;
        }

        LoadedImage firstImage = images.get(0);
        String newName = tokens[1];

        try {
            NetpbmImageSaver.save(firstImage, newName);
            System.out.println("Saved as: " + newName);
        } catch (IOException e) {
            System.out.println("Failed to save: " + e.getMessage());
        }
    }
}