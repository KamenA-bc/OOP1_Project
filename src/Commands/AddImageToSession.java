package Commands;

import Netpbm.*;
import Sessions.Command;
import Sessions.SessionManager;

import java.util.List;

public class AddImageToSession implements Command {
    private final SessionManager manager = SessionManager.getInstance();

    @Override
    public void execute(String fullInput) {
        String[] tokens = fullInput.trim().split("\\s+");

        if (tokens.length < 2) {
            System.out.println("Usage: add <filename1> <filename2> ...");
            return;
        }

        int sessionId = manager.getActiveSessionId();
        if (sessionId == -1) {
            System.out.println("No active session to add images to. Please load or switch to a session first.");
            return;
        }

        List<LoadedImage> loadedImages = NetPBMImageLoader.loadImagesFromTokens(tokens, 1);

        if (loadedImages.isEmpty()) {
            System.out.println("No valid image files were added.");
            return;
        }

        List<LoadedImage> currentImages = manager.getSessionImages(sessionId);

        for (LoadedImage image : loadedImages) {
            if (isAlreadyInSession(currentImages, image.getFilename())) {
                System.out.println("Image \"" + image.getFilename() + "\" is already in the session. Skipped.");
            } else {
                manager.addImageToSession(sessionId, image);
                System.out.println("Image \"" + image.getFilename() + "\" was added to the session.");
            }
        }
    }

    private boolean isAlreadyInSession(List<LoadedImage> currentImages, String filename) {
        for (LoadedImage img : currentImages) {
            if (img.getFilename().equals(filename)) {
                return true;
            }
        }
        return false;
    }
}

