package netpbm.command;

/**
 * Command that displays a list of all supported commands with usage instructions.
 */
public class HelpCommand implements Command {
    /**
     * Prints a formatted list of all available commands, their syntax, and brief descriptions.
     */
    @Override
    public void execute(String[] args) {
        System.out.println("Available Commands:");
        System.out.println("  load <file1> <file2> ...   Load one or more images, each into a new session");
        System.out.println("  save                       Save all images in the current session to their original file names");
        System.out.println("  saveas <filename>          Save the first image in the current session to a new file");
        System.out.println("  close <filename>           Remove the specified image from the current session");
        System.out.println("  switch <session_id>        Switch to a different image session by ID");
        System.out.println("  grayscale                  Convert color images (P3) to grayscale (P2) if not already grayscale");
        System.out.println("  negative                   Invert pixel colors for all P2 and P3 images in the session");
        System.out.println("  help                       Show this help message");
        System.out.println("  exit                       Exit the program");
        System.out.println("  monochrome                  Convert color images to black and white (1-bit)");
        System.out.println("  session info                Display session ID, images, and pending alternations");
        System.out.println("  add <file1> <file2> ...     Add one or more images to the current session");
        System.out.println("  rotate <left|right>         Rotate all images 90 degrees in given direction");
        System.out.println("  undo                        Reverts the last image operation in the current session");
        System.out.println("collage <horizontal|vertical> <img1> <img2> <output> - Creates a collage from two images");

    }
}
