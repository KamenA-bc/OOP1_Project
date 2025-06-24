package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.image.Pixel;
import netpbm.session.Session;
import netpbm.session.SessionManager;

/**
 * Command for applying a negative (color inversion) effect to all images in the active session.
 * <p>
 * Supports inversion for binary (PBM), grayscale (PGM), and color (PPM) images.
 * The original state is saved to allow undo.
 * </p>
 */
public class NegativeCommand implements Command {

    /**
     * Executes the negative command.
     * <p>
     * Inverts all images in the current session by modifying pixel values
     * based on their format. Stores the previous state for undo functionality.
     * </p>
     *
     * @param args not used for this command
     */
    @Override
    public void execute(String[] args) {
        Session session = SessionManager.getActiveSession();
        if (session == null || session.getImages().isEmpty()) {
            System.out.println("No active session with images.");
            return;
        }

        session.saveState(); // Enable undo

        for (NetPBMImages image : session.getImages()) {
            applyNegative(image);
            System.out.println("Inverted: " + image.getFileName());
        }

        session.addAlternation("negative");
    }

    /**
     * Applies a negative transformation to the specified image.
     * <p>
     * For PBM: flips 0 to 1 and vice versa.
     * For PGM: subtracts pixel value from max grayscale value.
     * For PPM: inverts each RGB channel independently.
     * </p>
     *
     * @param image the image to transform
     */
    private void applyNegative(NetPBMImages image) {
        int maxVal = image.getMaxVal();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Pixel p = image.getPixel(y, x);

                if (image.getFormat().equals("P1")) {
                    int inverted = (p.getRed() == 0) ? 1 : 0;
                    image.setPixel(y, x, new Pixel(inverted));
                } else if (image.getFormat().equals("P2")) {
                    int inverted = maxVal - p.getRed();
                    image.setPixel(y, x, new Pixel(inverted));
                } else if (image.getFormat().equals("P3")) {
                    int r = maxVal - p.getRed();
                    int g = maxVal - p.getGreen();
                    int b = maxVal - p.getBlue();
                    image.setPixel(y, x, new Pixel(r, g, b));
                }
            }
        }
    }
}

