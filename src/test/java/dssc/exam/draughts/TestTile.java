package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTile {

    Tile empty_tile = new Tile();
    Tile black_tile_black_piece = new Tile(new Piece(1, Color.BLACK), Color.BLACK);

    @Test
    void createNonEmptyTile() {
        assertEquals(black_tile_black_piece.piece.id, 1);
        assertEquals(black_tile_black_piece.piece.piece_color, Color.BLACK);
    }

    @Test
    void createEmptyTile() {
        assertNull(empty_tile.piece);
    }

    @Test
    void emptinessOfTile() {
        assertTrue(empty_tile.isTileEmpty());
    }

    @Test
    void nonEmptinessOfTile() {
        assertFalse(black_tile_black_piece.isTileEmpty());
    }

}
