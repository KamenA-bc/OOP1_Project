package Netpbm;

import java.io.*;
import java.util.Scanner;

public class PGMReader implements NetpbmImageReader {
    @Override
    public LoadedImage read(String filePath) throws IOException {
        try (Scanner sc = new Scanner(new File(filePath))) {
            String format = sc.next();
            if (!format.equals("P2")) {
                throw new IOException("Invalid PGM format (only ASCII P2 supported)");
            }

            int width = sc.nextInt();
            int height = sc.nextInt();
            int maxVal = sc.nextInt();

            int[][][] data = new int[height][width][1]; // 1 grayscale channel

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    data[y][x][0] = sc.nextInt();
                }
            }

            return new LoadedImage(filePath, LoadedImage.Format.PGM, width, height, maxVal, data);
        }
    }
}