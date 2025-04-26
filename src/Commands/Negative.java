package Commands;

import Netpbm.Enums.Format;
import Netpbm.LoadedImage;
import Sessions.Command;
import Sessions.SessionManager;

import java.util.List;

public class Negative implements Command {
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
            applyNegative(image);
            System.out.println("Negative applied to image: " + image.getFilename());
            applied = true;
        }

        if (applied) {
            manager.addAlternationToSession(sessionId, "negative");
        }
    }

    private void applyNegative(LoadedImage image) {
        int[][][] data = image.getPixelData();
        int maxVal = image.getMaxVal();

        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if (image.getFormat() == Format.PPM) {
                    data[i][j][0] = maxVal - data[i][j][0]; // Red
                    data[i][j][1] = maxVal - data[i][j][1]; // Green
                    data[i][j][2] = maxVal - data[i][j][2]; // Blue
                } else {
                    // PBM and PGM
                    data[i][j][0] = maxVal - data[i][j][0];
                }
            }
        }
    }
}
