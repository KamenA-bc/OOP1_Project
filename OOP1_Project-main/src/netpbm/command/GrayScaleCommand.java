package netpbm.command;

import netpbm.image.NetpbmImage;
import netpbm.session.Session;
import netpbm.session.SessionManager;

/**
 * Command that converts all color (P3) images in the active session to grayscale.
 * <p>
 * Grayscale conversion is applied by averaging the red, green, and blue channel values.
 * Images that are not in color format (P3) are skipped. The session state is saved before modification.
 */
public class GrayScaleCommand implements Command {

    /**
     * Executes the grayscale command.
     * <p>
     * Converts each P3 image in the current session to grayscale and logs the operation.
     * Non-color images are ignored with a warning message.
     *
     * @param args Command-line arguments (not used).
     */
    @Override
    public void execute(String[] args) {
        Session session = SessionManager.getActiveSession();
        if (session == null || session.getImages().isEmpty()) {
            System.out.println("No active session with images.");
            return;
        }

        session.saveState();

        for (NetpbmImage image : session.getImages()) {
            if (!image.getFormat().equals("P3")) {
                System.out.println("Skipped (not color): " + image.getFileName());
                continue;
            }

            convertToGrayscale(image);
            System.out.println("Converted to grayscale: " + image.getFileName());
        }

        session.addAlternation("grayscale");
    }

    /**
     * Converts the given image to grayscale by averaging its RGB values.
     *
     * @param image The image to convert.
     */
    private void convertToGrayscale(NetpbmImage image) {
        int[][][] pixels = image.getPixels();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int r = pixels[y][x][0];
                int g = pixels[y][x][1];
                int b = pixels[y][x][2];
                int gray = (r + g + b) / 3;
                pixels[y][x][0] = gray;
                pixels[y][x][1] = gray;
                pixels[y][x][2] = gray;
            }
        }
    }
}


