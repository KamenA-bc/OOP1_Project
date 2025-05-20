package netpbm.io;

import netpbm.image.NetpbmImage;

import java.io.*;
import java.util.*;

/**
 * Provides functionality for reading PGM (Portable Graymap, format P2) image files.
 * <p>
 * Parses the plain-text PGM format, skipping comment lines and reading pixel values
 * as grayscale intensities in the range [0, maxVal]. The result is stored as a single-channel image.
 */
public class PGMReader {

    /**
     * Reads a PGM (P2) image from the specified file.
     * <p>
     * The method expects a valid P2 header, optional comments, and grayscale pixel data.
     *
     * @param file The PGM file to read.
     * @return A {@link NetpbmImage} containing the parsed grayscale image.
     * @throws IOException If the file format is invalid or reading fails.
     */
    public static NetpbmImage read(File file) throws IOException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));

        String magic = sc.next();
        if (!magic.equals("P2")) throw new IOException("Invalid PGM format");

        while (sc.hasNext("#.*")) sc.nextLine();

        int width = sc.nextInt();
        int height = sc.nextInt();
        int maxVal = sc.nextInt();

        NetpbmImage img = new NetpbmImage("P2", width, height, maxVal, 1);

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                img.setPixel(y, x, 0, sc.nextInt());

        return img;
    }
}
