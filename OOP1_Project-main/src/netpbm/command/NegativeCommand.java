package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.image.Pixel;
import netpbm.session.Session;
import netpbm.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

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
     * Inverts all images in the current session by modifying pixel values
     * based on their format. Stores the previous state for undo functionality.
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
     * For PBM: flips 0 and 1.
     * For PGM: subtracts grayscale from maxVal.
     * For PPM: inverts each RGB channel.
     *
     * @param image the image to transform
     */
    private void applyNegative(NetPBMImages image) {
        int maxVal = image.getMaxVal();
        List<Pixel> updated = new ArrayList<>();

        for (Pixel p : image.getPixels()) {
            int x = p.getX();
            int y = p.getY();

            if (image.getFormat().equals("P1")) {
                int inverted = (p.getRed() == 0) ? 1 : 0;
                updated.add(new Pixel(x, y, inverted));
            } else if (image.getFormat().equals("P2")) {
                int inverted = maxVal - p.getRed();
                updated.add(new Pixel(x, y, inverted));
            } else if (image.getFormat().equals("P3")) {
                int r = maxVal - p.getRed();
                int g = maxVal - p.getGreen();
                int b = maxVal - p.getBlue();
                updated.add(new Pixel(x, y, r, g, b));
            }
        }

        image.setPixels(updated);
    }
}

