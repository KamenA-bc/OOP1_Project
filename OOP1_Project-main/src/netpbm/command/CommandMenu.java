package netpbm.command;

import java.util.*;

/**
 * Manages and dispatches all console commands in the image editor.
 * <p>
 * This class holds a map between command types and their corresponding actions.
 * It parses user input, identifies the appropriate command, and executes it with any given arguments.
 * Unknown or invalid commands are handled  with a warning message.
 */
public class CommandMenu {

    private final Map<CommandType, Command> commands = new HashMap<>();

    /**
     * Initializes the command menu by registering all supported image editor commands.
     * Each command is mapped to its keyword as defined in the {@link CommandType} enum.
     */
    public CommandMenu() {
        commands.put(CommandType.LOAD, new LoadCommand());
        commands.put(CommandType.EXIT, new ExitCommand());
        commands.put(CommandType.SWITCH, new SwitchCommand());
        commands.put(CommandType.HELP, new HelpCommand());
        commands.put(CommandType.CLOSE, new CloseCommand());
        commands.put(CommandType.GRAYSCALE, new GrayscaleCommand());
        commands.put(CommandType.SAVE, new SaveCommand());
        commands.put(CommandType.SAVEAS, new SaveAsCommand());
        commands.put(CommandType.NEGATIVE, new NegativeCommand());
        commands.put(CommandType.MONOCHROME, new MonochromeCommand());
        commands.put(CommandType.SESSION_INFO, new SessionInfoCommand());
        commands.put(CommandType.ADD, new AddCommand());
        commands.put(CommandType.ROTATE, new RotateCommand());
        commands.put(CommandType.UNDO, new UndoCommand());
        commands.put(CommandType.COLLAGE, new CollageCommand());
    }

    /**
     * Parses and executes a user command from the console input.
     * If the command is recognized, its associated action is performed.
     * Otherwise, an error message is printed to notify the user.
     *
     * @param input The full command line input entered by the user.
     */
    public void execute(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length == 0) return;

        CommandType type = CommandType.fromString(parts[0]);
        if (type == null || !commands.containsKey(type)) {
            System.out.println("Unknown command: " + parts[0]);
            return;
        }

        commands.get(type).execute(parts);
    }
}
