package netpbm.command;

/**
 * A command that can be run from the console with arguments.
 * Used in the image editor to trigger specific actions like loading or editing images.
 */
public interface Command {

    /**
     * Runs the command with the given arguments.
     *
     * @param args The input parameters from the console.
     */
    void execute(String[] args);
}
