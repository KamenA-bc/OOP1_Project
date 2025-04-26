package Commands;


import Netpbm.Enums.Format;
import Netpbm.LoadedImage;
import Sessions.Command;
import Sessions.SessionManager;

import java.util.List;

public class Collage implements Command {
    private final SessionManager manager = SessionManager.getInstance();

    @Override
    public void execute(String fullInput) {
        String[] tokens = fullInput.trim().split("\\s+");

        if (tokens.length != 5) {
            System.out.println("Usage: collage <horizontal|vertical> <image1> <image2> <outimage>");
            return;
        }

        String direction = tokens[1];
        String imgName1 = tokens[2];
        String imgName2 = tokens[3];
        String outImageName = tokens[4];

        if (!direction.equals("horizontal") && !direction.equals("vertical")) {
            System.out.println("Invalid direction. Use 'horizontal' or 'vertical'.");
            return;
        }

        int sessionId = manager.getActiveSessionId();
        if (sessionId == -1) {
            System.out.println("No active session to create a collage.");
            return;
        }

        List<LoadedImage> images = manager.getSessionImages(sessionId);

        LoadedImage img1 = findImage(images, imgName1);
        LoadedImage img2 = findImage(images, imgName2);

        if (img1 == null || img2 == null) {
            System.out.println("One or both images not found in the current session.");
            return;
        }

        if (img1.getFormat() != img2.getFormat()) {
            System.out.println("Images must have the same format.");
            return;
        }

        if (img1.getWidth() != img2.getWidth() || img1.getHeight() != img2.getHeight()) {
            System.out.println("Images must have the same dimensions.");
            return;
        }

        LoadedImage collageImage = createCollage(img1, img2, direction, outImageName);

        if (collageImage != null) {
            manager.addImageToSession(sessionId, collageImage);
            System.out.println("Collage created and added to the session: " + outImageName);
        } else {
            System.out.println("Error creating collage.");
        }
    }

    private LoadedImage findImage(List<LoadedImage> images, String name) {
        for (LoadedImage img : images) {
            if (img.getFilename().equals(name)) {
                return img;
            }
        }
        return null;
    }

    private LoadedImage createCollage(LoadedImage img1, LoadedImage img2, String direction, String outImageName) {
        int[][][] data1 = img1.getPixelData();
        int[][][] data2 = img2.getPixelData();

        int newWidth, newHeight;
        int[][][] newData;

        if (direction.equals("horizontal")) {
            newWidth = img1.getWidth() + img2.getWidth();
            newHeight = img1.getHeight();
            newData = new int[newHeight][newWidth][(img1.getFormat() == Format.PPM) ? 3 : 1];

            for (int y = 0; y < newHeight; y++) {
                for (int x = 0; x < img1.getWidth(); x++) {
                    newData[y][x] = data1[y][x].clone();
                }
                for (int x = 0; x < img2.getWidth(); x++) {
                    newData[y][x + img1.getWidth()] = data2[y][x].clone();
                }
            }
        } else { // vertical
            newWidth = img1.getWidth();
            newHeight = img1.getHeight() + img2.getHeight();
            newData = new int[newHeight][newWidth][(img1.getFormat() == Format.PPM) ? 3 : 1];

            for (int y = 0; y < img1.getHeight(); y++) {
                for (int x = 0; x < newWidth; x++) {
                    newData[y][x] = data1[y][x].clone();
                }
            }
            for (int y = 0; y < img2.getHeight(); y++) {
                for (int x = 0; x < newWidth; x++) {
                    newData[y + img1.getHeight()][x] = data2[y][x].clone();
                }
            }
        }

        return new LoadedImage(outImageName, img1.getFormat(), newWidth, newHeight, img1.getMaxVal(), newData);
    }
}
