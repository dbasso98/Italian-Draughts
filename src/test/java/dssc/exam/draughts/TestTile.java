package dssc.exam.draughts;

import org.junit.jupiter.api.Test;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestTile {

    Tile empty_tile = new Tile();
    Tile black_tile_black_piece = new Tile(new Piece(1, Color.BLACK), new Point(0,0), Color.BLACK);

    @Test
    void createNonEmptyTile() {
        assertEquals(black_tile_black_piece.piece.id, 1);
        assertEquals(black_tile_black_piece.piece.color, Color.BLACK);
        assertEquals(black_tile_black_piece.position.getX(), 0);
        assertEquals(black_tile_black_piece.position.getY(), 0);
    }

    @Test
    // to transform into property test
    void testTilePositionX(){
        assertTrue(black_tile_black_piece.position.getX() >= 0);
        assertTrue(black_tile_black_piece.position.getX() <= 7);
    }

    @Test
    void testTilePositionY(){
        assertTrue(black_tile_black_piece.position.getY() >= 0);
        assertTrue(black_tile_black_piece.position.getY() <= 7);
    }

    @Test
    void createEmptyTile() {
        assertNull(empty_tile.piece);
    }

    @Test
    void emptinessOfTile() {
        assertTrue(empty_tile.is_tile_empty());
    }

    @Test
    void NonEmptinessOfTile() {
        assertFalse(black_tile_black_piece.is_tile_empty());
    }

}
