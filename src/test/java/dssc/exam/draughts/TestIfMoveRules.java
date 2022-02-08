package dssc.exam.draughts;

import java.awt.*;
import org.junit.jupiter.api.Test;
import dssc.exam.draughts.MoveRules;
import static org.junit.jupiter.api.Assertions.*;
import net.jqwik.api.*;

public class TestIfMoveRules {
    private Board board = new Board();

    @Property
    public void throwsInvalidPositionException(@ForAll("invalidIndexGenerator") int sourceRow, @ForAll("invalidIndexGenerator") int sourceCol,
                                             @ForAll("invalidIndexGenerator") int destinationRow, @ForAll("invalidIndexGenerator") int destinationCol) throws Exception {
        Exception exception = assertThrows(Exception.class, () -> {MoveRules.isValidMove(board, new Point(sourceRow, sourceCol), new Point(destinationRow, destinationCol));});
        assertEquals("Every position must be in range of 0 to 7 for each axis!", exception.getMessage());
    }
    @Provide
    Arbitrary<Integer> invalidIndexGenerator () {
        return Arbitraries.integers().between(-10, -1);
    }

    @Property
    public void throwsValidPositionException(@ForAll("validIndexGenerator") int sourceRow, @ForAll("validIndexGenerator") int sourceCol,
                                             @ForAll("validIndexGenerator") int destinationRow, @ForAll("validIndexGenerator") int destinationCol) throws Exception {
        assertTrue(MoveRules.isValidMove(board, new Point(sourceRow, sourceCol), new Point(destinationRow, destinationCol)));
    }
    @Provide
    Arbitrary<Integer> validIndexGenerator () {
        return Arbitraries.integers().between(0, 7);
    }
}
