package search;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String filename = null;
        if (args.length >= 2 && args[0].equals("--data")) {
            filename = args[1];
        }

        Scanner scanner = new Scanner(System.in);
        UserInterface ui = new UserInterface(scanner, filename);
        ui.boot();
    }
}
