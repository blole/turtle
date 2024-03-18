package dev.blole.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void canAdd() {
        Position pos = new Position(-10, 20);
        pos = pos.add(new Position(15, -50));
        assertEquals(new Position(5, -30), pos);
    }

    @Test
    public void canClamp() {
        Tabletop tabletop = new Tabletop(-5, 200, 5, 300);
        assertEquals(new Position(-5, 300), new Position(-10, 500).clamp(tabletop));
        assertEquals(new Position(5, 200), new Position(10, 100).clamp(tabletop));
    }
}
