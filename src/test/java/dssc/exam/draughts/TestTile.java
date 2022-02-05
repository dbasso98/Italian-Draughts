package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTile {

    Tile emptyTile = new Tile();
    Tile balckTileBlackMan = new Tile(new Piece(1, Color.BLACK), Color.BLACK);

    @Test
    void createNonEmptyTile() {
        assertEquals(balckTileBlackMan.piece.id, 1);
        assertEquals(balckTileBlackMan.piece.pieceColor, Color.BLACK);
    }

    @Test
    void createEmptyTile() {
        assertNull(emptyTile.piece);
    }

    @Test
    void emptinessOfTile() {
        assertTrue(emptyTile.isTileEmpty());
    }

    @Test
    void nonEmptinessOfTile() {
        assertFalse(balckTileBlackMan.isTileEmpty());
    }

    @Test
    void emptyTileDisplay(){
        assertEquals("[ ]", emptyTile.display());
    }

    @Test
    void nonemptyTileDisplay(){
        assertEquals("[b]", balckTileBlackMan.display());
        Tile blackTileWhiteKing = new Tile(new Piece(1, Color.WHITE), Color.BLACK);
        blackTileWhiteKing.piece.upgradePieceToKing();
        assertEquals("[W]", blackTileWhiteKing.display());
    }


}
