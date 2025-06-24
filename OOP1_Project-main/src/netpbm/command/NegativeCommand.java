package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.image.Pixel;
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
     * Applies the negative transformation to each image in the active session.
     *
     * @param args Not used.
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
     * Applies the negative filter to the given image.
     *
     * @param image The image to modify.
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

