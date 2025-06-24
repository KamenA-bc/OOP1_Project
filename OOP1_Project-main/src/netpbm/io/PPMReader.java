package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.PPMImage;
import netpbm.image.Pixel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Reads PPM (Portable Pixmap) image files in P3 format.
 * Implements the {@code Readers} interface to provide image loading functionality.
 */
public class PPMReader implements Readers {

    /**
     * Parses a P3-formatted PPM file from the specified path.
     * Supports comment skipping and reconstructs the image from pixel data.
     *
     * @param path the path to the image file
     * @return a {@code PPMImage} object containing the parsed image data
     * @throws IOException if the file cannot be read
     */
    @Override
    public NetPBMImages read(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            // Skip comments
            do {
                line = reader.readLine();
            } while (line != null && line.startsWith("#"));
            String format = line.trim(); // Should be P3

            // Skip comments
            do {
                line = reader.readLine();
            } while (line != null && line.startsWith("#"));

            String[] dims = line.trim().split("\\s+");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);

            int maxVal = Integer.parseInt(reader.readLine().trim());

            PPMImage image = new PPMImage(width, height, maxVal);

            // Collect all RGB values in order
            List<Integer> values = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) continue;
                for (String part : line.trim().split("\\s+")) {
                    values.add(Integer.parseInt(part));
                }
            }

            // Fill pixel matrix
            int index = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int r = values.get(index++);
                    int g = values.get(index++);
                    int b = values.get(index++);
                    image.setPixel(y, x, new Pixel(r, g, b));
                }
            }

            image.setFileName(new File(path).getName());
            return image;
        }
    }
}

