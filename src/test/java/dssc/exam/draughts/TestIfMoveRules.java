package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.InvalidIndexException;
import dssc.exam.draughts.exceptions.NotDiagonalMoveException;
import dssc.exam.draughts.exceptions.SamePositionException;
import dssc.exam.draughts.exceptions.WhiteTileException;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfMoveRules {
    private Board board = new Board();

    @Property
    void throwsInvalidPositionException(@ForAll("invalidIndexGenerator") int sourceRow, @ForAll("invalidIndexGenerator") int sourceCol,
                                             @ForAll("invalidIndexGenerator") int destinationRow, @ForAll("invalidIndexGenerator") int destinationCol) {
        Exception exception = assertThrows(InvalidIndexException.class, () -> MoveRules.checkIfPositionsAreValid(board, new Point(sourceRow, sourceCol), new Point(destinationRow, destinationCol)));
        assertEquals("Every position must be in range of 1 to 8 for each axis!", exception.getMessage());
    }
    @Provide
    Arbitrary<Integer> invalidIndexGenerator () {
        return Arbitraries.integers().between(-10, 10).filter(n -> n<0 || n>7);
    }

    @ParameterizedTest
    @CsvSource({"0,0,1,1", "3,3,4,4", "5,5,6,6", "0,2,1,3"})
    void throwsInvalidTileException(int sourceRow, int sourceCol, int destinationRow, int destinationCol) {
        Exception exception = assertThrows(WhiteTileException.class, () -> MoveRules.checkIfPositionsAreValid(board, new Point(sourceRow, sourceCol), new Point(destinationRow, destinationCol)));
        assertEquals("Cannot play on white tiles, only black ones, please change position!", exception.getMessage());
    }

    @Property
    void checksSamePosition(@ForAll("validIndexGenerator") int row, @ForAll("validIndexGenerator") int column) throws Exception{
        if (checkIfTileIsBlack(row, column)) {
            Exception exception = assertThrows(SamePositionException.class, () -> MoveRules.checkIfPositionsAreValid(board, new Point(row, column), new Point(row, column)));
            assertEquals("Source and destination position cannot be the same!", exception.getMessage());
        }
    }

    @Provide
    Arbitrary<Integer> validIndexGenerator () {
        return Arbitraries.integers().between(0, 7);
    }

    @Property
    void checksDiagonalPosition(@ForAll("subSquareGenerator") int row, @ForAll("subSquareGenerator") int column, @ForAll("offset") Integer[] offset) throws Exception{
        if (checkIfTileIsBlack(row, column) && checkIfTileIsBlack(row + offset[0], column + offset[1])) {
            Exception exception = assertThrows(NotDiagonalMoveException.class, () -> MoveRules.checkIfPositionsAreValid(board, new Point(row, column),
                    new Point(row + offset[0], column + offset[1])));
            assertEquals("Checker can only move diagonally!", exception.getMessage());
        }
    }
    @Provide
    Arbitrary<Integer> subSquareGenerator(){ return Arbitraries.integers().between(1, 6);}

    @Provide
    Arbitrary<Integer[]> offset() {
        Arbitrary<Integer> integerArbitrary = Arbitraries.integers().between(-1,1);
        return integerArbitrary.array(Integer[].class).ofSize(2).filter(x -> x[0]!=x[1] && (x[0]==0 || x[1]==0) );
    }

    private boolean checkIfTileIsBlack(int row, int column) throws Exception{
        return board.getTile(row, column).getTileColor() == Color.BLACK;
    }

    @Test
    void checksPresenceOfManInAdjacentDiagonals() throws Exception{
        var newBoard = new Board();
        Move.movePiece(newBoard, new Point(5,4), new Point(3,2));
        assertEquals(1, MoveRules.checkAdjacentDiagonal(newBoard, newBoard.getTile(new Point(2,1)), Color.WHITE, 1, 0));
    }

    @Test
    void checksAbsenceOfManInAdjacentDiagonals() throws Exception{
        var newBoard = new Board();
        assertEquals(0,MoveRules.checkAdjacentDiagonal(newBoard, newBoard.getTile(new Point(2,1)), Color.WHITE, 1, 0));
    }

    @Test
    void checksCandidateTilesForSkipMove() throws Exception{
        var newBoard = new Board();
        Move.movePiece(newBoard, new Point(5,4), new Point(3,2));
        assertEquals(2,MoveRules.candidateTilesForSkipMove(newBoard, Color.WHITE).size());
        assertEquals(0,MoveRules.candidateTilesForSkipMove(newBoard, Color.BLACK).size());
    }

    @Test
    void checksCandidateTilesForSkipMoveIsEmptyAtBeginning() {
        var newBoard = new Board();
        assertEquals(0,MoveRules.candidateTilesForSkipMove(newBoard, Color.WHITE).size());
        assertEquals(0,MoveRules.candidateTilesForSkipMove(newBoard, Color.BLACK).size());
    }

    @Test
    void checkCandidateTilesForMoreThanOneSkip() throws Exception{
        var newBoard = new Board();
        Move.movePiece(newBoard, new Point(5,4), new Point(3,2));
        Move.movePiece(newBoard, new Point(6,1), new Point(5,4));
        Move.movePiece(newBoard, new Point(6,5), new Point(4,7));
        assertEquals(2,MoveRules.candidateTilesForSkipMove(newBoard, Color.WHITE).size());
    }

    @Test
    void checkSkipsForKingMove() throws Exception{
        var newBoard = new Board();
        newBoard.getPieceAtTile(2,1).upgradePieceToKing();
        Move.movePiece(newBoard, new Point(5,4), new Point(3,2));
        Move.movePiece(newBoard, new Point(6,1), new Point(5,4));
        Move.movePiece(newBoard, new Point(6,5), new Point(3,6));
        assertEquals(3, Collections.max(MoveRules.candidateTilesForSkipMove(newBoard, Color.WHITE).values()));
    }

}
