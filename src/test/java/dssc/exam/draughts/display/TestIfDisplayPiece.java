package dssc.exam.draughts.display;

import dssc.exam.draughts.Color;
import dssc.exam.draughts.Piece;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfDisplayPiece {

    private ByteArrayOutputStream changeStdOutputToFakeOutput() {
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        return fakeStandardOutput;
    }

    @ParameterizedTest
    @CsvSource({"BLACK, [b]", "WHITE, [w]"})
    void printsManToStdOutput(Color colorOfPiece, String representationOfPiece) {
        var fakeStandardOutput = changeStdOutputToFakeOutput();

        new DisplayPiece().display(new Piece(colorOfPiece));
        assertEquals(representationOfPiece, fakeStandardOutput.toString());
    }

    @ParameterizedTest
    @CsvSource({"BLACK, [B]", "WHITE, [W]"})
    void printsKingToStdOutput(Color colorOfPiece, String representationOfPiece) {
        var fakeStandardOutput = changeStdOutputToFakeOutput();

        var fakePiece = new Piece(colorOfPiece);
        fakePiece.upgradeToKing();

        new DisplayPiece().display(fakePiece);
        assertEquals(representationOfPiece, fakeStandardOutput.toString());
    }

}
