package netpbm.io;

import netpbm.image.NetpbmImage;

import java.io.*;
import java.util.*;

/**
 * Provides functionality for reading PBM (Portable Bitmap, format P1) image files.
 * <p>
 * Parses the plain-text PBM format, ignoring comment lines and extracting pixel data
 * as binary values. The result is returned as a {@link NetpbmImage} with a single channel.
 */
public class PBMReader {

    /**
     * Reads a PBM (P1) image from the given file.
     * <p>
     * The method expects a valid P1 header, optional comments, and binary pixel data.
     *
     * @param file The PBM file to read.
     * @return A {@link NetpbmImage} containing the parsed image data.
     * @throws IOException If the file format is invalid or reading fails.
     */
    public static NetpbmImage read(File file) throws IOException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));

        String magic = sc.next();
        if (!magic.equals("P1")) throw new IOException("Invalid PBM format");


        while (sc.hasNext("#.*")) sc.nextLine();

        int width = sc.nextInt();
        int height = sc.nextInt();

        NetpbmImage img = new NetpbmImage("P1", width, height, 1, 1);

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                img.setPixel(y, x, 0, sc.nextInt());

        return img;
    }
}