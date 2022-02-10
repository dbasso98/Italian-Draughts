package dssc.exam.draughts;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class TestIfBoard {
    Board board = new Board();

    @Test
    void hasSizeOf64Tiles() {
        assertEquals(64, board.getSizeOfBoard());
    }

    @Test
    void has24Pieces() {
        assertEquals(24, board.getTotalNumberOfPieces());
    }

    @ParameterizedTest
    @CsvSource({"BLACK, 12", "WHITE, 12"})
    void has12PiecesOfColor(Color color, int number) {
        assertEquals(number, board.getPiecesOfColor(color));
    }

    @ParameterizedTest
    @CsvSource({"WHITE, 17", "WHITE, 19", "WHITE, 21", "WHITE, 23",
            "WHITE, 8", "WHITE, 10", "WHITE, 12", "WHITE, 14",
            "WHITE, 1", "WHITE, 3", "WHITE, 5", "WHITE, 7"})
    void has12WhitePiecesInFirstThreeRows(Color color, int position) {
        assertEquals(board.getTile(position).getTilePiece().getColor(), color);
        assertEquals(board.getPieceAtTile(position).getColor(), color);
    }

    @ParameterizedTest
    @CsvSource({"BLACK, 56", "BLACK, 58", "BLACK, 60", "BLACK, 62",
            "BLACK, 49", "BLACK, 51", "BLACK, 53", "BLACK, 55",
            "BLACK, 40", "BLACK, 42", "BLACK, 44", "BLACK, 46"})
    void has12BlackPiecesInLastThreeRows(Color color, int position) {
        assertEquals(board.getPieceAtTile(position).getColor(), color);
    }

    @ParameterizedTest
    @ValueSource(ints = {24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39})
    void hasEmptyTilesInTwoMiddleRows(int position) {
        assertTrue(board.getTile(position).isTileEmpty());
    }

    @ParameterizedTest
    @ValueSource(ints = {24, 25, 26, 27, 28, 29, 30, 31})
    void isColorSymmetricInTwoMiddleRows(int position) {
        assertSame(board.getTile(position).getTileColor(), board.getSymmetricTile(position).getTileColor());
    }

    @ParameterizedTest
    @CsvSource({"0, 18, 9", "2, 16, 9",
            "57, 43, 50", "52, 38, 45",
            "50, 0, -1", "64, 46, -1"})
    void isMiddlePositionCorrect(int startPosition, int endPosition, int middlePosition) {
        assertEquals(board.getMiddlePosition(startPosition, endPosition), middlePosition);
    }

    @ParameterizedTest
    @CsvSource({"0, 0, 2, 2, 9", "0, 6, 2, 4, 13",
            "7, 1, 5, 3, 50", "7, 7, 5, 5, 54",
            "-1, 63, 1, 5, -1", "64, 30, 5, 8, -1"})
    void isMiddlePositionCorrect(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn, int middlePosition) {
        var sourcePoint = new Point(sourceRow, sourceColumn);
        var destinationPoint = new Point(destinationRow, destinationColumn);
        assertEquals(board.getMiddlePosition(sourcePoint, destinationPoint), middlePosition);
    }

    @Test
    void printBoard() {
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
        Board board = new Board();
        board.getPieceAtTile(1, 0).upgradePieceToKing();
        board.getPieceAtTile(7, 4).upgradePieceToKing();

        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        board.display();
        assertEquals(expected, fakeStandardOutput.toString());
    }
}