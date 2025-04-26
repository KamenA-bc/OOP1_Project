package Commands;


import Netpbm.Enums.Format;
import Netpbm.LoadedImage;
import Sessions.Command;
import Sessions.SessionManager;

import java.util.List;

public class Rotate implements Command {
    private final SessionManager manager = SessionManager.getInstance();

    @Override
    public void execute(String fullInput) {
        String[] tokens = fullInput.trim().split("\\s+");

        if (tokens.length != 2) {
            System.out.println("Usage: rotate <direction>");
            return;
        }

        String direction = tokens[1].toLowerCase();
        if (!direction.equals("left") && !direction.equals("right")) {
            System.out.println("Invalid direction. Use 'left' or 'right'.");
            return;
        }

        int sessionId = manager.getActiveSessionId();
        List<LoadedImage> images = manager.getSessionImages(sessionId);

        if (images.isEmpty()) {
            System.out.println("No images to modify in the current session.");
            return;
        }

        manager.saveImageBackup(sessionId);

        for (LoadedImage image : images) {
            rotateImage(image, direction);
            System.out.println("Rotated image " + image.getFilename() + " to the " + direction + ".");
        }

        manager.addAlternationToSession(sessionId, "rotate " + direction);
    }

    private void rotateImage(LoadedImage image, String direction) {
        int oldHeight = image.getHeight();
        int oldWidth = image.getWidth();
        int channels = (image.getFormat() == Format.PPM) ? 3 : 1;

        int[][][] oldData = image.getPixelData();
        int[][][] newData = new int[oldWidth][oldHeight][channels];

        if (direction.equals("right")) {
            for (int y = 0; y < oldHeight; y++) {
                for (int x = 0; x < oldWidth; x++) {
                    for (int c = 0; c < channels; c++) {
                        newData[x][oldHeight - 1 - y][c] = oldData[y][x][c];
                    }
                }
            }
        } else {
            for (int y = 0; y < oldHeight; y++) {
                for (int x = 0; x < oldWidth; x++) {
                    for (int c = 0; c < channels; c++) {
                        newData[oldWidth - 1 - x][y][c] = oldData[y][x][c];
                    }
                }
            }
        }

        image.setPixelData(newData);
        image.setWidth(oldHeight);
        image.setHeight(oldWidth);
    }
}
