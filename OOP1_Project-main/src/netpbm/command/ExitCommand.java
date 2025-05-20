package netpbm.command;


/**
 * Command that terminates the image editor application.
 */
public class ExitCommand implements Command {


    @Override
    public void execute(String[] args) {
        System.out.println("Exiting program...");
        System.exit(0);
    }
}