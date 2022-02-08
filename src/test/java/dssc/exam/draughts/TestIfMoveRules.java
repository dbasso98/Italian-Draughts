package dssc.exam.draughts;

import net.jqwik.api.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfMoveRules {
    private Board board = new Board();

    @Property
    public void throwsInvalidPositionException(@ForAll("invalidIndexGenerator") int sourceRow, @ForAll("invalidIndexGenerator") int sourceCol,
                                             @ForAll("invalidIndexGenerator") int destinationRow, @ForAll("invalidIndexGenerator") int destinationCol) {
        Exception exception = assertThrows(Exception.class, () -> MoveRules.isPositionOfMoveValid(board, new Point(sourceRow, sourceCol), new Point(destinationRow, destinationCol)));
        assertEquals("Every position must be in range of 0 to 7 for each axis!", exception.getMessage());
    }
    @Provide
    Arbitrary<Integer> invalidIndexGenerator () {
        return Arbitraries.integers().between(-10, 10).filter(n -> n<0 || n>7);
    }

    @Property
    public void throwsValidPositionException(@ForAll("validIndexGenerator") int sourceRow, @ForAll("validIndexGenerator") int sourceCol,
                                             @ForAll("validIndexGenerator") int destinationRow, @ForAll("validIndexGenerator") int destinationCol) throws Exception {
        assertTrue(MoveRules.isPositionOfMoveValid(board, new Point(sourceRow, sourceCol), new Point(destinationRow, destinationCol)));
    }
    @Provide
    Arbitrary<Integer> validIndexGenerator () {
        return Arbitraries.integers().between(0, 7);
    }
}
