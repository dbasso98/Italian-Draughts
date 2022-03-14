package dssc.exam.draughts.display;

import dssc.exam.draughts.Color;
import dssc.exam.draughts.Piece;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfDisplayPiece {
    private static final String piece =  "[b]";

    @Test
    void printsToStdOutput() {
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        var fakePiece = new Piece(Color.BLACK);
        fakePiece.display();

        assertEquals(TestIfDisplayPiece.piece, fakeStandardOutput.toString());
    }
}
