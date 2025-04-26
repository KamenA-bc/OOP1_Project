package Commands;

import Netpbm.Enums.Format;
import Netpbm.LoadedImage;
import Sessions.Command;
import Sessions.SessionManager;

import java.util.List;

public class Monochrome implements Command {
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
            if (isAlreadyMonochrome(image)) {
                System.out.println("Image " + image.getFilename() + " is already monochrome. Skipping.");
            } else {
                applyMonochrome(image);
                System.out.println("Monochrome applied to image: " + image.getFilename());
                applied = true;
            }
        }

        if (applied) {
            manager.addAlternationToSession(sessionId, "monochrome");
        }
    }

    private boolean isAlreadyMonochrome(LoadedImage image) {
        int[][][] data = image.getPixelData();
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int value;
                if (image.getFormat() == Format.PPM) {
                    int r = data[i][j][0];
                    int g = data[i][j][1];
                    int b = data[i][j][2];
                    if (r != g || g != b) {
                        return false; // colored pixel
                    }
                    value = r; // all channels same
                } else {
                    value = data[i][j][0];
                }

                if (value != 0 && value != image.getMaxVal()) {
                    return false; // not pure black or white
                }
            }
        }
        return true;
    }

    private void applyMonochrome(LoadedImage image) {
        int[][][] data = image.getPixelData();
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                int brightness;
                if (image.getFormat() == Format.PPM) {
                    int r = data[i][j][0];
                    int g = data[i][j][1];
                    int b = data[i][j][2];
                    brightness = (r + g + b) / 3;
                } else {
                    brightness = data[i][j][0];
                }

                int color = (brightness >= 128) ? image.getMaxVal() : 0;

                if (image.getFormat() == Format.PPM) {
                    data[i][j][0] = color;
                    data[i][j][1] = color;
                    data[i][j][2] = color;
                } else {
                    data[i][j][0] = color;
                }
            }
        }
    }
}
