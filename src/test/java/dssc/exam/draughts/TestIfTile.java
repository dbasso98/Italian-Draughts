package dssc.exam.draughts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfTile {

    Tile emptyTile = new Tile(Color.BLACK, new Point(0,0));
    Tile blackTileBlackMan = new Tile(Color.BLACK, new Point(0,0));

    @Test
    void isEmpty() {
        assertTrue(emptyTile.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"BLACK, BLACK", "WHITE, WHITE"})
    void isOfColor(Color colorOfTile, Color expectedColor){
        Tile coloredTile = new Tile(colorOfTile, new Point(0,0));
        assertEquals(expectedColor, coloredTile.getColor());
    }

    @Test
    void isNotEmptyIfPieceIsSetOnIt() {
        blackTileBlackMan.setPiece(new Piece(1, Color.BLACK));
        assertFalse(blackTileBlackMan.isEmpty());
    }

    @Test
    void correctlyDisplaysEmptyTile(){
        assertEquals("[ ]", emptyTile.display());
    }

    @Test
    void correctlyDisplaysTileOncePieceIsSetOnIT(){
        blackTileBlackMan.setPiece(new Piece(1, Color.BLACK));
        assertEquals("[b]", blackTileBlackMan.display());

        Tile blackTileWhiteKing = new Tile(Color.BLACK, new Point(0,0));
        blackTileWhiteKing.setPiece(new Piece(1, Color.WHITE));
        blackTileWhiteKing.getPiece().upgradeToKing();

        assertEquals("[W]", blackTileWhiteKing.display());
    }


}
