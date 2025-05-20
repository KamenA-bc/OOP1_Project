package netpbm.io;

import netpbm.image.NetpbmImage;
import java.io.*;

/**
 * Utility class for writing {@link NetpbmImage} objects to disk in Netpbm format.
 * <p>
 * Supports saving PBM (P1), PGM (P2), and PPM (P3) images based on the format.
 */
public class NetpbmWriter {

    /**
     * Saves the given image to the specified file in Netpbm format.
     * <p>
     * The method writes the format identifier, image dimensions, optional max value,
     * and pixel data according to the Netpbm specification.
     *
     * @param img  The image to save.
     * @param file The file to write to.
     * @throws IOException If an error occurs while writing the file.
     */
    public static void save(NetpbmImage img, File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file)) {
            out.println(img.getFormat());
            out.println(img.getWidth() + " " + img.getHeight());
            if (!img.getFormat().equals("P1")) {
                out.println(img.getMaxVal());
            }

            int[][][] pixels = img.getPixels();
            for (int y = 0; y < img.getHeight(); y++) {
                for (int x = 0; x < img.getWidth(); x++) {
                    for (int c = 0; c < pixels[y][x].length; c++) {
                        out.print(pixels[y][x][c] + " ");
                    }
                }
                out.println();
            }
        }
    }
}


