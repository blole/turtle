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

    final record Move() implements Command {

    }

    final record Left() implements Command {

    }

    final record Right() implements Command {

    }

    final record Report() implements Command {

    }

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
                return new Command.Move();
            case "LEFT":
                return new Command.Left();
            case "RIGHT":
                return new Command.Right();
            case "REPORT":
                return new Command.Report();
            default:
                throw new IllegalArgumentException("unknown command " + x[0]);
        }
    }
}
