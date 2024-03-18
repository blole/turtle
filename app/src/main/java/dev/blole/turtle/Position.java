package dev.blole.turtle;

public final record Position(int x, int y) {

    public Position add(Position other) {
        return new Position(x + other.x, y + other.y);
    }

    public Position add(Direction direction) {
        return add(direction.offset);
    }

    public Position clamp(Tabletop tabletop) {
        return clamp(tabletop.minX(), tabletop.minY(), tabletop.maxX(), tabletop.maxY());
    }

    public Position clamp(int minX, int minY, int maxX, int maxY) {
        return new Position(clamp(x, minX, maxX), clamp(y, minY, maxY));
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }
}
