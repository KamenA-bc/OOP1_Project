package netpbm.command;

/**
 * Lists all available command types in the image editor.
 * Each command is identified by a keyword typed in the console.
 */
public enum CommandType {
    LOAD("load"),
    EXIT("exit"),
    SWITCH("switch"),
    HELP("help"),
    CLOSE("close"),
    GRAYSCALE("grayscale"),
    SAVE("save"),
    SAVEAS("saveas"),
    NEGATIVE("negative"),
    MONOCHROME("monochrome"),
    SESSION_INFO("sessioninfo"),
    ADD("add"),
    ROTATE("rotate"),
    UNDO("undo"),
    COLLAGE("collage");

    private final String keyword;

    /**
     * Creates a command type with a specific console keyword.
     *
     * @param keyword The text used to invoke the command.
     */
    CommandType(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Returns the keyword associated with this command.
     *
     * @return The console keyword for the command.
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * Finds a command type that matches the given string.
     *
     * @param str The input text from the console.
     * @return The matching CommandType, or null if no match is found.
     */
    public static CommandType fromString(String str) {
        for (CommandType type : values()) {
            if (type.keyword.equalsIgnoreCase(str)) {
                return type;
            }
        }
        return null;
    }
}


