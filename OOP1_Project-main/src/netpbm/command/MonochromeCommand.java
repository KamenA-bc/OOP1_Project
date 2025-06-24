package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.image.Pixel;
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

    @Override
    public void execute(String[] args) {
        Session session = SessionManager.getActiveSession();
        if (session == null || session.getImages().isEmpty()) {
            System.out.println("No active session with images.");
            return;
        }

        session.saveState();
        int converted = 0;

        for (NetPBMImages image : session.getImages()) {
            String format = image.getFormat();
            if (format.equals("P1")) continue; // Already monochrome

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
     * Checks if all pixel values are already 0 or 1.
     */
    private boolean isAlreadyBinary(NetPBMImages image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int val = image.getPixel(y, x).getRed();
                if (val != 0 && val != 1) return false;
            }
        }
        return true;
    }

    /**
     * Converts an image to pure black & white using a brightness threshold.
     * Pixels are set to 1 if brightness > 127, else 0.
     * The format is also changed to "P1" to indicate PBM.
     */
    private void convertToMonochrome(NetPBMImages image) {
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                Pixel p = image.getPixel(y, x);
                int brightness = (p.getRed() + p.getGreen() + p.getBlue()) / 3;
                int binary = (brightness > 127) ? 1 : 0;
                image.setPixel(y, x, new Pixel(binary));
            }
        }

        image.setFormat("P1");
    }
}



