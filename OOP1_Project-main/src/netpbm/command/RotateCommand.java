package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.image.Pixel;
import netpbm.session.Session;
import netpbm.session.SessionManager;

import java.util.ArrayList;
import java.util.List;


/**
 * Command for rotating all images in the active session.
 * <p>
 * Supports 90-degree rotation in either the left (counterclockwise)
 * or right (clockwise) direction. Updates each image in place.
 * </p>
 */
public class RotateCommand implements Command {

    /**
     * Executes the rotate command.
     * Rotates all images in the current session 90 degrees left or right.
     *
     * @param args expects: rotate <left|right>
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

        for (NetPBMImages image : session.getImages()) {
            rotateImage(image, direction.equals("left"));
            System.out.println("Rotated image: " + image.getFileName() + " to the " + direction);
        }

        session.addAlternation("rotate " + direction);
    }

    /**
     * Rotates an image 90 degrees in the specified direction.
     *
     * @param image      the image to rotate
     * @param rotateLeft true to rotate counterclockwise; false for clockwise
     */
    private void rotateImage(NetPBMImages image, boolean rotateLeft) {
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();
        List<Pixel> newPixels = new ArrayList<>();

        for (Pixel p : image.getPixels()) {
            int x = p.getX();
            int y = p.getY();

            int newX, newY;
            if (rotateLeft) {
                newX = y;
                newY = originalWidth - 1 - x;
            } else {
                newX = originalHeight - 1 - y;
                newY = x;
            }

            Pixel rotatedPixel = new Pixel(newX, newY, p.getRed(), p.getGreen(), p.getBlue());
            newPixels.add(rotatedPixel);
        }

        image.setPixels(newPixels);
        image.setWidth(originalHeight);
        image.setHeight(originalWidth);
    }
}


