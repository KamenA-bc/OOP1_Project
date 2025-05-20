package netpbm.command;

import netpbm.image.NetpbmImage;
import netpbm.io.NetpbmWriter;
import netpbm.session.Session;
import netpbm.session.SessionManager;

import java.io.File;

/**
 * Command that saves the first image in the active session under a new file name.
 * <p>
 * This does not change the image's original file name in memory.
 */
public class SaveAsCommand implements Command {

    /**
     * Executes the saveas command.
     * <p>
     * Saves the first image in the current session to the specified file path.
     *
     * @param args Command-line arguments, where {@code args[1]} is the target file name.
     */
    @Override
    public void execute(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: saveas <new_filename>");
            return;
        }

        Session session = SessionManager.getActiveSession();
        if (session == null || session.getImages().isEmpty()) {
            System.out.println("No image loaded in the current session.");
            return;
        }

        NetpbmImage image = session.getFirstImage();

        try {
            NetpbmWriter.save(image, new File(args[1]));
            System.out.println("Saved first image as: " + args[1]);
        } catch (Exception e) {
            System.out.println("Failed to save as " + args[1] + ": " + e.getMessage());
        }
    }
}


