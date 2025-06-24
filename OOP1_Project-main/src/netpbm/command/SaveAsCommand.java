package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.io.NetpbmWriter;
import netpbm.session.Session;
import netpbm.session.SessionManager;

import java.io.File;


/**
 * Command for saving the first image in the current session under a new file name.
 * <p>
 * Allows the user to specify a new output file name, independent of the original.
 * Only the first image in the session is saved.
 * </p>
 */
public class SaveAsCommand implements Command {

    /**
     * Executes the saveas command.
     * <p>
     * Expects a single argument: the new file name. If a session exists and contains
     * at least one image, the first image is saved to the specified file.
     * </p>
     *
     * @param args command-line arguments; expects format {@code ["saveas", "new_filename"]}
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

        NetPBMImages image = session.getFirstImage();

        try {
            File output = new File(args[1]);
            image.setFileName(output.getName());
            NetpbmWriter.save(image, output);
            System.out.println("Saved first image as: " + args[1]);
        } catch (Exception e) {
            System.out.println("Failed to save as " + args[1] + ": " + e.getMessage());
        }
    }
}



