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
            if (image.getFormat().equals("P1")) continue;

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
     * Checks if all pixel RGB values are either (0,0,0) or (1,1,1).
     */
    private boolean isAlreadyBinary(NetPBMImages image) {
        for (Pixel p : image.getPixels()) {
            int r = p.getRed(), g = p.getGreen(), b = p.getBlue();
            if (!((r == 0 && g == 0 && b == 0) || (r == 1 && g == 1 && b == 1))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts the image to black & white (PBM) using a threshold on brightness.
     * Sets all three RGB channels to 0 or 1.
     */
    private void convertToMonochrome(NetPBMImages image) {
        for (Pixel p : image.getPixels()) {
            int brightness = (p.getRed() + p.getGreen() + p.getBlue()) / 3;
            int binary = brightness > 127 ? 1 : 0;
            p.setRed(binary);
            p.setGreen(binary);
            p.setBlue(binary);
        }

        image.setFormat("P1");
    }
}




