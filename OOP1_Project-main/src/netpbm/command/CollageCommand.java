package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.image.PPMImage;
import netpbm.image.Pixel;
import netpbm.session.Session;
import netpbm.session.SessionManager;
import netpbm.image.PGMImage;
import netpbm.image.PBMImage;



/**
 * Command for creating a collage from two loaded images.
 * <p>
 * Supports both horizontal and vertical collages. The images must have
 * identical dimensions and be present in the current session.
 * </p>
 */
public class CollageCommand implements Command {

    /**
     * Executes the collage command.
     * <p>
     * Expects arguments: {@code collage <horizontal|vertical> <image1> <image2> <output>}.
     * Combines two images from the current session into a new one, depending on the specified direction.
     * </p>
     *
     * @param args command-line arguments specifying direction, image names, and output file name
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 5) {
            System.out.println("Usage: collage <horizontal|vertical> <image1> <image2> <output>");
            return;
        }

        String direction = args[1].toLowerCase();
        String file1 = args[2];
        String file2 = args[3];
        String output = args[4];

        if (!direction.equals("horizontal") && !direction.equals("vertical")) {
            System.out.println("Invalid direction. Must be 'horizontal' or 'vertical'.");
            return;
        }

        Session session = SessionManager.getActiveSession();
        if (session == null) {
            System.out.println("No active session.");
            return;
        }

        NetPBMImages img1 = null;
        NetPBMImages img2 = null;

        for (NetPBMImages img : session.getImages()) {
            if (img.getFileName().equalsIgnoreCase(file1)) {
                img1 = img;
            } else if (img.getFileName().equalsIgnoreCase(file2)) {
                img2 = img;
            }
        }

        if (img1 == null || img2 == null) {
            System.out.println("Both images must be loaded in the session.");
            return;
        }

        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            System.out.println("Images must have the same width and height.");
            return;
        }

        session.saveState();

        NetPBMImages result;
        if (direction.equals("horizontal")) {
            result = createHorizontalCollage(img1, img2);
        } else {
            result = createVerticalCollage(img1, img2);
        }

        result.setFileName(output);
        session.addImage(result);
        session.addAlternation("collage " + direction);
        System.out.println("Collage created: " + output);
    }

    /**
     * Combines two images horizontally by placing them side by side.
     */
    private NetPBMImages createHorizontalCollage(NetPBMImages img1, NetPBMImages img2) {
        int height = img1.getHeight();
        int width = img1.getWidth() * 2;
        Pixel[][] resultPixels = new Pixel[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                resultPixels[y][x] = img1.getPixel(y, x).clone();
                resultPixels[y][x + img1.getWidth()] = img2.getPixel(y, x).clone();
            }
        }

        return createTypedImage(width, height, resultPixels);
    }

    /**
     * Combines two images vertically by placing one below the other.
     */
    private NetPBMImages createVerticalCollage(NetPBMImages img1, NetPBMImages img2) {
        int width = img1.getWidth();
        int height = img1.getHeight() * 2;
        Pixel[][] resultPixels = new Pixel[height][width];

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < width; x++) {
                resultPixels[y][x] = img1.getPixel(y, x).clone();
            }
        }

        for (int y = 0; y < img2.getHeight(); y++) {
            for (int x = 0; x < width; x++) {
                resultPixels[y + img1.getHeight()][x] = img2.getPixel(y, x).clone();
            }
        }

        return createTypedImage(width, height, resultPixels);
    }

    /**
     * Creates a new image of the appropriate type (PBM, PGM, or PPM) based on pixel content.
     */
    private NetPBMImages createTypedImage(int width, int height, Pixel[][] pixels) {
        if (isOnlyBlackWhite(pixels)) {
            return new PBMImage(width, height, pixels);
        } else if (isGrayscale(pixels)) {
            return new PGMImage(width, height, 255, pixels);
        } else {
            return new PPMImage(width, height, 255, pixels);
        }
    }

    /**
     * Checks whether the image contains only black (0) and white (1) pixels.
     */
    private boolean isOnlyBlackWhite(Pixel[][] pixels) {
        for (Pixel[] row : pixels) {
            for (Pixel p : row) {
                int r = p.getRed();
                if ((r != 0 && r != 1) || p.getRed() != p.getGreen() || p.getRed() != p.getBlue()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks whether the image is grayscale (all RGB values are equal).
     */
    private boolean isGrayscale(Pixel[][] pixels) {
        for (Pixel[] row : pixels) {
            for (Pixel p : row) {
                if (p.getRed() != p.getGreen() || p.getRed() != p.getBlue()) {
                    return false;
                }
            }
        }
        return true;
    }
}


