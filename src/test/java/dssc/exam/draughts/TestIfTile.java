package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import javax.swing.text.Position;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfTile {

    Tile emptyTile = new Tile(Color.BLACK, new Point(0,0));
    Tile blackTileBlackMan = new Tile(new Piece(1, Color.BLACK), Color.BLACK, new Point(0,0));

    @Test
    void createNonEmptyTile() {
        assertEquals(blackTileBlackMan.getTilePiece().getIdOfPiece(), 1);
        assertEquals(blackTileBlackMan.getTilePiece().getColorOfPiece(), Color.BLACK);
    }

    @Test
    void createEmptyTile() {
        assertNull(emptyTile.getTilePiece());
    }

    @Test
    void emptinessOfTile() {
        assertTrue(emptyTile.isTileEmpty());
    }

    @Test
    void nonEmptinessOfTile() {
        assertFalse(blackTileBlackMan.isTileEmpty());
    }

    @Test
    void emptyTileDisplay(){
        assertEquals("[ ]", emptyTile.display());
    }

    @Test
    void nonemptyTileDisplay(){
        assertEquals("[b]", blackTileBlackMan.display());
        Tile blackTileWhiteKing = new Tile(new Piece(1, Color.WHITE), Color.BLACK, new Point(0,0));
        blackTileWhiteKing.getTilePiece().upgradePieceToKing();
        assertEquals("[W]", blackTileWhiteKing.display());
    }


}
