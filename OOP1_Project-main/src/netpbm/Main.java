package netpbm;

import netpbm.command.CommandMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CommandMenu menu = new CommandMenu();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            menu.execute(input);
        }
    }
}
