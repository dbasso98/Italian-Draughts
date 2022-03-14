package dssc.exam.draughts.display;

import dssc.exam.draughts.Color;
import dssc.exam.draughts.Piece;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfDisplayPiece {

    @ParameterizedTest
    @CsvSource({"BLACK, [b]", "WHITE, [w]"})
    void printsManToStdOutput(Color colorOfPiece, String representationOfPiece) {
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        var fakePiece = new Piece(colorOfPiece);

        new DisplayPiece().display(fakePiece);
        assertEquals(representationOfPiece, fakeStandardOutput.toString());
    }
    @ParameterizedTest
    @CsvSource({"BLACK, [B]", "WHITE, [W]"})
    void printsKingToStdOutput(Color colorOfPiece, String representationOfPiece) {
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        var fakePiece = new Piece(colorOfPiece);
        fakePiece.upgradeToKing();

        new DisplayPiece().display(fakePiece);
        assertEquals(representationOfPiece, fakeStandardOutput.toString());
    }

}
