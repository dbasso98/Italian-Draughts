package dssc.exam.draughts;

import net.jqwik.api.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestIfMoveRules {
    private Board board = new Board();

    @Property
    void throwsInvalidPositionException(@ForAll("invalidIndexGenerator") int sourceRow, @ForAll("invalidIndexGenerator") int sourceCol,
                                             @ForAll("invalidIndexGenerator") int destinationRow, @ForAll("invalidIndexGenerator") int destinationCol) {
        Exception exception = assertThrows(Exception.class, () -> MoveRules.checkIfPositionsAreValid(board, new Point(sourceRow, sourceCol), new Point(destinationRow, destinationCol)));
        assertEquals("Every position must be in range of 0 to 7 for each axis!", exception.getMessage());
    }
    @Provide
    Arbitrary<Integer> invalidIndexGenerator () {
        return Arbitraries.integers().between(-10, 10).filter(n -> n<0 || n>7);
    }

    @Property
    void checksSamePosition(@ForAll("validIndexGenerator") int row, @ForAll("validIndexGenerator") int column) {
        Exception exception = assertThrows(Exception.class, () -> MoveRules.checkIfPositionsAreValid(board, new Point(row, column), new Point(row, column)));
        assertEquals("Source and destination position cannot be the same!", exception.getMessage());
    }
    @Provide
    Arbitrary<Integer> validIndexGenerator () {
        return Arbitraries.integers().between(0, 7);
    }


    @Property
    void checksDiagonalPosition(@ForAll("subSquareGenerator") int row, @ForAll("subSquareGenerator") int column, @ForAll("offset") Integer[] offset){
        Exception exception = assertThrows(Exception.class, () -> MoveRules.checkIfPositionsAreValid(board, new Point(row, column),
                new Point(row+offset[0], column+offset[1])));
        assertEquals("Checker can only move diagonally!", exception.getMessage());
    }

    @Provide
    Arbitrary<Integer> subSquareGenerator(){ return Arbitraries.integers().between( 1, 6 );}

    @Provide
    Arbitrary<Integer[]> offset() {
        Arbitrary<Integer> integerArbitrary = Arbitraries.integers().between(-1,1);
        return integerArbitrary.array(Integer[].class).ofSize(2).filter(x -> x[0]!=x[1] && (x[0]==0 || x[1]==0) );
    }


}
