package dev.blole.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class DirectionTest {

    @Test
    public void canRotateLeft() {
        Direction dir = Direction.NORTH;
        dir = dir.turnLeft();
        assertEquals(Direction.WEST, dir);
        dir = dir.turnLeft();
        assertEquals(Direction.SOUTH, dir);
        dir = dir.turnLeft();
        assertEquals(Direction.EAST, dir);
        dir = dir.turnLeft();
        assertEquals(Direction.NORTH, dir);
    }

    @Test
    public void canRotateRight() {
        Direction dir = Direction.NORTH;
        dir = dir.turnRight();
        assertEquals(Direction.EAST, dir);
        dir = dir.turnRight();
        assertEquals(Direction.SOUTH, dir);
        dir = dir.turnRight();
        assertEquals(Direction.WEST, dir);
        dir = dir.turnRight();
        assertEquals(Direction.NORTH, dir);
    }
}