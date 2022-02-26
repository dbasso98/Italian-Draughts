package dssc.exam.draughts.display;

import dssc.exam.draughts.Color;
import dssc.exam.draughts.Tile;
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
