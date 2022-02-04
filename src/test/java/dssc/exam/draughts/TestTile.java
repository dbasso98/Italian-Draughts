package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestTile {

    Tile emptytile = new Tile();
    Piece pieceBlack1 = new Piece(1, Color.BLACK);
    Tile tileBlack1 = new Tile(pieceBlack1);

    @Test
    void createNonEmptyTile() {
        assertEquals(tileBlack1.piece.id, 1);
        assertEquals(tileBlack1.piece.color, Color.BLACK);
    }

    @Test
    void createEmptyTile() {
        assertNull(emptytile.piece);
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
