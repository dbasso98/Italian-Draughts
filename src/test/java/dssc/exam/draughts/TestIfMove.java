package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.InvalidMoveException;
import dssc.exam.draughts.exceptions.NonEmptyTileException;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfMove {

    @Test
    void doesntMoveDiagonallyIfTileIsOccupied() {
        var board = new Board();
        assertTrue(board.getTile(1, 2).isNotEmpty());
        assertTrue(board.getTile(2, 1).isNotEmpty());
        NonEmptyTileException exception = assertThrows(NonEmptyTileException.class, () -> new Move(board, new Point(1, 2), new Point(2, 1)).diagonalMove());
        assertEquals("Cannot move since tile at (2,3) is not empty", exception.getMessage());
    }

    @Test
    void doesASimpleMove() throws Exception {
        var newBoard = new Board();
        new Move(newBoard, new Point(2, 1), new Point(3, 2)).moveDecider();
        assertTrue(newBoard.getTile(2, 1).isEmpty());
        assertTrue(newBoard.getTile(3, 2).isNotEmpty());
        assertEquals(17, newBoard.getPieceAtTile(3,2).getId());
    }

    @Test
    void doesASkipMove() throws Exception {
        var newBoard = new Board();
        new Move(newBoard, new Point(5, 4), new Point(3, 2)).movePiece();
        new Move(newBoard, new Point(2, 1), new Point(4, 3)).moveDecider();
        assertTrue(newBoard.getTile(3,2).isEmpty());
        assertTrue(newBoard.getTile(2, 1).isEmpty());
        assertTrue(newBoard.getTile(4, 3).isNotEmpty());
        assertEquals(17, newBoard.getPieceAtTile(4,3).getId());
    }

    @Test
    void updatesManToKingWhenLastRowIsReached() throws Exception {
        var newBoard = new Board();
        new Move(newBoard, new Point(2,1), new Point(7,7)).diagonalMove();
        assertTrue(newBoard.getPieceAtTile(7,7).isKing());
    }

    @Test
    void suggestsOptimalMove() {
        var newBoard = new Board();
        newBoard.getPieceAtTile(2, 1).upgradeToKing();
        new Move(newBoard, new Point(5, 4), new Point(3, 2)).movePiece();
        new Move(newBoard, new Point(6, 1), new Point(5, 4)).movePiece();
        new Move(newBoard, new Point(6, 5), new Point(3, 6)).movePiece();
        InvalidMoveException exception = assertThrows(InvalidMoveException.class, () -> new Move(newBoard, new Point(2, 1), new Point(3, 0)).moveDecider());
        assertEquals("There are pieces that must capture, try these positions: (2,3)", exception.getMessage());
    }

    @Test
    void suggestsOptimalMoveEvenIfOriginalIsASkip() {
        var newBoard = new Board();
        newBoard.getPieceAtTile(2, 1).upgradeToKing();
        new Move(newBoard, new Point(5, 4), new Point(3, 2)).movePiece();
        new Move(newBoard, new Point(6, 1), new Point(5, 4)).movePiece();
        new Move(newBoard, new Point(6, 5), new Point(3, 6)).movePiece();
        InvalidMoveException exception = assertThrows(InvalidMoveException.class, () -> new Move(newBoard, new Point(2, 3), new Point(4, 1)).moveDecider());
        assertEquals("You can select a better skip! Choose one of the tiles at these positions: (2,3)", exception.getMessage());
    }

    @Test
    void skipsWithAKingInsteadOfAMan() {
        var newBoard = new Board();
        newBoard.getPieceAtTile(2, 1).upgradeToKing();
        new Move(newBoard, new Point(5, 4), new Point(3, 2)).movePiece();
        InvalidMoveException exception = assertThrows(InvalidMoveException.class, () -> new Move(newBoard, new Point(2, 3), new Point(4, 1)).moveDecider());
        assertEquals("You should skip with a King instead of a Man! Choose one of these positions: (2,3)", exception.getMessage());
    }
}
