package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.image.PPMImage;
import netpbm.image.Pixel;
import netpbm.session.Session;
import netpbm.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Command that converts all color (P3) images in the active session to grayscale.
 * <p>
 * Grayscale conversion is applied by averaging the red, green, and blue channel values.
 * Images that are not in color format (P3) are skipped. The session state is saved before modification.
 */
public class GrayscaleCommand implements Command {

    /**
     * Executes the grayscale command.
     * Converts each PPM image in the current session to grayscale.
     * Non-color images are skipped with a notice.
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

        for (NetPBMImages image : session.getImages()) {
            if (!(image instanceof PPMImage)) {
                System.out.println("Skipped (not color): " + image.getFileName());
                continue;
            }

            convertToGrayscale((PPMImage) image);
            System.out.println("Converted to grayscale: " + image.getFileName());
        }

        session.addAlternation("grayscale");
    }

    /**
     * Converts the given PPM image to grayscale by averaging its RGB values.
     *
     * @param image the color image to convert
     */
    private void convertToGrayscale(PPMImage image) {
        List<Pixel> newPixels = new ArrayList<>();

        for (Pixel p : image.getPixels()) {
            int avg = (p.getRed() + p.getGreen() + p.getBlue()) / 3;
            newPixels.add(new Pixel(p.getX(), p.getY(), avg, avg, avg));
        }

        image.setPixels(newPixels);
    }
}


