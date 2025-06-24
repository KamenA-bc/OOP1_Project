package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.PGMImage;
import netpbm.image.Pixel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Reads PGM (Portable Graymap) image files in P2 format.
 * Implements the {@code Readers} interface to parse grayscale pixel data.
 */
public class PGMReader implements Readers {

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
            PGMImage image = new PGMImage(width, height, maxVal);

            List<Integer> values = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) continue;
                for (String val : line.trim().split("\\s+")) {
                    values.add(Integer.parseInt(val));
                }
            }

            int index = 0;
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int gray = values.get(index++);
                    image.setPixel(y, x, new Pixel(gray));
                }
            }

            image.setFileName(new File(path).getName());
            return image;
        }
    }
}


