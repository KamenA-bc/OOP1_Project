package netpbm.command;

import netpbm.image.NetpbmImage;
import netpbm.session.Session;
import netpbm.session.SessionManager;

/**
 * Command that inverts the pixel values of all images in the active session, excluding PBM (P1) images.
 * <p>
 * For each channel value, the negative is calculated as {@code maxVal - currentValue}.
 * PBM images are skipped since they only contain binary values (0 or 1).
 * The session state is saved before modification.
 */
public class NegativeCommand implements Command {

    /**
     * Executes the negative command.
     * <p>
     * Applies a color inversion to all P2 and P3 images in the active session.
     * Skips unsupported P1 (PBM) images and logs actions to the console.
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
        int inverted = 0;

        for (NetpbmImage image : session.getImages()) {
            String format = image.getFormat();

            if ("P1".equals(format)) {
                System.out.println("Skipping PBM image: " + image.getFileName());
                continue;
            }

            applyNegative(image);
            System.out.println("Negative applied to: " + image.getFileName());
            inverted++;
        }

        if (inverted > 0) {
            session.addAlternation("negative");
        } else {
            System.out.println("No images were modified.");
        }
    }

    /**
     * Inverts the pixel values of the given image.
     *
     * @param image The image to process.
     */
    private void applyNegative(NetpbmImage image) {
        int[][][] pixels = image.getPixels();
        int maxVal = image.getMaxVal();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                for (int c = 0; c < pixels[y][x].length; c++) {
                    pixels[y][x][c] = maxVal - pixels[y][x][c];
                }
            }
        }
    }
}

