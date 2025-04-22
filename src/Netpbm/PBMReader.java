package Netpbm;

import java.io.*;
import java.util.Scanner;

public class PBMReader implements NetpbmImageReader {
    @Override
    public LoadedImage read(String filePath) throws IOException {
        try (Scanner sc = new Scanner(new File(filePath))) {
            String format = sc.next();
            if (!format.equals("P1")) {
                throw new IOException("Invalid PBM format (only ASCII P1 supported)");
            }

            int width = sc.nextInt();
            int height = sc.nextInt();

            int[][][] data = new int[height][width][1]; // 1 channel (black/white)

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    data[y][x][0] = sc.nextInt(); // 0 or 1
                }
            }

            return new LoadedImage(filePath, LoadedImage.Format.PBM, width, height, 1, data);
        }
    }
}