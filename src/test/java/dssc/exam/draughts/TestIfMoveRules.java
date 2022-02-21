package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.*;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfMoveRules {
    private final Board board = new Board();

    @Property
    void throwsInvalidPositionException(@ForAll("invalidIndexGenerator") int sourceRow, @ForAll("invalidIndexGenerator") int sourceCol,
                                        @ForAll("invalidIndexGenerator") int destinationRow, @ForAll("invalidIndexGenerator") int destinationCol) {
        Exception exception = assertThrows(InvalidIndexException.class, () -> MoveRules.checkIfPositionsAreValid(board, new Point(sourceRow, sourceCol), new Point(destinationRow, destinationCol)));
        assertEquals("Position is not valid! Index must be between 1 and 8 for each axis!", exception.getMessage());
    }

    @Provide
    Arbitrary<Integer> invalidIndexGenerator() {
        return Arbitraries.integers().between(-10, 10).filter(n -> n < 0 || n > 7);
    }

    @ParameterizedTest
    @CsvSource({"0,0,1,1", "3,3,4,4", "5,5,6,6", "0,2,1,3"})
    void throwsInvalidTileException(int sourceRow, int sourceCol, int destinationRow, int destinationCol) {
        Exception exception = assertThrows(WhiteTileException.class, () -> MoveRules.checkIfPositionsAreValid(board, new Point(sourceRow, sourceCol), new Point(destinationRow, destinationCol)));
        assertEquals("Cannot play on white tiles, only black ones, please change position!", exception.getMessage());
    }

    @Property
    void checksSamePosition(@ForAll("validIndexGenerator") int row, @ForAll("validIndexGenerator") int column) {
        if (checkIfTileIsBlack(row, column)) {
            Exception exception = assertThrows(SamePositionException.class, () -> MoveRules.checkIfPositionsAreValid(board, new Point(row, column), new Point(row, column)));
            assertEquals("Source and destination position cannot be the same!", exception.getMessage());
        }
    }

    @Provide
    Arbitrary<Integer> validIndexGenerator() {
        return Arbitraries.integers().between(0, 7);
    }

    @Property
    void checksDiagonalPosition(@ForAll("subSquareGenerator") int row, @ForAll("subSquareGenerator") int column, @ForAll("offset") Integer[] offset){
        if (checkIfTileIsBlack(row, column) && checkIfTileIsBlack(row + offset[0], column + offset[1])) {
            Exception exception = assertThrows(NotDiagonalMoveException.class, () -> MoveRules.checkIfPositionsAreValid(board, new Point(row, column),
                    new Point(row + offset[0], column + offset[1])));
            assertEquals("Checker can only move diagonally!", exception.getMessage());
        }
    }

    @Provide
    Arbitrary<Integer> subSquareGenerator() {
        return Arbitraries.integers().between(1, 6);
    }

    @Provide
    Arbitrary<Integer[]> offset() {
        Arbitrary<Integer> integerArbitrary = Arbitraries.integers().between(-1, 1);
        return integerArbitrary.array(Integer[].class).ofSize(2).filter(x -> x[0] != x[1] && (x[0] == 0 || x[1] == 0));
    }

    private boolean checkIfTileIsBlack(int row, int column){
        return board.getTile(row, column).getColor() == Color.BLACK;
    }

    @Test
    void checksPresenceOfManInAdjacentDiagonals() throws Exception{
        var newBoard = new Board();
        Move.movePiece(newBoard, new Point(5, 4), new Point(3, 2));
        var bestPath = new Path(newBoard.getTile(new Point(2, 1)));
        MoveRules.buildPathStartingFromMan(newBoard, newBoard.getTile(new Point(2, 1)), bestPath);
        assertEquals(10, bestPath.getWeight());
    }

    @Test
    void checksAbsenceOfManInAdjacentDiagonals() {
        var newBoard = new Board();
        var bestPath = new Path(newBoard.getTile(new Point(2, 1)));
        MoveRules.buildPathStartingFromMan(newBoard, newBoard.getTile(new Point(2, 1)), bestPath);
        assertEquals(0, bestPath.getWeight());
    }

    @Test
    void checksCandidateTilesForSkipMove() throws Exception {
        var newBoard = new Board();
        Move.movePiece(newBoard, new Point(5, 4), new Point(3, 2));
        assertEquals(2, MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).size());
        assertEquals(0, MoveRules.candidatePathsForSkipMove(newBoard, Color.BLACK).size());
    }

    @Test
    void checksCandidateTilesForSkipMoveIsEmptyAtBeginning() {
        var newBoard = new Board();
        assertEquals(0, MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).size());
        assertEquals(0, MoveRules.candidatePathsForSkipMove(newBoard, Color.BLACK).size());
    }

    @Test
    void checksCandidateTilesForMoreThanOneSkip() throws Exception {
        var newBoard = new Board();
        Move.movePiece(newBoard, new Point(5, 4), new Point(3, 2));
        Move.movePiece(newBoard, new Point(6, 1), new Point(5, 4));
        Move.movePiece(newBoard, new Point(6, 5), new Point(3, 6));
        assertEquals(4, MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).size());
    }

    @Test
    void checksSkipsForKingMove() throws Exception {
        var newBoard = new Board();
        newBoard.getPieceAtTile(2, 1).upgradeToKing();
        Move.movePiece(newBoard, new Point(5, 4), new Point(3, 2));
        Move.movePiece(newBoard, new Point(6, 1), new Point(5, 4));
        Move.movePiece(newBoard, new Point(6, 5), new Point(3, 6));
        assertEquals(60, Collections.max((MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).values().stream()
                                                                                                            .map(path -> path.getWeight())
                                                                                                            .collect(Collectors.toList()))));
    }

    @Test
    void doesntAllowTooLongMove() {
        var newBoard = new Board();
        Exception exception = assertThrows(Exception.class, () -> MoveRules.checkIfPositionsAreValid(newBoard, new Point(2,1), new Point(6,5)));
        assertEquals("Checker can move only by one or two tiles!", exception.getMessage());
    }

    @Test
    void checksForCorrectDirectionOfAPiece(){
        var newBoard = new Board();
        Exception exception = assertThrows(Exception.class, () -> MoveRules.checkIfPositionsAreValid(newBoard, new Point(2,1), new Point(1,0)));
        assertEquals("You are moving in the opposite direction!", exception.getMessage());
    }

    @Test
    void stopsMoveAfterThreeCompletedSkips() throws Exception {
        var newBoard = new Board();
        newBoard.getPieceAtTile(2, 1).upgradeToKing();
        Move.movePiece(newBoard, new Point(5, 4), new Point(3, 2));
        Move.movePiece(newBoard, new Point(6, 1), new Point(5, 4));
        Move.movePiece(newBoard, new Point(6, 5), new Point(3, 6));
        Move.movePiece(newBoard, new Point(2, 5), new Point(3, 4));
        assertEquals(60, Collections.max(MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).values().stream()
                                                                                                            .map(path -> path.getWeight())
                                                                                                            .collect(Collectors.toList())));
    }

    @Test
    void doesNotAllowAManToSkipAKing() throws Exception{
        var newBoard = new Board();
        Move.movePiece(newBoard, new Point(6, 5), new Point(3, 2));
        newBoard.getPieceAtTile(5, 4).upgradeToKing();
        assertEquals(2, MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).size());
        assertEquals(10,  Collections.max((MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).values().stream()
                                                                                                            .map(path -> path.getWeight())
                                                                                                            .collect(Collectors.toList()))));
    }

    @Test
    void givesHigherScoreToPathWithMostKingsToEat() throws Exception{
        var newBoard = new Board();
        newBoard.getPieceAtTile(2, 1).upgradeToKing();
        newBoard.getPieceAtTile(6, 5).upgradeToKing();
        newBoard.getPieceAtTile(5, 4).upgradeToKing();
        newBoard.getPieceAtTile(2, 5).upgradeToKing();
        Move.movePiece(newBoard, new Point(6, 5), new Point(3, 2));
        Move.movePiece(newBoard, new Point(5, 6), new Point(3, 4));
        Move.movePiece(newBoard, new Point(6, 1), new Point(4, 0));
        assertEquals(45, Collections.max((MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).values().stream()
                                                                                                            .map(path -> path.getWeight())
                                                                                                            .collect(Collectors.toList()))));
    }

    @Test
    void givesHigherScoreToPathWithFirstOccurrenceOfAKing() throws Exception {
        var newBoard = new Board();
        newBoard.getPieceAtTile(2, 1).upgradeToKing();
        newBoard.getPieceAtTile(2, 3).upgradeToKing();
        Move.movePiece(newBoard, new Point(5, 6), new Point(3, 4));
        Move.movePiece(newBoard, new Point(6, 1), new Point(3, 2));
        Move.movePiece(newBoard, new Point(6, 3), new Point(4, 7));
        Move.movePiece(newBoard, new Point(5, 0), new Point(4, 1));
        newBoard.getPieceAtTile(3, 2).upgradeToKing();
        newBoard.getPieceAtTile(5, 4).upgradeToKing();
        assertEquals(38, Collections.max((MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).values().stream()
                                                                                                            .map(path -> path.getWeight())
                                                                                                            .collect(Collectors.toList()))));
    }
}