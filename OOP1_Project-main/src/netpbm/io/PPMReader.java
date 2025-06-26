package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.PPMImage;
import netpbm.image.Pixel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A reader for PPM (Portable Pixmap) images in ASCII (P3) format.
 * <p>
 * This class parses the content of a PPM file, skipping comments,
 * extracting dimensions and color data, and converting it into a
 * {@link PPMImage} composed of {@link Pixel} objects.
 */
public class PPMReader implements Readers {
    /**
     * Reads an ASCII PPM (P3) image file from the given path and returns
     * a {@link PPMImage} object representing the image.
     * <p>
     * Supports comments (lines starting with '#') and assumes that pixel
     * data is organized in RGB triplets.
     *
     * @param path the file path to the image
     * @return a PPMImage containing the parsed pixel data
     * @throws IOException if an I/O error occurs while reading the file
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
            int[] rgb = new int[3];
            int channel = 0;
            int x = 0, y = 0;

            while ((line = reader.readLine()) != null && y < height) {
                if (line.startsWith("#") || line.trim().isEmpty()) continue;

                for (String valStr : line.trim().split("\\s+")) {
                    rgb[channel] = Integer.parseInt(valStr);
                    channel++;

                    if (channel == 3) {
                        pixelList.add(new Pixel(x, y, rgb[0], rgb[1], rgb[2]));
                        x++;
                        if (x == width) {
                            x = 0;
                            y++;
                        }
                        channel = 0;
                    }
                    if (y == height) break;
                }
            }

            PPMImage image = new PPMImage(width, height, maxVal, pixelList);
            image.setFileName(new File(path).getName());
            return image;
        }
    }
}

