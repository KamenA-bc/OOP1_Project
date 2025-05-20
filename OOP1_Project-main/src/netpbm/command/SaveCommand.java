package netpbm.command;

import netpbm.image.NetpbmImage;
import netpbm.io.NetpbmWriter;
import netpbm.session.Session;
import netpbm.session.SessionManager;

import java.io.File;

/**
 * Command that saves all images in the active session to their original file names.
 * <p>
 * Uses {@link NetpbmWriter} to write image data to disk.
 */
public class SaveCommand implements Command {

    /**
     * Executes the save command.
     * <p>
     * Saves each image in the active session using its original file name.
     * Logs the result of each save operation.
     *
     */
    @Override
    public void execute(String[] args) {
        Session session = SessionManager.getActiveSession();
        if (session == null) {
            System.out.println("No active session.");
            return;
        }

        for (NetpbmImage image : session.getImages()) {
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


