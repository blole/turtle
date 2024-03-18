package dev.blole.turtle;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CommandTest {

    @Test
    void canParseSimpleCommands() {
        assertEquals(Command.Move, Command.parse("MOVE"));
        assertEquals(Command.Left, Command.parse("LEFT"));
        assertEquals(Command.Right, Command.parse("RIGHT"));
        assertEquals(Command.Report, Command.parse("REPORT"));
    }

    @Test
    void throwsOnBadSimpleCommands() {
        assertThrows(IllegalArgumentException.class, () -> Command.parse(""));
        assertThrows(IllegalArgumentException.class, () -> Command.parse(","));
        assertThrows(IllegalArgumentException.class, () -> Command.parse("JUMP"));
        assertThrows(IllegalArgumentException.class, () -> Command.parse("MOVE,"));
        assertThrows(IllegalArgumentException.class, () -> Command.parse(",RIGHT"));
        assertThrows(IllegalArgumentException.class, () -> Command.parse("LEFT,1"));
        assertThrows(IllegalArgumentException.class, () -> Command.parse(",REPORT,"));
    }

    @Test
    void canParsePlaceCommand() {
        assertEquals(new Command.Place(new Position(-5, 1000), Direction.EAST), Command.parse("PLACE,-5,1000,EAST"));
        assertEquals(new Command.Place(new Position(0, -999), Direction.SOUTH), Command.parse("PLACE,0,-999,SOUTH"));
        assertEquals(new Command.Place(new Position(0, 0), Direction.NORTH), Command.parse("PLACE,0,0,NORTH"));
    }

    @Test
    void throwsOnBadPlaceCommand() {
        assertThrows(IllegalArgumentException.class, () -> Command.parse("PLACE,0,0,"));
        assertThrows(IllegalArgumentException.class, () -> Command.parse("PLACE,0,0,SOUT"));
        assertThrows(IllegalArgumentException.class, () -> Command.parse("PLACE,,0,SOUTH"));
        assertThrows(IllegalArgumentException.class, () -> Command.parse("PLACE,0,0,SOUTH,"));
        assertThrows(IllegalArgumentException.class, () -> Command.parse(",PLACE,0,0,SOUTH"));
        assertThrows(IllegalArgumentException.class, () -> Command.parse("PLACE,A,0,SOUTH"));
    }
}
