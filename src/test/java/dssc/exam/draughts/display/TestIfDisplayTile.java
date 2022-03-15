package dssc.exam.draughts.display;

import dssc.exam.draughts.utilities.Color;
import dssc.exam.draughts.core.Piece;
import dssc.exam.draughts.core.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestIfDisplayTile {
    Tile emptyTile = new Tile(Color.BLACK, new Point(0,0));
    Tile blackTileBlackMan = new Tile(Color.BLACK, new Point(0,0));
    ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();

    @Test
    void correctlyDisplaysEmptyTile(){
        System.setOut(new PrintStream(fakeStandardOutput));
        emptyTile.display();
        assertEquals("[ ]", fakeStandardOutput.toString());

    }

    @BeforeEach
    void setBlackPiece(){
        blackTileBlackMan.setPiece(new Piece(Color.BLACK));
    }

    @Test
    void correctlyDisplaysTileOnceBlackPieceIsSetOnIt(){
        System.setOut(new PrintStream(fakeStandardOutput));
        blackTileBlackMan.display();
        assertEquals("[b]", fakeStandardOutput.toString());
    }

    @Test
    void correctlyDisplaysTileOnceBlackKingIsPlacedOnIt(){
        System.setOut(new PrintStream(fakeStandardOutput));
        blackTileBlackMan.getPiece().upgradeToKing();
        blackTileBlackMan.display();
        assertEquals("[B]", fakeStandardOutput.toString());
    }
}
