package netpbm.command;

import netpbm.image.NetPBMImages;
import netpbm.io.NetpbmWriter;
import netpbm.session.Session;
import netpbm.session.SessionManager;

import java.io.File;


/**
 * Command for saving all images in the active session.
 * <p>
 * Iterates over all images in the session and writes each one
 * to disk using its current file name and format.
 * </p>
 */
public class SaveCommand implements Command {

    /**
     * Executes the save command.
     * <p>
     * If there is an active session, attempts to save each image
     * in that session using {@code NetpbmWriter}. Images without
     * assigned file names are skipped.
     * </p>
     *
     * @param args not used in this command
     */
    @Override
    public void execute(String[] args) {
        Session session = SessionManager.getActiveSession();
        if (session == null) {
            System.out.println("No active session.");
            return;
        }

        for (NetPBMImages image : session.getImages()) {
            String fileName = image.getFileName();
            if (fileName == null || fileName.isEmpty()) {
                System.out.println("Skipping unnamed image.");
                continue;
            }

            try {
                NetpbmWriter.save(image, new File(fileName));
                System.out.println("Saved to: " + fileName);
            } catch (Exception e) {
                System.out.println("Failed to save " + fileName + ": " + e.getMessage());
            }
        }
    }
}


