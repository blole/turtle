package dev.blole.turtle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

class RobotTest {

    private static Tabletop tabletop = spy(new Tabletop(-1, -1, 1, 1));

    private Robot robot;

    @BeforeAll
    private static void beforeAll() {
        doNothing().when(tabletop).report(any());
    }

    @BeforeEach
    private void beforeEach() {
        robot = new Robot(tabletop);
    }

    @Test
    public void onlyReportAfterPlacedOnTable() {
        robot.execute(Command.Report);
        robot.execute(new Command.Place(new Position(-2, 0), Direction.WEST));
        robot.execute(Command.Report);
        verify(tabletop, never()).report(any());
        robot.execute(new Command.Place(new Position(-1, -1), Direction.EAST));
        robot.execute(Command.Report);
        verify(tabletop).report("REPORT,-1,-1,EAST");
    }

    @Test
    public void onlyPlaceOnTable() {
        robot.execute(new Command.Place(new Position(-2, -1), Direction.NORTH));
        robot.execute(new Command.Place(new Position(-1, -2), Direction.EAST));
        robot.execute(new Command.Place(new Position(2, 1), Direction.SOUTH));
        robot.execute(new Command.Place(new Position(1, 2), Direction.WEST));
        assertNull(robot.pos);
        robot.execute(new Command.Place(new Position(1, 1), Direction.NORTH));
        assertEquals(new Position(1, 1), robot.pos);
        robot.execute(new Command.Place(new Position(-1, -2), Direction.EAST));
        assertEquals(new Position(1, 1), robot.pos);
    }

    @Test
    public void canReplace() {
        robot.execute(new Command.Place(new Position(0, 0), Direction.NORTH));
        assertEquals(new Position(0, 0), robot.pos);
        assertEquals(Direction.NORTH, robot.dir);
        robot.execute(new Command.Place(new Position(0, 1), Direction.SOUTH));
        assertEquals(new Position(0, 1), robot.pos);
        assertEquals(Direction.SOUTH, robot.dir);
    }

    @Test
    public void cantMoveOffTable() {
        robot.execute(new Command.Place(new Position(0, 0), Direction.NORTH));
        assertEquals(new Position(0, 0), robot.pos);
        robot.execute(Command.Move);
        assertEquals(new Position(0, 1), robot.pos);
        robot.execute(Command.Move);
        assertEquals(new Position(0, 1), robot.pos);
        robot.execute(Command.Right);
    }

    @Test
    public void canMove() {
        /*
         * move in an 8
         * +---+
         * |12 |
         * |036|
         * | 45|
         * +---+
         * 
         */
        robot.execute(new Command.Place(new Position(-1, 0), Direction.NORTH));
        robot.execute(Command.Move);
        robot.execute(Command.Right);
        robot.execute(Command.Move);
        robot.execute(Command.Right);
        robot.execute(Command.Move);
        assertEquals(new Position(0, 0), robot.pos);
        robot.execute(Command.Move);
        robot.execute(Command.Left);
        robot.execute(Command.Move);
        robot.execute(Command.Left);
        robot.execute(Command.Move);
        robot.execute(Command.Left);
        robot.execute(Command.Left);
        assertEquals(new Position(1, 0), robot.pos);
        assertEquals(Direction.SOUTH, robot.dir);
        robot.execute(Command.Report);
        verify(tabletop).report("REPORT,1,0,SOUTH");
    }
}
