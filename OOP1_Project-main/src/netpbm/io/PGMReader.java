package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.PGMImage;
import netpbm.image.Pixel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * A reader for PGM (Portable Graymap) images in ASCII (P2) format.
 * <p>
 * This class parses the contents of a PGM file, skips comment lines,
 * extracts image dimensions and grayscale values, and converts the
 * result into a {@link PGMImage} composed of {@link Pixel} objects.
 */
public class PGMReader implements Readers {

    /**
     * Reads an ASCII PGM (P2) image file from the specified path and
     * constructs a {@link PGMImage} containing its pixel data.
     * <p>
     * Comments (lines starting with '#') are ignored, and each grayscale
     * value is stored as a single intensity in a {@link Pixel}.
     *
     * @param path the file path to the PGM image
     * @return a PGMImage representing the grayscale image
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

            int maxVal = Integer.parseInt(reader.readLine().trim());

            List<Pixel> pixelList = new ArrayList<>();
            int x = 0, y = 0;

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

            PGMImage image = new PGMImage(width, height, maxVal, pixelList);
            image.setFileName(new File(path).getName());
            return image;
        }
    }
}



