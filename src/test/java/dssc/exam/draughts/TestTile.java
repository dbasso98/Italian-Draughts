package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTile {

    @Test
    void createNonEmptyTile() {
        Piece piece = new Piece(1, Color.BLACK);
        Tile tile = new Tile(piece);
        assertEquals(tile.piece.id, 1);
        assertEquals(tile.piece.color, Color.BLACK);
    }

}
