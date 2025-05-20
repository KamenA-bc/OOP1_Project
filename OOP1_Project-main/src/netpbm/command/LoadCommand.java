package netpbm.command;

import netpbm.image.NetpbmImage;
import netpbm.io.NetpbmLoader;
import netpbm.session.SessionManager;

import java.io.File;

/**
 * Loads one or more image files into the editor as new sessions.
 * <p>
 * Each file is validated and parsed using the Netpbm format loader.
 * A separate session is created for each successfully loaded image.
 */
public class LoadCommand implements Command {

    /**
     * Executes the load command with the given arguments.
     * <p>
     * For each valid file, an image is loaded and a new session is created.
     * Files that do not exist or fail to load are reported to the user.
     *
     * @param args Command-line input, where args[0] is "load" and the rest are file paths.
     */
    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: load <file1> <file2> ...");
            return;
        }

        for (int i = 1; i < args.length; i++) {
            File file = new File(args[i]);
            if (!file.exists()) {
                System.out.println("File not found: " + file.getName());
                continue;
            }

            try {
                NetpbmImage image = NetpbmLoader.load(file);
                SessionManager.createSessionWithSingleImage(image);
                System.out.println("Loaded: " + file.getName());
            } catch (Exception e) {
                System.out.println("Failed to load: " + file.getName() + " - " + e.getMessage());
            }
        }
    }
}




