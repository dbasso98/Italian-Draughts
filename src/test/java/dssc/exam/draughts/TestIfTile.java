package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfTile {

    Tile emptyTile = new Tile(Color.BLACK, new Point(0,0));
    Tile blackTileBlackMan = new Tile(new Piece(1, Color.BLACK), Color.BLACK, new Point(0,0));

    @Test
    void createNonEmptyTile() {
        assertEquals(blackTileBlackMan.getPiece().getId(), 1);
        assertEquals(blackTileBlackMan.getPiece().getColor(), Color.BLACK);
    }

    @Test
    void createEmptyTile() {
        assertNull(emptyTile.getPiece());
    }

    @Test
    void emptinessOfTile() {
        assertTrue(emptyTile.isEmpty());
    }

    @Test
    void nonEmptinessOfTile() {
        assertFalse(blackTileBlackMan.isEmpty());
    }

    @Test
    void emptyTileDisplay(){
        assertEquals("[ ]", emptyTile.display());
    }

    @Test
    void nonemptyTileDisplay(){
        assertEquals("[b]", blackTileBlackMan.display());
        Tile blackTileWhiteKing = new Tile(new Piece(1, Color.WHITE), Color.BLACK, new Point(0,0));
        blackTileWhiteKing.getPiece().upgradeToKing();
        assertEquals("[W]", blackTileWhiteKing.display());
    }


}
