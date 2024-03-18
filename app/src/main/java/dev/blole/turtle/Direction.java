package dev.blole.turtle;

public enum Direction {
    NORTH(0, new Position(0, 1)),
    EAST(1, new Position(1, 0)),
    SOUTH(2, new Position(0, -1)),
    WEST(3, new Position(-1, 0));

    private final int value;
    public final Position offset;

    Direction(int value, Position offset) {
        this.value = value;
        this.offset = offset;
    }

    public Direction turnLeft() {
        return values()[(value + 3) % 4];
    }

    public Direction turnRight() {
        return values()[(value + 1) % 4];
    }
}
