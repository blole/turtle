package dev.blole.turtle;

import org.jetbrains.annotations.NotNull;

public class Robot {
    public final Tabletop tabletop;
    public Position pos = null;
    public Direction dir = null;

    public Robot(@NotNull Tabletop tabletop) {
        this.tabletop = tabletop;
    }

    public void execute(@NotNull Command command) {
        if (command instanceof Command.Place place) {
            // ignore place commands outside the board
            if (place.pos().clamp(tabletop).equals(place.pos())) {
                pos = place.pos();
                dir = place.dir();
            }
        } else if (pos != null && dir != null) {
            switch (command) {
                case Command.Move move:
                    pos = pos.add(dir).clamp(tabletop);
                    break;
                case Command.Left left:
                    dir = dir.turnLeft();
                    break;
                case Command.Right right:
                    dir = dir.turnRight();
                    break;
                case Command.Report report:
                    tabletop.report(String.format("REPORT,%d,%d,%s", pos.x(), pos.y(), dir.name()));
                    break;
                case Command.Place place:
                    throw new IllegalStateException("unreachable, checked above");
            }
        }
    }
}
