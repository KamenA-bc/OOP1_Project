package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.Pixel;

import java.io.*;
import java.util.List;

/**
 * Utility class for writing NetPBM images (PBM, PGM, PPM) to files.
 * <p>
 * Supports saving images in plain-text formats: P1 (binary), P2 (grayscale), and P3 (color),
 * based on the image's format metadata.
 * </p>
 */
public class NetpbmWriter {

    /**
     * Saves a {@code NetPBMImages} object to the specified file using its native NetPBM format.
     * The output includes format identifier, dimensions, optional max value,
     * and pixel data in row-major order.
     *
     * @param img  the image to be saved
     * @param file the destination file
     * @throws IOException if writing fails
     */
    public static void save(NetPBMImages img, File file) throws IOException {
        try (PrintWriter out = new PrintWriter(file)) {
            String format = img.getFormat();
            int width = img.getWidth();
            int height = img.getHeight();

            out.println(format);
            out.println(width + " " + height);

            if (!format.equals("P1")) {
                out.println(img.getMaxVal());
            }

            List<Pixel> pixels = img.getPixels();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Pixel p = findPixelAt(pixels, x, y);

                    if (p == null) {
                        out.print("0 "); // default to black or blank
                    } else if (format.equals("P3")) {
                        out.print(p.getRed() + " " + p.getGreen() + " " + p.getBlue() + " ");
                    } else {
                        out.print(p.getRed() + " ");
                    }
                }
                out.println();
            }
        }
    }

    /**
     * Searches for a pixel at (x, y) in the provided list.
     */
    private static Pixel findPixelAt(List<Pixel> pixels, int x, int y) {
        for (Pixel p : pixels) {
            if (p.getX() == x && p.getY() == y) {
                return p;
            }
        }
        return null;
    }
}


