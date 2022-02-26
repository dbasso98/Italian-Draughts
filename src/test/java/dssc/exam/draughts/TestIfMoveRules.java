package dssc.exam.draughts;

import dssc.exam.draughts.IOInterfaces.OutInterfaceStdout;
import dssc.exam.draughts.exceptions.*;
import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfMoveRules {

    @Test
    void checksCandidateTilesForSkipMove() {
        var newBoard = new Board();
        new Move(newBoard, new Point(5, 4), new Point(3, 2)).movePiece();
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
    void checksCandidateTilesForMoreThanOneSkip() {
        var newBoard = new Board();
        new Move(newBoard, new Point(5, 4), new Point(3, 2)).movePiece();
        new Move(newBoard, new Point(6, 1), new Point(5, 4)).movePiece();
        new Move(newBoard, new Point(6, 5), new Point(3, 6)).movePiece();
        assertEquals(4, MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).size());
    }

    @Test
    void checksSkipsForKingMove() {
        var newBoard = new Board();
        newBoard.getPieceAtTile(2, 1).upgradeToKing();
        new Move(newBoard, new Point(5, 4), new Point(3, 2)).movePiece();
        new Move(newBoard, new Point(6, 1), new Point(5, 4)).movePiece();
        new Move(newBoard, new Point(6, 5), new Point(3, 6)).movePiece();
        assertEquals(60, Collections.max((MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).values().stream()
                .map(Path::getWeight).collect(Collectors.toList()))));
    }

    @Test
    void stopsMoveAfterThreeCompletedSkips() {
        var newBoard = new Board();
        newBoard.getPieceAtTile(2, 1).upgradeToKing();
        new Move(newBoard, new Point(5, 4), new Point(3, 2)).movePiece();
        new Move(newBoard, new Point(6, 1), new Point(5, 4)).movePiece();
        new Move(newBoard, new Point(6, 5), new Point(3, 6)).movePiece();
        new Move(newBoard, new Point(2, 5), new Point(3, 4)).movePiece();
        assertEquals(3, Collections.max(MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).values().stream()
                .map(Path::getNumberOfSkips)
                .collect(Collectors.toList())));
    }

    @Test
    void doesNotAllowAManToSkipAKing() {
        var newBoard = new Board();
        new Move(newBoard, new Point(6, 5), new Point(3, 2)).movePiece();
        newBoard.getPieceAtTile(5, 4).upgradeToKing();
        assertEquals(2, MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).size());
        assertEquals(10, Collections.max((MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE).values().stream()
                .map(Path::getWeight)
                .collect(Collectors.toList()))));
    }

    @Test
    void givesHigherScoreToPathWithMostKingsToEat() {
        CustomizableBoard board = new CustomizableBoard()
                .popPiecesAt(Arrays.asList(46, 49, 53))
                .setMultipleManAt(Arrays.asList(26, 28, 32, 40), Color.BLACK)
                .upgradeToKing(Arrays.asList(17, 21, 26, 44));

        var pathValues = MoveRules.candidatePathsForSkipMove(board, Color.WHITE)
                .values();

        assertEquals(45, Collections.max((pathValues.stream().
                map(Path::getWeight)
                .collect(Collectors.toList()))));

        assertEquals(board.getTile(2, 1),
                pathValues.stream()
                        .filter(path -> path.getWeight() == 45)
                        .map(Path::getSource)
                        .collect(Collectors.toList())
                        .get(0));
    }

    @Test
    void givesHigherScoreToPathWithFirstOccurrenceOfAKing() {
        var board = new CustomizableBoard();
        var newBoard = new Board();
        newBoard.getPieceAtTile(2, 1).upgradeToKing();
        newBoard.getPieceAtTile(2, 3).upgradeToKing();
        new Move(newBoard, new Point(5, 6), new Point(3, 4)).movePiece();
        new Move(newBoard, new Point(6, 1), new Point(3, 2)).movePiece();
        new Move(newBoard, new Point(6, 3), new Point(4, 7)).movePiece();
        new Move(newBoard, new Point(5, 0), new Point(4, 1)).movePiece();
        newBoard.getPieceAtTile(3, 2).upgradeToKing();
        newBoard.getPieceAtTile(5, 4).upgradeToKing();

        new OutInterfaceStdout().displayBoard(newBoard);
        new OutInterfaceStdout().displayBoard(board);

        var pathValues = MoveRules.candidatePathsForSkipMove(newBoard, Color.WHITE)
                .values();

        assertEquals(38, Collections.max((pathValues.stream()
                .map(Path::getWeight)
                .collect(Collectors.toList()))));
        assertEquals(newBoard.getTile(2, 1), pathValues.stream()
                .filter(path -> path.getWeight() == 38)
                .map(Path::getSource)
                .collect(Collectors.toList())
                .get(0));
    }

}