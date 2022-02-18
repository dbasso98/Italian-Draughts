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
        var fakePiece = new Piece(1, Color.BLACK);
        System.out.println(fakePiece);

        assertEquals(TestIfPiece.piece, fakeStandardOutput.toString());
    }

    @Test
    void displayBlackMan(){
        Piece blackMan = new Piece(1, Color.BLACK);
        assertEquals("[b]", blackMan.display());
    }

    @Test
    void displayWhiteMan(){
        Piece whiteMan = new Piece(1, Color.WHITE);
        assertEquals("[w]", whiteMan.display());
    }

    @Test
    void displayBlackKing(){
        Piece blackKing = new Piece(1, Color.BLACK);
        blackKing.upgradeToKing();
        assertEquals("[B]", blackKing.display() );
    }

    @Test
    void displayWhiteKing(){
        Piece whiteKing = new Piece(1, Color.WHITE);
        whiteKing.upgradeToKing();
        assertEquals("[W]", whiteKing.display() );
    }
}
