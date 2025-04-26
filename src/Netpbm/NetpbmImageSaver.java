package Netpbm;

import Netpbm.Enums.Format;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class NetpbmImageSaver {
    public static void save(LoadedImage image, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            switch (image.getFormat()) {
                case PBM -> writer.write("P1\n");
                case PGM -> writer.write("P2\n");
                case PPM -> writer.write("P3\n");
            }
            writer.write(image.getWidth() + " " + image.getHeight() + "\n");
            if (image.getFormat() != Format.PBM) {
                writer.write(image.getMaxVal() + "\n");
            }

            int[][][] data = image.getPixelData();
            for (int i = 0; i < image.getHeight(); i++) {
                for (int j = 0; j < image.getWidth(); j++) {
                    int[] pixel = data[i][j];
                    for (int value : pixel) {
                        writer.write(value + " ");
                    }
                }
                writer.newLine();
            }
        }
    }
}