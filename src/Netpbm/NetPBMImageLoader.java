package Netpbm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NetPBMImageLoader {

    public static List<LoadedImage> loadImagesFromTokens(String[] tokens, int startIndex) {
        List<LoadedImage> loadedImages = new ArrayList<>();

        for (int i = startIndex; i < tokens.length; i++) {
            String filename = tokens[i];
            LoadedImage image = loadImage(filename);
            if (image != null) {
                loadedImages.add(image);
            } else {
                System.out.println("Skipped invalid or unsupported file: " + filename);
            }
        }

        return loadedImages;
    }

    private static LoadedImage loadImage(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found: " + filename);
            return null;
        }

        String ext = getExtension(filename).toLowerCase();
        try {
            return switch (ext) {
                case "pbm" -> new PBMReader().read(filename);
                case "pgm" -> new PGMReader().read(filename);
                case "ppm" -> new PPMReader().read(filename);
                default -> null;
            };
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
            return null;
        }
    }

    private static String getExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        return (dot > 0 && dot < filename.length() - 1) ? filename.substring(dot + 1) : "";
    }
}
