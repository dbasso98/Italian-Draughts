package dssc.exam.draughts;

import dssc.exam.draughts.core.Piece;
import dssc.exam.draughts.core.Tile;
import dssc.exam.draughts.utilities.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfTile {

    Tile emptyTile = new Tile(dssc.exam.draughts.utilities.Color.BLACK, new Point(0,0));
    Tile blackTileBlackMan = new Tile(dssc.exam.draughts.utilities.Color.BLACK, new Point(0,0));

    @Test
    void isEmpty() {
        assertTrue(emptyTile.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"BLACK, BLACK", "WHITE, WHITE"})
    void isOfColor(dssc.exam.draughts.utilities.Color colorOfTile, dssc.exam.draughts.utilities.Color expectedColor){
        Tile coloredTile = new Tile(colorOfTile, new Point(0,0));
        assertEquals(expectedColor, coloredTile.getColor());
    }

    @BeforeEach
    void setBlackPieceOnTile(){
        blackTileBlackMan.setPiece(new Piece(Color.BLACK));
    }

    @Test
    void isNotEmptyIfPieceIsSetOnIt() {
        assertFalse(blackTileBlackMan.isEmpty());
    }
}
