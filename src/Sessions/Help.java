package Sessions;

public class Help implements Command {
    @Override
    public void execute(String fullInput) {
        System.out.println("Available commands:");
        System.out.println("  load <file1> <file2> ...  - Start a new session and add image files");
        System.out.println("  close <file1> <file2> ... - Close one or more files from the current session");
        System.out.println("  exit                      - Exit the program");
        System.out.println("  help                      - Show this help message");
    }
}