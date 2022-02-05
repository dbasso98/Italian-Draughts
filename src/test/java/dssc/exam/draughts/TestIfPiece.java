package dssc.exam.draughts;

import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfPiece {
    private static final String piece =  "Piece{" +
            "id=" + 1 +
            ", color=" + "BLACK" +
            ", is_king=" + false +
            "}" + System.lineSeparator();

    @Test
    void prints_to_std_output() {
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        var Piece = new Piece(1, Color.BLACK);
        Piece.print_piece_info();

        assertEquals(piece, fakeStandardOutput.toString());
    }
}
