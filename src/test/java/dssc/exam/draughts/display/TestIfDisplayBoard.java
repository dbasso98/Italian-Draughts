package dssc.exam.draughts.display;

import dssc.exam.draughts.core.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfDisplayBoard {
    Board board = new Board();


    @BeforeEach
    private void makeBoardWithWhiteKingAndBlackKing() {
        board.getPieceAtTile(1, 0).upgradeToKing();
        board.getPieceAtTile(7, 4).upgradeToKing();
    }

    @Test
    void printsBoardCorrectly() {
        String expected = "   1  2  3  4  5  6  7  8" + System.lineSeparator() +
                "8 [b][ ][b][ ][B][ ][b][ ] 8" + System.lineSeparator() +
                "7 [ ][b][ ][b][ ][b][ ][b] 7" + System.lineSeparator() +
                "6 [b][ ][b][ ][b][ ][b][ ] 6" + System.lineSeparator() +
                "5 [ ][ ][ ][ ][ ][ ][ ][ ] 5" + System.lineSeparator() +
                "4 [ ][ ][ ][ ][ ][ ][ ][ ] 4" + System.lineSeparator() +
                "3 [ ][w][ ][w][ ][w][ ][w] 3" + System.lineSeparator() +
                "2 [W][ ][w][ ][w][ ][w][ ] 2" + System.lineSeparator() +
                "1 [ ][w][ ][w][ ][w][ ][w] 1" + System.lineSeparator() +
                "   1  2  3  4  5  6  7  8" + System.lineSeparator();

        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        new DisplayBoard().display(board);
        assertEquals(expected, fakeStandardOutput.toString());
    }
}
