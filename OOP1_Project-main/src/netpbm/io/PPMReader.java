package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.PPMImage;
import netpbm.image.Pixel;

import java.io.*;


/**
 * Provides functionality for reading PPM (Portable Pixmap, format P3) image files.
 * <p>
 * Parses the plain-text PPM format, skipping comment lines and reading RGB pixel values.
 * The resulting image is stored with three color channels: red, green, and blue.
 */


public class PPMReader implements Readers {

    @Override
    public  NetPBMImages read(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            // Skip comments
            do {
                line = reader.readLine();
            } while (line != null && line.startsWith("#"));

            String format = line.trim(); // Should be P3

            do {
                line = reader.readLine();
            } while (line != null && line.startsWith("#"));

            String[] dims = line.trim().split("\\s+");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);

            int maxVal = Integer.parseInt(reader.readLine().trim());

            PPMImage image = new PPMImage(width, height, maxVal);

            int y = 0, x = 0, channel = 0;
            int[] rgb = new int[3];

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) continue;

                for (String valStr : line.trim().split("\\s+")) {
                    rgb[channel] = Integer.parseInt(valStr);
                    channel++;
                    if (channel == 3) {
                        image.setPixel(y, x, new Pixel(rgb[0], rgb[1], rgb[2]));
                        channel = 0;
                        x++;
                        if (x == width) {
                            x = 0;
                            y++;
                        }
                        if (y == height) break;
                    }
                }
            }

            image.setFileName(new File(path).getName());
            return image;
        }
    }
}

