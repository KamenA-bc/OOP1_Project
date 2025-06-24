package netpbm.io;

import netpbm.image.NetPBMImages;
import netpbm.image.PBMImage;
import netpbm.image.Pixel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Reads PBM (Portable BitMap) image files in P1 format.
 * Parses binary pixel data into a {@code PBMImage} object.
 */
public class PBMReader implements Readers {

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

            PBMImage image = new PBMImage(width, height);


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
                    int bit = values.get(index++);
                    image.setPixel(y, x, new Pixel(bit));
                }
            }

            image.setFileName(new File(path).getName());
            return image;
        }
    }
}

