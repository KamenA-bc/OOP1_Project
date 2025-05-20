package netpbm.io;

import netpbm.image.NetpbmImage;

import java.io.*;
import java.util.*;

/**
 * Provides functionality for reading PPM (Portable Pixmap, format P3) image files.
 * <p>
 * Parses the plain-text PPM format, skipping comment lines and reading RGB pixel values.
 * The resulting image is stored with three color channels: red, green, and blue.
 */
public class PPMReader {

    /**
     * Reads a PPM (P3) image from the specified file.
     * <p>
     * The method expects a valid P3 header, optional comments, and RGB pixel data.
     *
     * @param file The PPM file to read.
     * @return A {@link NetpbmImage} representing the parsed color image.
     * @throws IOException If the file format is invalid or reading fails.
     */
    public static NetpbmImage read(File file) throws IOException {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(file)));

        String magic = sc.next();
        if (!magic.equals("P3")) throw new IOException("Invalid PPM format");

        while (sc.hasNext("#.*")) sc.nextLine();

        int width = sc.nextInt();
        int height = sc.nextInt();
        int maxVal = sc.nextInt();

        NetpbmImage img = new NetpbmImage("P3", width, height, maxVal, 3);

        for (int y = 0; y < height; y++)
            for (int x = 0; x < width; x++)
                for (int c = 0; c < 3; c++)
                    img.setPixel(y, x, c, sc.nextInt());

        return img;
    }
}
