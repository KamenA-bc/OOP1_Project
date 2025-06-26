package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.image.PPMImage;
import netpbm.image.Pixel;
import netpbm.session.Session;
import netpbm.session.SessionManager;
import netpbm.image.PGMImage;
import netpbm.image.PBMImage;

import java.util.ArrayList;
import java.util.List;


/**
 * Command for creating a collage from two loaded images.
 * <p>
 * Supports both horizontal and vertical collages. The images must have
 * identical dimensions and be present in the current session.
 * </p>
 */
public class CollageCommand implements Command {

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

        NetPBMImages img1 = findImageByName(session, file1);
        NetPBMImages img2 = findImageByName(session, file2);

        if (img1 == null || img2 == null) {
            System.out.println("Both images must be loaded in the session.");
            return;
        }

        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            System.out.println("Images must have the same dimensions.");
            return;
        }

        session.saveState();

        NetPBMImages result = direction.equals("horizontal")
                ? buildHorizontalCollage(img1, img2)
                : buildVerticalCollage(img1, img2);

        result.setFileName(output);
        session.addImage(result);
        session.addAlternation("collage " + direction);
        System.out.println("Collage created: " + output);
    }

    private NetPBMImages findImageByName(Session session, String name) {
        for (NetPBMImages img : session.getImages()) {
            if (img.getFileName().equalsIgnoreCase(name)) {
                return img;
            }
        }
        return null;
    }

    private NetPBMImages buildHorizontalCollage(NetPBMImages img1, NetPBMImages img2) {
        int newWidth = img1.getWidth() * 2;
        int height = img1.getHeight();
        List<Pixel> resultPixels = new ArrayList<>();

        for (Pixel p : img1.getPixels()) {
            resultPixels.add(new Pixel(p.getX(), p.getY(), p.getRed(), p.getGreen(), p.getBlue()));
        }

        for (Pixel p : img2.getPixels()) {
            int newX = p.getX() + img1.getWidth();
            resultPixels.add(new Pixel(newX, p.getY(), p.getRed(), p.getGreen(), p.getBlue()));
        }

        return buildTypedImage(newWidth, height, resultPixels);
    }

    private NetPBMImages buildVerticalCollage(NetPBMImages img1, NetPBMImages img2) {
        int width = img1.getWidth();
        int newHeight = img1.getHeight() * 2;
        List<Pixel> resultPixels = new ArrayList<>();

        for (Pixel p : img1.getPixels()) {
            resultPixels.add(new Pixel(p.getX(), p.getY(), p.getRed(), p.getGreen(), p.getBlue()));
        }

        for (Pixel p : img2.getPixels()) {
            int newY = p.getY() + img1.getHeight();
            resultPixels.add(new Pixel(p.getX(), newY, p.getRed(), p.getGreen(), p.getBlue()));
        }

        return buildTypedImage(width, newHeight, resultPixels);
    }

    private NetPBMImages buildTypedImage(int width, int height, List<Pixel> pixels) {
        if (isBlackWhite(pixels)) {
            return new PBMImage(width, height, pixels);
        } else if (isGrayscale(pixels)) {
            return new PGMImage(width, height, 255, pixels);
        } else {
            return new PPMImage(width, height, 255, pixels);
        }
    }

    private boolean isBlackWhite(List<Pixel> pixels) {
        for (Pixel p : pixels) {
            int r = p.getRed();
            if ((r != 0 && r != 1) || p.getGreen() != r || p.getBlue() != r) {
                return false;
            }
        }
        return true;
    }

    private boolean isGrayscale(List<Pixel> pixels) {
        for (Pixel p : pixels) {
            if (p.getRed() != p.getGreen() || p.getRed() != p.getBlue()) {
                return false;
            }
        }
        return true;
    }
}

