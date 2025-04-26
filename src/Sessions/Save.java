package Sessions;

import Netpbm.LoadedImage;
import Netpbm.NetpbmImageSaver;

import java.io.IOException;
import java.util.List;

public class Save implements Command {
    private final SessionManager manager = SessionManager.getInstance();

    @Override
    public void execute(String fullInput) {
        int sessionId = manager.getActiveSessionId();
        List<LoadedImage> images = manager.getSessionImages(sessionId);

        if (images.isEmpty()) {
            System.out.println("No images to save in the current session.");
            return;
        }

        for (LoadedImage image : images) {
            try {
                NetpbmImageSaver.save(image, image.getFilename());
                System.out.println("Saved: " + image.getFilename());
            } catch (IOException e) {
                System.out.println("Failed to save " + image.getFilename() + ": " + e.getMessage());
            }
        }
    }
}