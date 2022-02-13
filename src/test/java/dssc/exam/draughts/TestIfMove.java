package dssc.exam.draughts;

import org.junit.jupiter.api.Test;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestIfMove {

    @Test
    void canMoveDiagonallyToEmptySpace() throws Exception{
        var board = new Board();
        assertTrue(board.getTile(3,2).isEmpty());
        assertTrue(board.getTile(2,1 ).isNotEmpty());
        try{
        Move.diagonalMove(board, new Point(2,1), new Point(3,2));
        assertTrue(board.getTile(2,1).isEmpty());
        assertTrue(board.getTile(3,2 ).isNotEmpty());
        }
        catch (Exception e){
            throw e;
        }
    }

    @Test
    void doesntMoveDiagonallyIfTileIsOccupied() throws Exception{
        var board = new Board();
        assertTrue(board.getTile(1,2).isNotEmpty());
        assertTrue(board.getTile(2,1 ).isNotEmpty());
        Exception exception = assertThrows(Exception.class, () -> Move.diagonalMove(board, new Point(1,2), new Point(2,1)));
        assertEquals("Cannot move since tile at (2,3) is not empty", exception.getMessage());
    }

    @Test
    void canSkipMove() throws Exception {
        var board = new Board();
        assertTrue(board.getTile(2,1 ).isNotEmpty());
        Move.movePiece(board, new Point(5,2), new Point(3,2));
        assertTrue(board.getTile(3,2).isNotEmpty());
        assertTrue(board.getTile(5,2).isEmpty());
        try{
            Move.skipMove(board, new Point(2,1), new Point(4,3));
            assertTrue(board.getTile(2,1).isEmpty());
            assertTrue(board.getTile(3,2).isEmpty());
            assertTrue(board.getTile(4,3).isNotEmpty());
        }
        catch (Exception e){
            throw e;
        }
    }
}
