package Sessions;

import java.io.*;
import java.util.*;

public class Close implements Command {
    private final String outputFile = "sessions.txt";

    @Override
    public void execute(String fullInput) {
        String[] tokens = fullInput.trim().split("\\s+");
        if (tokens.length <= 1) {
            System.out.println("Usage: close <file1> <file2> ...");
            return;
        }

        List<String> toClose = new ArrayList<>();
        for (int i = 1; i < tokens.length; i++) {
            toClose.add(tokens[i]);
        }

        List<String> allLines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(outputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                allLines.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading sessions file.");
            return;
        }

        if (allLines.isEmpty()) {
            System.out.println("No active sessions to close files from.");
            return;
        }

        // Get the last line (last session)
        int lastIndex = allLines.size() - 1;
        String lastLine = allLines.get(lastIndex);
        String[] parts = lastLine.trim().split("\\s+");
        String sessionId = parts[0];

        List<String> currentFiles = new ArrayList<>();
        for (int i = 1; i < parts.length; i++) {
            currentFiles.add(parts[i]);
        }

        int originalSize = currentFiles.size();
        currentFiles.removeAll(toClose);
        int updatedSize = currentFiles.size();

        if (originalSize == updatedSize) {
            System.out.println("None of the specified files were found in session " + sessionId);
        } else {
            System.out.println("Closed specified files from session " + sessionId);
        }

        // Rebuild and update the last session line
        StringBuilder updatedLine = new StringBuilder();
        updatedLine.append(sessionId);
        for (String file : currentFiles) {
            updatedLine.append(" ").append(file);
        }

        allLines.set(lastIndex, updatedLine.toString());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            for (String line : allLines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to sessions file.");
        }
    }
}