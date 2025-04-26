package Sessions;

import Commands.*;

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
        commands.put("save", new Save());
        commands.put("saveas", new SaveAs());
        commands.put("grayscale", new Grayscale());
        commands.put("monochrome", new Monochrome());
        commands.put("switch", new SwitchSession());
        commands.put("add", new AddImageToSession());
        commands.put("sessioninfo", new SessionInfo());
        commands.put("negative", new Negative());
        commands.put("rotate", new Rotate());
        commands.put("undo", new Undo());
        commands.put("collage", new Collage());


    }

    private void clearSessionFile() {
        try (BufferedWriter clear = new BufferedWriter(new FileWriter("sessions.txt"))) {
            clear.write("");
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