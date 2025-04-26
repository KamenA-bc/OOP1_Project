package Commands;


import Netpbm.Enums.Format;
import Netpbm.LoadedImage;
import Sessions.Command;
import Sessions.SessionManager;

import java.util.List;

public class Grayscale implements Command {
    private final SessionManager manager = SessionManager.getInstance();

    @Override
    public void execute(String fullInput) {
        int sessionId = manager.getActiveSessionId();
        List<LoadedImage> images = manager.getSessionImages(sessionId);

        if (images.isEmpty()) {
            System.out.println("No images to modify in the current session.");
            return;
        }

        manager.saveImageBackup(sessionId);

        boolean applied = false;

        for (LoadedImage image : images) {
            if (image.getFormat() == Format.PPM) {
                if (isAlreadyGrayscale(image)) {
                    System.out.println("Image " + image.getFilename() + " is already grayscale. Skipping.");
                } else {
                    applyGrayscale(image);
                    System.out.println("Grayscale applied to image: " + image.getFilename());
                    applied = true;
                }
            }
        }

        if (applied) {
            manager.addAlternationToSession(sessionId, "grayscale");
        }

    }

        private boolean isAlreadyGrayscale(LoadedImage image) {
            int[][][] data = image.getPixelData();
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    int r = data[i][j][0];
                    int g = data[i][j][1];
                    int b = data[i][j][2];
                    if (r != g || g != b) {
                        return false; // found colored pixel
                    }
                }
            }
            return true; // all pixels are grayscale
        }

        private void applyGrayscale(LoadedImage image) {
            int[][][] data = image.getPixelData();
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    int r = data[i][j][0];
                    int g = data[i][j][1];
                    int b = data[i][j][2];
                    int gray = (r + g + b) / 3;
                    data[i][j][0] = gray;
                    data[i][j][1] = gray;
                    data[i][j][2] = gray;
                }
            }
        }
    }
