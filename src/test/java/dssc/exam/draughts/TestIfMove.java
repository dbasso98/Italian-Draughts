package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestIfMove {

    @Test
    void canMoveDiagonallyToEmptySpace() throws Exception{
        var board = new Board();
        assertTrue(board.getTile(3,2).isTileEmpty());
        assertTrue(board.getTile(2,1 ).isTileNotEmpty());
        try{
        Move.simpleDiagonalMove(board, new Point(2,1), new Point(3,2));
        assertTrue(board.getTile(2,1).isTileEmpty());
        assertTrue(board.getTile(3,2 ).isTileNotEmpty());
        }
        catch (Exception e){
            throw e;
        }
    }

    @Test
    void doesntMoveDiagonallyIfTileIsOccupied() {
        var board = new Board();
        assertTrue(board.getTile(1,2).isTileNotEmpty());
        assertTrue(board.getTile(2,1 ).isTileNotEmpty());
        Exception exception = assertThrows(Exception.class, () -> Move.simpleDiagonalMove(board, new Point(1,2), new Point(2,1)));
        assertEquals("Cannot move since tile at (2,3) is not empty", exception.getMessage());
    }

    @Test
    void canSkipMove() throws Exception {
        var board = new Board();
        assertTrue(board.getTile(2,1 ).isTileNotEmpty());
        Move.movePiece(board, new Point(3,2), board.getTile(new Point(5,2)));
        assertTrue(board.getTile(3,2).isTileNotEmpty());
        assertTrue(board.getTile(5,2).isTileEmpty());
        try{
            Move.simpleSkipMove(board, new Point(2,1), new Point(4,3));
            assertTrue(board.getTile(2,1).isTileEmpty());
            assertTrue(board.getTile(3,2).isTileEmpty());
            assertTrue(board.getTile(4,3).isTileNotEmpty());
        }
        catch (Exception e){
            throw e;
        }
    }


}
