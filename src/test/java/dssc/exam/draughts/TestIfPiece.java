package dssc.exam.draughts;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfPiece {
    private static final String piece =  "Piece{" +
            "id=" + 1 +
            ", color=" + "BLACK" +
            ", isKing=" + false +
            "}" + System.lineSeparator();

    @Test
    void printsToStdOutput() {
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        var Piece = new Piece(1, Color.BLACK);
        Piece.printPieceInfo();

        assertEquals(piece, fakeStandardOutput.toString());
    }

    @Test
    void displayBlackMan(){
        Piece blackMan = new Piece(1, Color.BLACK);
        assertEquals("[b]", blackMan.display());
    }
}
