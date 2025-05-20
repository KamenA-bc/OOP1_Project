package netpbm.command;

import netpbm.image.NetpbmImage;
import netpbm.session.Session;
import netpbm.session.SessionManager;

/**
 * Command that converts all grayscale (P2) and color (P3) images in the active session to monochrome (black and white).
 * <p>
 * Images already in binary form (containing only 0 and 1 values) are skipped.
 * The conversion is based on the average brightness, using a threshold of 127.
 * Converted images are also updated to use the PBM format (P1).
 */
public class MonochromeCommand implements Command {

    /**
     * Executes the monochrome command.
     * <p>
     * Converts eligible P2 and P3 images to black and white using a fixed threshold.
     * Skips images that are already binary or not in grayscale/color format.
     *
     */
    @Override
    public void execute(String[] args) {
        Session session = SessionManager.getActiveSession();
        if (session == null || session.getImages().isEmpty()) {
            System.out.println("No active session with images.");
            return;
        }

        session.saveState();
        int converted = 0;

        for (NetpbmImage image : session.getImages()) {
            String format = image.getFormat();
            if (!format.equals("P3") && !format.equals("P2")) {
                continue;
            }

            if (isAlreadyBinary(image)) {
                System.out.println("Image '" + image.getFileName() + "' is already black and white. Skipping.");
                continue;
            }

            convertToMonochrome(image);
            System.out.println("Converted to black and white: " + image.getFileName());
            converted++;
        }

        if (converted > 0) {
            session.addAlternation("monochrome");
        } else {
            System.out.println("No images were converted.");
        }
    }

    /**
     * Checks if the image already contains only binary pixel values (0 or 1).
     *
     * @param image The image to check.
     * @return {@code true} if all pixels are 0 or 1, otherwise {@code false}.
     */
    private boolean isAlreadyBinary(NetpbmImage image) {
        int[][][] pixels = image.getPixels();

        for (int[][] row : pixels) {
            for (int[] px : row) {
                int val = px[0];
                if (!(val == 0 || val == 1)) return false;
            }
        }

        return true;
    }

    /**
     * Converts the image to monochrome using a brightness threshold.
     * Updates all pixels to 0 (black) or 1 (white) and changes the format to P1.
     *
     * @param image The image to convert.
     */
    private void convertToMonochrome(NetpbmImage image) {
        int[][][] pixels = image.getPixels();
        int channels = pixels[0][0].length;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int gray;

                if (channels == 1) {
                    gray = pixels[y][x][0];
                } else {
                    int r = pixels[y][x][0];
                    int g = pixels[y][x][1];
                    int b = pixels[y][x][2];
                    gray = (r + g + b) / 3;
                }

                int binary = (gray > 127) ? 1 : 0;
                pixels[y][x] = new int[]{binary};
            }
        }

        image.setFormat("P1");
    }
}



