package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.PBMImage;
import netpbm.image.Pixel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * A reader for PBM (Portable Bitmap) images in ASCII (P1) format.
 * <p>
 * This class reads PBM files, skips comments, and parses the image
 * dimensions and monochrome pixel values into a {@link PBMImage}.
 * Each pixel is either black (1) or white (0), stored in a {@link Pixel}.
 */
public class PBMReader implements Readers {

    /**
     * Reads an ASCII PBM (P1) image file from the specified path and
     * returns a {@link PBMImage} containing its pixel data.
     * <p>
     * The method skips comment lines and whitespace, and interprets each
     * value as either 0 or 1, representing black or white pixels.
     *
     * @param path the file path to the PBM image
     * @return a PBMImage representing the binary image
     * @throws IOException if an error occurs while reading the file
     */
    @Override
    public NetPBMImages read(String path) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;

            do {
                line = reader.readLine();
            } while (line != null && line.startsWith("#"));

            String format = line.trim();

            do {
                line = reader.readLine();
            } while (line != null && line.startsWith("#"));

            String[] dims = line.trim().split("\\s+");
            int width = Integer.parseInt(dims[0]);
            int height = Integer.parseInt(dims[1]);

            List<Pixel> pixelList = new ArrayList<>();
            int y = 0, x = 0;

            while ((line = reader.readLine()) != null && y < height) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;

                for (String valStr : line.trim().split("\\s+")) {
                    int val = Integer.parseInt(valStr);
                    pixelList.add(new Pixel(x, y, val));
                    x++;
                    if (x == width) {
                        x = 0;
                        y++;
                    }
                    if (y == height) break;
                }
            }

            PBMImage image = new PBMImage(width, height, pixelList);
            image.setFileName(new File(path).getName());
            return image;
        }
    }
}

