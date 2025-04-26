package Netpbm;

import Netpbm.Enums.Format;

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

            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    data[i][j][0] = sc.nextInt(); // R
                    data[i][j][1] = sc.nextInt(); // G
                    data[i][j][2] = sc.nextInt(); // B
                }
            }

            return new LoadedImage(filePath, Format.PPM, width, height, maxVal, data);
        }
    }
}