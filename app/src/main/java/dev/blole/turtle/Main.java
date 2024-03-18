package dev.blole.turtle;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Tabletop tabletop = new Tabletop(0, 0, 4, 4);
        Robot robot = new Robot(tabletop);

        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Command command = Command.parse(line);
                robot.execute(command);
            }
        }
    }
}