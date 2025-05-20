package netpbm.command;

import netpbm.image.NetpbmImage;
import netpbm.session.Session;
import netpbm.session.SessionManager;

/**
 * Command that creates a collage from two loaded images in the active session.
 * <p>
 * Supports horizontal and vertical collage directions. The two input images must have
 * the same dimensions and be present in the current session. A new image is created
 * by placing the two side-by-side (horizontal) or one above the other (vertical),
 * and added to the session under the specified output file name.
 */
public class CollageCommand implements Command {

    /**
     * Executes the collage command.
     * <p>
     * Usage: {@code collage <horizontal|vertical> <image1> <image2> <output>}
     * Finds the two specified images in the active session, validates their dimensions,
     * and creates a new collage image based on the given direction. The resulting image
     * is added to the session with the provided output file name.
     *
     * @param args Command-line arguments specifying the direction, image names, and output name.
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

        NetpbmImage img1 = null;
        NetpbmImage img2 = null;

        for (NetpbmImage img : session.getImages()) {
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

        NetpbmImage result;
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
     * Creates a horizontal collage by placing two images side by side.
     */
    private NetpbmImage createHorizontalCollage(NetpbmImage img1, NetpbmImage img2) {
        int height = img1.getHeight();
        int width = img1.getWidth() * 2;
        int channels = Math.max(img1.getPixels()[0][0].length, img2.getPixels()[0][0].length);
        NetpbmImage result = new NetpbmImage("P3", width, height, 255, channels);

        int[][][] p1 = img1.getPixels();
        int[][][] p2 = img2.getPixels();
        int[][][] out = result.getPixels();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < img1.getWidth(); x++) {
                copyPixel(p1[y][x], out[y][x]);
                copyPixel(p2[y][x], out[y][x + img1.getWidth()]);
            }
        }

        return result;
    }

    /**
     * Creates a vertical collage by placing two images one above the other.
     */
    private NetpbmImage createVerticalCollage(NetpbmImage img1, NetpbmImage img2) {
        int width = img1.getWidth();
        int height = img1.getHeight() * 2;
        int channels = Math.max(img1.getPixels()[0][0].length, img2.getPixels()[0][0].length);
        NetpbmImage result = new NetpbmImage("P3", width, height, 255, channels);

        int[][][] p1 = img1.getPixels();
        int[][][] p2 = img2.getPixels();
        int[][][] out = result.getPixels();

        for (int y = 0; y < img1.getHeight(); y++) {
            for (int x = 0; x < width; x++) {
                copyPixel(p1[y][x], out[y][x]);
            }
        }

        for (int y = 0; y < img2.getHeight(); y++) {
            for (int x = 0; x < width; x++) {
                copyPixel(p2[y][x], out[y + img1.getHeight()][x]);
            }
        }

        return result;
    }

    /**
     * Copies the values from one pixel array into another.
     * If the destination has more channels, missing values are duplicated from the first source channel.
     *
     * @param source The source pixel array.
     * @param dest   The destination pixel array.
     */
    private void copyPixel(int[] source, int[] dest) {
        for (int i = 0; i < dest.length; i++) {
            dest[i] = (i < source.length) ? source[i] : source[0];
        }
    }
}

