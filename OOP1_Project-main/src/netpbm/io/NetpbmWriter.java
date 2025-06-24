package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.Pixel;

import java.io.*;

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
     * <p>
     * The output includes the format identifier, dimensions, optional max value,
     * and the image's pixel data formatted according to the type (P1, P2, or P3).
     * </p>
     *
     * @param img  the image object to be saved
     * @param file the destination file
     * @throws IOException if writing to the file fails
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

            Pixel[][] pixels = img.getPixels();

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    Pixel p = pixels[y][x];

                    if (format.equals("P3")) {
                        out.print(p.getRed() + " ");
                        out.print(p.getGreen() + " ");
                        out.print(p.getBlue() + " ");
                    } else if (format.equals("P2")) {
                        out.print(p.getRed() + " ");
                    } else if (format.equals("P1")) {
                        out.print(p.getRed() + " ");
                    }
                }
                out.println();
            }
        }
    }
}


