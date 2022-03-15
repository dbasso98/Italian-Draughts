package dssc.exam.draughts;

import dssc.exam.draughts.core.Board;
import dssc.exam.draughts.moveLogics.MoveValidator;
import dssc.exam.draughts.exceptions.IndexException;
import dssc.exam.draughts.exceptions.MoveException;
import dssc.exam.draughts.exceptions.TileException;
import dssc.exam.draughts.utilities.Color;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestIfMoveValidator {
    private final Board board = new Board();

    @Property
    void throwsInvalidPositionException(@ForAll("invalidIndexGenerator") int sourceRow, @ForAll("invalidIndexGenerator") int sourceCol,
                                        @ForAll("invalidIndexGenerator") int destinationRow, @ForAll("invalidIndexGenerator") int destinationCol) {
        MoveValidator moveValidator = new MoveValidator(board, new Point(sourceRow, sourceCol), new Point(destinationRow, destinationCol));
        Exception exception = assertThrows(IndexException.class, moveValidator::throwExceptionIfPositionsAreInvalid);
        assertEquals("Position is not valid! Index must be between 1 and 8 for each axis!", exception.getMessage());
    }

    @Provide
    Arbitrary<Integer> invalidIndexGenerator() {
        return Arbitraries.integers().between(-10, 10).filter(n -> n < 0 || n > 7);
    }

    @ParameterizedTest
    @CsvSource({"0,0,1,1", "3,3,4,4", "5,5,6,6", "0,2,1,3"})
    void throwsInvalidTileException(int sourceRow, int sourceCol, int destinationRow, int destinationCol) {
        MoveValidator moveValidator = new MoveValidator(board, new Point(sourceRow, sourceCol), new Point(destinationRow, destinationCol));
        Exception exception = assertThrows(TileException.class, moveValidator::throwExceptionIfPositionsAreInvalid);
        assertEquals("Cannot play on white tiles, only black ones, please change position!", exception.getMessage());
    }

    @Property
    void checksDiagonalPosition(@ForAll("subSquareGenerator") int row, @ForAll("subSquareGenerator") int column, @ForAll("offset") Integer[] offset) {
        if (checkIfTileIsBlack(row, column) && checkIfTileIsBlack(row + offset[0], column + offset[1])) {
            MoveValidator moveValidator = new MoveValidator(board, new Point(row, column),
                    new Point(row + offset[0], column + offset[1]));
            Exception exception = assertThrows(MoveException.class, moveValidator::throwExceptionIfPositionsAreInvalid);
            assertEquals("Checker can only move diagonally!", exception.getMessage());
        }
    }

    @Property
    void throwsErrorIfSamePosition(@ForAll("validIndexGenerator") int row, @ForAll("validIndexGenerator") int column) {
        Board board = new Board();
        if (checkIfTileIsBlack(row, column)) {
            MoveValidator moveValidator = new MoveValidator(board, new Point(row, column), new Point(row, column));
            Exception exception = assertThrows(MoveException.class, moveValidator::throwExceptionIfPositionsAreInvalid);
            assertEquals("Checker can move only by one or two tiles!", exception.getMessage());
        }
    }

    @Provide
    Arbitrary<Integer> validIndexGenerator() {
        return Arbitraries.integers().between(0, 7);
    }

    private boolean checkIfTileIsBlack(int row, int column) {
        return board.getTile(row, column).getColor() == Color.BLACK;
    }

    @Test
    void checksForCorrectDirectionOfAPiece() {
        var newBoard = new Board();
        newBoard.getTile(1, 0).popPiece();
        MoveValidator moveValidator = new MoveValidator(newBoard, new Point(2, 1), new Point(1, 0));
        Exception exception = assertThrows(Exception.class, moveValidator::throwExceptionIfPositionsAreInvalid);
        assertEquals("You are moving in the opposite rowOffset!", exception.getMessage());
    }

    @Test
    void allowsToMoveOnlyByOneOrTwoTiles() {
        var newBoard = new Board();
        MoveValidator moveValidator = new MoveValidator(newBoard, new Point(2, 1), new Point(6, 5));
        Exception exception = assertThrows(Exception.class, moveValidator::throwExceptionIfPositionsAreInvalid);
        assertEquals("Checker can move only by one or two tiles!", exception.getMessage());
    }

    @Provide
    Arbitrary<Integer> subSquareGenerator() {
        return Arbitraries.integers().between(1, 6);
    }


    @Provide
    Arbitrary<Integer[]> offset() {
        Arbitrary<Integer> integerArbitrary = Arbitraries.integers().between(-1, 1);
        return integerArbitrary.array(Integer[].class).ofSize(2).filter(x -> !x[0].equals(x[1]) && (x[0] == 0 || x[1] == 0));
    }

}
