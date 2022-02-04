package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestTile {

    Tile emptytile = new Tile();

    @Test
    void createNonEmptyTile() {
        Piece piece = new Piece(1, Color.BLACK);
        Tile tile = new Tile(piece);
        assertEquals(tile.piece.id, 1);
        assertEquals(tile.piece.color, Color.BLACK);
    }

    @Test
    void createEmptyTile() {
        Tile tile = new Tile();
        assertNull(tile.piece);
    }

    @Test
    void emptinessOfTile() {
        assertEquals(emptytile.empty(), true);
    }

    @Test
    void NonEmptinessOfTile() {
        Tile tile = new Tile(new Piece(1, Color.BLACK));
        assertEquals(tile.empty(), false);
    }

}
