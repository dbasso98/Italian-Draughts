package dssc.exam.draughts;

import dssc.exam.draughts.display.DisplayBoard;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfCandidateSkipPathBuilder {

    @Test
    void findsCandidateTilesForSkipMove() {
        var board = new Board();
        new Move(board, new Point(5, 4), new Point(3, 2)).movePiece();
        assertEquals(2, CandidateSkipPathBuilder.build(board, Color.WHITE).size());
        assertEquals(0, CandidateSkipPathBuilder.build(board, Color.BLACK).size());
    }

    @Test
    void doesntFindAnyCandidateTileForSkipMoveAtBeginning() {
        var board = new Board();
        assertEquals(0, CandidateSkipPathBuilder.build(board, Color.WHITE).size());
        assertEquals(0, CandidateSkipPathBuilder.build(board, Color.BLACK).size());
    }

    @Test
    void findsAPathWithMoreThanOneSkip() {
        var board = new CustomizableBoard()
                .popPiecesAt(Arrays.asList(44, 49, 53))
                .setMultipleManAt(Arrays.asList(26, 44, 30), Color.BLACK);
        assertEquals(2, Collections.max((CandidateSkipPathBuilder.build(board, Color.WHITE).values().stream()
                                                                                        .map(Path::getNumberOfSkips)
                                                                                        .collect(Collectors.toList()))));
    }

    @Test
    void stopsMoveAfterThreeCompletedSkips() {
        var board = new CustomizableBoard()
                .upgradeToKing(Arrays.asList(17))
                .popPiecesAt(Arrays.asList(44, 49, 53, 21))
                .setMultipleManAt(Arrays.asList(26, 44, 30, 28), Color.BLACK);
        assertEquals(3, Collections.max(CandidateSkipPathBuilder.build(board, Color.WHITE).values().stream()
                                                                                        .map(Path::getNumberOfSkips)
                                                                                        .collect(Collectors.toList())));
    }

    @Test
    void doesNotAllowAManToSkipAKing() {
        var board = new Board();
        new Move(board, new Point(6, 5), new Point(3, 2)).movePiece();
        board.getPieceAtTile(5, 4).upgradeToKing();
        var candidateTilesToStartASkip = CandidateSkipPathBuilder.build(board, Color.WHITE);
        var pathOfTileThatCannotSkipAKing = candidateTilesToStartASkip.get(board.getTile(2, 1));
        assertEquals(1, pathOfTileThatCannotSkipAKing.getNumberOfSkips());
        assertEquals(10, pathOfTileThatCannotSkipAKing.getWeight());
    }

    @Test
    void givesHigherScoreToPathWithMostKingsToEat() {
        var board = new CustomizableBoard()
                .popPiecesAt(Arrays.asList(46, 49, 53))
                .setMultipleManAt(Arrays.asList(26, 28, 32, 40), Color.BLACK)
                .upgradeToKing(Arrays.asList(17, 21, 26, 44));
        var pathValues = CandidateSkipPathBuilder.build(board, Color.WHITE).values();
        assertEquals(45, Collections.max((pathValues.stream()
                                                            .map(Path::getWeight)
                                                            .collect(Collectors.toList()))));
        assertEquals(board.getTile(2, 1), pathValues.stream()
                                                            .filter(path -> path.getWeight() == 45)
                                                            .map(Path::getSource)
                                                            .collect(Collectors.toList())
                                                            .get(0));
    }

    @Test
    void givesHigherScoreToPathWithFirstOccurrenceOfAKing() {
        var board = new CustomizableBoard()
                .upgradeToKing(Arrays.asList(17, 19, 44))
                .popPiecesAt(Arrays.asList(46, 49, 51, 40))
                .setMultipleManAt(Arrays.asList(28, 26, 39, 33), Color.BLACK)
                .upgradeToKing(Arrays.asList(26));
        var pathValues = CandidateSkipPathBuilder.build(board, Color.WHITE).values();
        assertEquals(38, Collections.max((pathValues.stream()
                                                            .map(Path::getWeight)
                                                            .collect(Collectors.toList()))));
        assertEquals(board.getTile(2, 1), pathValues.stream()
                                                            .filter(path -> path.getWeight() == 38)
                                                            .map(Path::getSource)
                                                            .collect(Collectors.toList())
                                                            .get(0));
    }

}