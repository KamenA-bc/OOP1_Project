package Sessions;

public class Help implements Command {
    @Override
    public void execute(String fullInput) {
        System.out.println("Available commands:");
        System.out.println("  load <file1> <file2> ...  - Start a new session and add image files");
        System.out.println("  close <file1> <file2> ... - Close one or more files from the current session");
        System.out.println("  exit                      - Exit the program");
        System.out.println("  help                      - Show this help message");
        System.out.println("  save                     - Save all images in the current session");
        System.out.println("  saveas <filename>        - Save the first image under a new name");
        System.out.println("  grayscale                - Convert all colored images in current session to grayscale if needed");
        System.out.println("  monochrome               - Convert all images in current session to pure black and white");
        System.out.println("  switch <sessionId>        - Switch to another existing session by ID");
        System.out.println("  add <file1> <file2> ...    - Add images to the current active session");
        System.out.println("  sessioninfo               - Show information about the current active session");
        System.out.println("  negative                  - Make a negative of all images in the current active session");
        System.out.println("  undo                      - Remove the last alternation from the current session");
        System.out.println("  collage <direction> <image1> <image2> <outimage> - Create a collage from two images");


    }
}