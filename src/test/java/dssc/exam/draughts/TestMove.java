package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestMove {

    @Test
    void canMoveDiagonallyToEmptySpace() throws Exception{
        var board = new Board();
        assertTrue(board.getTile(3,2).isTileEmpty());
        assertTrue(board.getTile(2,1 ).isTileNotEmpty());
        try{
        Move.moveDiagonallyToEmptyTile(board, new Point(2,1), new Point(3,2));
        assertTrue(board.getTile(2,1).isTileEmpty());
        assertTrue(board.getTile(3,2 ).isTileNotEmpty());
        }
        catch (Exception e){
            throw e;
        }
    }
    @Test
    void doesntMoveDiagonallyIfTileIsOccupied() throws Exception{
        var board = new Board();
        assertTrue(board.getTile(3,2).isTileEmpty());
        assertTrue(board.getTile(2,1 ).isTileNotEmpty());
        try{
            Move.moveDiagonallyToEmptyTile(board, new Point(2,1), new Point(3,2));
            assertTrue(board.getTile(2,1).isTileEmpty());
            assertTrue(board.getTile(3,2 ).isTileNotEmpty());
        }
        catch (Exception e){
            throw e;
        }
    }
}
