package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.image.Pixel;
import netpbm.session.Session;
import netpbm.session.SessionManager;

/**
 * Command that rotates all images in the active session 90 degrees to the left or right.
 * <p>
 * Rotation is applied in-place to each image, updating both the pixel matrix and dimensions.
 * The session state is saved before modification.
 */
public class RotateCommand implements Command {

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

        for (NetPBMImages image : session.getImages()) {
            rotateImage(image, direction.equals("left"));
            System.out.println("Rotated image: " + image.getFileName() + " to the " + direction);
        }

        session.addAlternation("rotate " + direction);
    }

    /**
     * Rotates the image 90 degrees in the specified direction.
     *
     * @param image The image to rotate.
     * @param rotateLeft If true, rotate left; otherwise, rotate right.
     */
    private void rotateImage(NetPBMImages image, boolean rotateLeft) {
        int height = image.getHeight();
        int width = image.getWidth();
        Pixel[][] original = image.getPixels();
        Pixel[][] rotated = new Pixel[width][height]; // dimensions flipped

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel current = original[y][x].clone();

                if (rotateLeft) {
                    rotated[width - x - 1][y] = current;
                } else {
                    rotated[x][height - y - 1] = current;
                }
            }
        }

        // Update image state
        image.setPixels(rotated);
        image.setWidth(height);
        image.setHeight(width);
    }
}

