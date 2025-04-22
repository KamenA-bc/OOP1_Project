package Sessions;

import Netpbm.*;

import java.io.*;
import java.util.*;

public class Load implements Command {
    private final String outputFile = "sessions.txt";
    private final SessionManager manager = SessionManager.getInstance();

    @Override
    public void execute(String fullInput) {
        String[] tokens = fullInput.trim().split("\\s+");
        List<LoadedImage> loadedImages = new ArrayList<>();

        for (int i = 1; i < tokens.length; i++) {
            String filename = tokens[i];
            LoadedImage image = loadImage(filename);
            if (image != null) {
                loadedImages.add(image);
            } else {
                System.out.println("Skipped invalid or unsupported file: " + filename);
            }
        }

        if (loadedImages.isEmpty()) {
            System.out.println("No valid image files were provided.");
            return;
        }

        // Register new session and store images
        int sessionId = manager.createNewSession();
        for (LoadedImage image : loadedImages) {
            manager.addImageToSession(sessionId, image);
        }

        // Save to sessions.txt
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, true))) {
            writer.write(sessionId + "");
            for (LoadedImage image : loadedImages) {
                writer.write(" " + image.getFilename());
            }
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to sessions file.");
        }

        // Output to user
        System.out.println("Session with ID: " + sessionId + " started");
        System.out.print("Images ");
        for (int i = 0; i < loadedImages.size(); i++) {
            System.out.print("\"" + loadedImages.get(i).getFilename() + "\"");
            if (i < loadedImages.size() - 1) System.out.print(" ");
        }
        System.out.println(" added");
    }

    private LoadedImage loadImage(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found: " + filename);
            return null;
        }

        String ext = getExtension(filename).toLowerCase();
        try {
            switch (ext) {
                case "pbm":
                    return new PBMReader().read(filename);
                case "pgm":
                    return new PGMReader().read(filename);
                case "ppm":
                    return new PPMReader().read(filename);
                default:
                    return null;
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
            return null;
        }
    }

    private String getExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        return (dot > 0 && dot < filename.length() - 1) ? filename.substring(dot + 1) : "";
    }
}


