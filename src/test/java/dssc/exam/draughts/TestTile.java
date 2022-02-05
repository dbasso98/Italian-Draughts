package dssc.exam.draughts;

import org.junit.jupiter.api.Test;
import java.awt.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestTile {

    Tile empty_tile = new Tile();
    Tile tile_black_1 = new Tile(new Piece(1, Color.BLACK), new Point(0,0));

    @Test
    void createNonEmptyTile() {
        assertEquals(tile_black_1.piece.id, 1);
        assertEquals(tile_black_1.piece.color, Color.BLACK);
        assertEquals(tile_black_1.position.getX(), 0);
        assertEquals(tile_black_1.position.getY(), 0);
    }

    @Test
    // to transform into property test
    void testTilePositionX(){
        assertTrue(tile_black_1.position.getX() >= 0);
        assertTrue(tile_black_1.position.getX() <= 7);
    }

    @Test
    void testTilePositionY(){
        assertTrue(tile_black_1.position.getY() >= 0);
        assertTrue(tile_black_1.position.getY() <= 7);
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
        assertFalse(tile_black_1.is_tile_empty());
    }

}
