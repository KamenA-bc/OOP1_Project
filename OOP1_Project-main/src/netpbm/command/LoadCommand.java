package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.io.NetpbmLoader;
import netpbm.session.SessionManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads one or more image files into a single new session.
 * <p>
 * Each file is validated and parsed using the Netpbm format loader.
 * A session is created only once and all successfully loaded images are added to it.
 */
public class LoadCommand implements Command {

    /**
     * Loads one or more image files into a single new session.
     * Validates each file path and attempts to parse it using the Netpbm loader.
     * Successfully loaded images are collected and added to a new session,
     * which becomes the currently active one. Errors are reported individually.
     *
     * @param args Command-line input, where args[0] is "load" and the rest are file paths.
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: load <file1> <file2> ...");
            return;
        }

        List<NetPBMImages> imagesToLoad = new ArrayList<>();

        for (int i = 1; i < args.length; i++) {
            File file = new File(args[i]);
            if (!file.exists()) {
                System.out.println("File not found: " + file.getName());
                continue;
            }

            try {
                NetPBMImages image = NetpbmLoader.load(file);
                imagesToLoad.add(image);
                System.out.println("Loaded: " + file.getName());
            } catch (Exception e) {
                System.out.println("Failed to load: " + file.getName() + " - " + e.getMessage());
            }
        }

        if (!imagesToLoad.isEmpty()) {
            SessionManager.createSessionWithImages(imagesToLoad);
        }
    }
}




