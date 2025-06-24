package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.PGMImage;
import netpbm.image.Pixel;

import java.io.*;

/**
 * Provides functionality for reading PGM (Portable Graymap, format P2) image files.
 * <p>
 * Parses the plain-text PGM format, skipping comment lines and reading pixel values
 * as grayscale intensities in the range [0, maxVal]. The result is stored as a single-channel image.
 */

public class PGMReader implements Readers {

   @Override
    public  NetPBMImages read(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            // Skip comments
            do {
                line = reader.readLine();
            } while (line != null && line.startsWith("#"));

            String format = line.trim(); // Should be P2

            do {
                line = reader.readLine();
            } while (line != null && line.startsWith("#"));

            String[] dims = line.trim().split("\\s+");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);

            int maxVal = Integer.parseInt(reader.readLine().trim());

            PGMImage image = new PGMImage(width, height, maxVal);

            int y = 0, x = 0;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) continue;

                for (String valStr : line.trim().split("\\s+")) {
                    int val = Integer.parseInt(valStr);
                    image.setPixel(y, x, new Pixel(val));
                    x++;
                    if (x == width) {
                        x = 0;
                        y++;
                    }
                    if (y == height) break;
                }
            }

            image.setFileName(new File(path).getName());
            return image;
        }
    }
}

