package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.PBMImage;
import netpbm.image.Pixel;
import java.io.*;


/**
 * Provides functionality for reading PBM (Portable Bitmap, format P1) image files.
 * <p>
 * Parses the plain-text PBM format, ignoring comment lines and extracting pixel data
 * as binary values. The result is returned as a {@link NetpbmImage} with a single channel.
 */


public class PBMReader implements Readers {

    @Override
    public  NetPBMImages read(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            // Skip comments
            do {
                line = reader.readLine();
            } while (line != null && line.startsWith("#"));

            String format = line.trim(); // Should be P1

            // Skip comments again if needed
            do {
                line = reader.readLine();
            } while (line != null && line.startsWith("#"));

            String[] dims = line.trim().split("\\s+");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);

            PBMImage image = new PBMImage(width, height);

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
