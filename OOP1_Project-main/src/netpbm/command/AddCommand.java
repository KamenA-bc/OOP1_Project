package netpbm.command;

import netpbm.image.NetPBMImages;

import netpbm.io.NetpbmLoader;
import netpbm.session.Session;
import netpbm.session.SessionManager;

import java.io.File;



/**
 * Command for adding one or more NetPBM image files to the active session.
 * <p>
 * Ensures each file exists, avoids duplicate file names in the session,
 * and loads each image using {@code NetpbmLoader}.
 * </p>
 */
public class AddCommand implements Command {

    /**
     * Executes the add command.
     * <p>
     * Expects one or more file paths as arguments.
     * Adds each valid, non-duplicate image to the active session.
     * </p>
     *
     * @param args command-line arguments; expects {@code ["add", "file1", "file2", ...]}
     */
    @Override
    public void execute(String[] args) {
        Session session = SessionManager.getActiveSession();
        if (session == null) {
            System.out.println("No active session. Use 'load' to start a session.");
            return;
        }

        if (args.length < 2) {
            System.out.println("Usage: add <file1> <file2> ...");
            return;
        }

        int added = 0;

        for (int i = 1; i < args.length; i++) {
            File file = new File(args[i]);
            if (!file.exists()) {
                System.out.println("File not found: " + file.getName());
                continue;
            }

            String filenameToAdd = file.getName();
            boolean exists = false;

            for (NetPBMImages img : session.getImages()) {
                if (img.getFileName().equalsIgnoreCase(filenameToAdd)) {
                    exists = true;
                    break;
                }
            }

            if (exists) {
                System.out.println("Image already exists in session: " + filenameToAdd);
                continue;
            }

            try {
                NetPBMImages image = NetpbmLoader.load(file);
                session.addImage(image);
                System.out.println("Added image to session: " + filenameToAdd);
                added++;
            } catch (Exception e) {
                System.out.println("Failed to load image: " + filenameToAdd + " - " + e.getMessage());
            }
        }

        if (added == 0) {
            System.out.println("No new images were added.");
        }
    }
}

