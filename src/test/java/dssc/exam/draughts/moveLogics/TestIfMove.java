package dssc.exam.draughts.moveLogics;

import dssc.exam.draughts.core.CustomizableBoard;
import dssc.exam.draughts.core.Board;
import dssc.exam.draughts.exceptions.DraughtsException;
import dssc.exam.draughts.exceptions.MoveException;
import dssc.exam.draughts.exceptions.TileException;
import dssc.exam.draughts.utilities.Color;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfMove {

    @Test
    void doesntMoveDiagonallyIfTileIsOccupied() {
        var board = new Board();
        assertTrue(board.getTile(1, 2).isNotEmpty());
        assertTrue(board.getTile(2, 1).isNotEmpty());
        TileException exception = assertThrows(TileException.class, () -> new Move(board, new Point(1, 2), new Point(2, 1)).moveDecider());
        assertEquals("Cannot move since tile at (2,3) is not empty", exception.getMessage());
    }

    @Test
    void doesASimpleMove() throws DraughtsException {
        var board = new Board();
        new Move(board, new Point(2, 1), new Point(3, 2)).moveDecider();
        assertTrue(board.getTile(2, 1).isEmpty());
        assertTrue(board.getTile(3, 2).isNotEmpty());
    }

    @Test
    void doesASkipMove() throws DraughtsException {
        var board = new Board();
        new Move(board, new Point(5, 4), new Point(3, 2)).movePiece();
        new Move(board, new Point(2, 1), new Point(4, 3)).moveDecider();
        assertTrue(board.getTile(3, 2).isEmpty());
        assertTrue(board.getTile(2, 1).isEmpty());
        assertTrue(board.getTile(4, 3).isNotEmpty());
    }

    @Test
    void updatesManToKingWhenLastRowIsReached() {
        var board = new Board();
        new Move(board, new Point(2, 1), new Point(7, 7)).performTheMove();
        assertTrue(board.getPieceAtTile(7, 7).isKing());
    }

    @Test
    void suggestsOptimalMove() {
        var board = new CustomizableBoard()
                .upgradeToKing(List.of(17))
                .popPiecesAt(Arrays.asList(44, 49, 53))
                .setMultipleManAt(Arrays.asList(26, 44, 30), Color.BLACK);
        MoveException exception = assertThrows(MoveException.class, () -> new Move(board, new Point(2, 1), new Point(3, 0)).moveDecider());
        assertEquals("There are pieces that must capture, try these positions: (2,3)", exception.getMessage());
    }

    @Test
    void suggestsOptimalMoveEvenIfOriginalIsASkip() {
        var board = new CustomizableBoard()
                .upgradeToKing(List.of(17))
                .popPiecesAt(Arrays.asList(44, 49, 53))
                .setMultipleManAt(Arrays.asList(26, 44, 30), Color.BLACK);
        MoveException exception = assertThrows(MoveException.class, () -> new Move(board, new Point(2, 3), new Point(4, 1)).moveDecider());
        assertEquals("You can select a better skip! Choose one of the tiles at these positions: (2,3)", exception.getMessage());
    }

    @Test
    void skipsWithAKingInsteadOfAMan() {
        var board = new Board();
        board.getPieceAtTile(2, 1).upgradeToKing();
        new Move(board, new Point(5, 4), new Point(3, 2)).movePiece();
        MoveException exception = assertThrows(MoveException.class, () -> new Move(board, new Point(2, 3), new Point(4, 1)).moveDecider());
        assertEquals("You should skip with a King instead of a Man! Choose one of these positions: (2,3)", exception.getMessage());
    }

    @Test
    void isDoneIfNoneOfPreviousRulesApplies() throws Exception {
        var board = new CustomizableBoard()
                .removeAllPieces()
                .setMultipleManAt(Arrays.asList(1, 3), Color.WHITE);
        board.upgradeToKing(Arrays.asList(1, 3)).setKingAtTile(1, 2, Color.BLACK);
        new Move(board, new Point(0, 1), new Point(2, 3)).moveDecider();
        assertTrue(board.getTile(0, 1).isEmpty());
        assertTrue(board.getTile(2, 3).isNotEmpty());
    }
}
