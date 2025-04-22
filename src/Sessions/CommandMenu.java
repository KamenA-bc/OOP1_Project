package Sessions;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CommandMenu {
    private final Map<String, Command> commands = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public CommandMenu() {
        clearSessionFile();
        commands.put("load", new Load());
        commands.put("exit", new Exit());
        commands.put("help", new Help());
        commands.put("close", new Close());
    }

    private void clearSessionFile() {
        try (BufferedWriter clear = new BufferedWriter(new FileWriter("sessions.txt"))) {
            clear.write(""); // Empty the file
        } catch (IOException e) {
            System.err.println("Error clearing sessions file.");
        }
    }


    public void run() {
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) continue;

            String commandKey = input.split("\\s+")[0].toLowerCase();
            Command command = commands.get(commandKey);

            if (command != null) {
                command.execute(input);
            } else {
                System.out.println("Unknown command.");
            }
        }
    }


    public static void main(String[] args) {
        new CommandMenu().run();
    }
}