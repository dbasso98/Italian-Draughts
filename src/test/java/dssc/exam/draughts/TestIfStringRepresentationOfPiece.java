package dssc.exam.draughts;

import dssc.exam.draughts.core.Piece;
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
        blackMan.display();
        assertEquals("[b]", fakeStandardOutput.toString());
    }

    @Test
    void displayWhiteMan(){
        System.setOut(new PrintStream(fakeStandardOutput));
        Piece whiteMan = new Piece(Color.WHITE);
        whiteMan.display();
        assertEquals("[w]", fakeStandardOutput.toString());
    }

    @Test
    void displayBlackKing(){
        System.setOut(new PrintStream(fakeStandardOutput));
        Piece blackKing = new Piece(Color.BLACK);
        blackKing.upgradeToKing();
        blackKing.display();
        assertEquals("[B]", fakeStandardOutput.toString() );
    }

    @Test
    void displayWhiteKing(){
        System.setOut(new PrintStream(fakeStandardOutput));
        Piece whiteKing = new Piece(Color.WHITE);
        whiteKing.upgradeToKing();
        whiteKing.display();
        assertEquals("[W]", fakeStandardOutput.toString());
    }
}
