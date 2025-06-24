package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.Pixel;

import java.io.*;

public class NetpbmWriter {

    /**
     * Saves the given image to the specified file in Netpbm format.
     *
     * @param img  The image to save.
     * @param file The output file.
     * @throws IOException If an error occurs during writing.
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


