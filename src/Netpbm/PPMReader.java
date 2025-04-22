package Netpbm;

import java.io.*;
import java.util.Scanner;

public class PPMReader implements NetpbmImageReader {
    @Override
    public LoadedImage read(String filePath) throws IOException {
        try (Scanner sc = new Scanner(new File(filePath))) {
            String format = sc.next();
            if (!format.equals("P3")) {
                throw new IOException("Invalid PPM format (only ASCII P3 supported)");
            }

            int width = sc.nextInt();
            int height = sc.nextInt();
            int maxVal = sc.nextInt();

            int[][][] data = new int[height][width][3]; // RGB channels

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    data[y][x][0] = sc.nextInt(); // R
                    data[y][x][1] = sc.nextInt(); // G
                    data[y][x][2] = sc.nextInt(); // B
                }
            }

            return new LoadedImage(filePath, LoadedImage.Format.PPM, width, height, maxVal, data);
        }
    }
}