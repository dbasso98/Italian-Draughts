package dssc.exam.draughts;

import dssc.exam.draughts.core.Piece;
import dssc.exam.draughts.display.DisplayPiece;
import dssc.exam.draughts.utilities.Color;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfStringRepresentationOfPiece {
    ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();

    @Test
    void displayBlackMan(){
        System.setOut(new PrintStream(fakeStandardOutput));
        Piece blackMan = new Piece(Color.BLACK);
        new DisplayPiece().display(blackMan);
        assertEquals("[b]", fakeStandardOutput.toString());
    }

    @Test
    void displayWhiteMan(){
        System.setOut(new PrintStream(fakeStandardOutput));
        Piece whiteMan = new Piece(Color.WHITE);
        new DisplayPiece().display(whiteMan);
        assertEquals("[w]", fakeStandardOutput.toString());
    }

    @Test
    void displayBlackKing(){
        System.setOut(new PrintStream(fakeStandardOutput));
        Piece blackKing = new Piece(Color.BLACK);
        blackKing.upgradeToKing();
        new DisplayPiece().display(blackKing);
        assertEquals("[B]", fakeStandardOutput.toString() );
    }

    @Test
    void displayWhiteKing(){
        System.setOut(new PrintStream(fakeStandardOutput));
        Piece whiteKing = new Piece(Color.WHITE);
        whiteKing.upgradeToKing();
        new DisplayPiece().display(whiteKing);
        assertEquals("[W]", fakeStandardOutput.toString());
    }
}
