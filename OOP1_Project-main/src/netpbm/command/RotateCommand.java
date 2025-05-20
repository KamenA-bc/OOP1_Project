package netpbm.command;

import netpbm.image.NetpbmImage;
import netpbm.session.Session;
import netpbm.session.SessionManager;

/**
 * Command that rotates all images in the active session 90 degrees to the left or right.
 * <p>
 * Rotation is applied in-place to each image, updating both the pixel matrix and dimensions.
 * The session state is saved before modification.
 */
public class RotateCommand implements Command {

    /**
     * Executes the rotate command.
     * <p>
     * Rotates all images in the current session based on the specified direction.
     *
     * @param args Command-line arguments. The second argument must be "left" or "right".
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: rotate <left|right>");
            return;
        }

        String direction = args[1].toLowerCase();
        if (!direction.equals("left") && !direction.equals("right")) {
            System.out.println("Direction must be 'left' or 'right'.");
            return;
        }

        Session session = SessionManager.getActiveSession();
        if (session == null || session.getImages().isEmpty()) {
            System.out.println("No active session with images.");
            return;
        }

        session.saveState();

        for (NetpbmImage image : session.getImages()) {
            rotateImage(image, direction.equals("left"));
            System.out.println("Rotated image: " + image.getFileName() + " to the " + direction);
        }

        session.addAlternation("rotate " + direction);
    }

    /**
     * Rotates the image 90 degrees in the specified direction.
     * <p>
     * For left rotation, the image is switched and columns are reversed.
     * For right rotation, the image is switched and rows are reversed.
     * The resulting pixel matrix replaces the original, and the width nad height are swapped in the new matrix.
     *
     * @param image      The image to rotate.
     * @param rotateLeft {@code true} to rotate 90° left, {@code false} to rotate right.
     */
    private void rotateImage(NetpbmImage image, boolean rotateLeft) {
        int height = image.getHeight();
        int width = image.getWidth();
        int channels = image.getPixels()[0][0].length;

        int[][][] original = image.getPixels();
        int[][][] rotated = new int[width][height][channels];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (int c = 0; c < channels; c++) {
                    if (rotateLeft) {
                        rotated[width - x - 1][y][c] = original[y][x][c];
                    } else {
                        rotated[x][height - y - 1][c] = original[y][x][c];
                    }
                }
            }
        }

        image.setPixels(rotated);
        image.setWidth(height);
        image.setHeight(width);
    }
}

