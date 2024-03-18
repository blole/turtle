package dev.blole.turtle;

import org.jetbrains.annotations.NotNull;

public sealed interface Command permits
        Command.Place,
        Command.Move,
        Command.Left,
        Command.Right,
        Command.Report {

    final record Place(@NotNull Position pos, @NotNull Direction dir) implements Command {
    }

    final class Move implements Command {
        private Move() {
        }
    }

    final class Left implements Command {
        private Left() {
        }
    }

    final class Right implements Command {
        private Right() {
        }
    }

    final class Report implements Command {
        private Report() {
        }
    }

    public static final Command.Move Move = new Command.Move();
    public static final Command.Left Left = new Command.Left();
    public static final Command.Right Right = new Command.Right();
    public static final Command.Report Report = new Command.Report();

    public static Command parse(@NotNull String line) {
        if (line.isEmpty()) {
            throw new IllegalArgumentException("empty lines are not permitted");
        }
        String[] x = line.split(",");
        switch (x[0]) {
            case "PLACE":
                return new Command.Place(new Position(Integer.parseInt(x[1]), Integer.parseInt(x[2])),
                        Direction.valueOf(x[3]));
            case "MOVE":
                return Command.Move;
            case "LEFT":
                return Command.Left;
            case "RIGHT":
                return Command.Right;
            case "REPORT":
                return Command.Report;
            default:
                throw new IllegalArgumentException("unknown command " + x[0]);
        }
    }
}
