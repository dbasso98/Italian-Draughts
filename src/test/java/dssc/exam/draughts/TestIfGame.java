package dssc.exam.draughts;

import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIfGame {

    void setFakeStdInput(String fakeInput) {
        ByteArrayInputStream fakeStandardInput = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(fakeStandardInput);
    }

    @Test
    void testChangePlayer() {
        Player whitePlayer = new Player(Color.WHITE);
        Player blackPlayer = new Player(Color.BLACK);
        Game game = new Game(whitePlayer, blackPlayer);
//        assertEquals(whitePlayer, game.currentPlayer);
        assertEquals(whitePlayer, game.getCurrentPlayer());
        game.changePlayer();
        assertEquals(blackPlayer, game.getCurrentPlayer());
        game.changePlayer();
        assertEquals(whitePlayer, game.getCurrentPlayer());
    }

    @Test
    void testTurnBehaviour() {
        String fakeInput = "4 3" + System.lineSeparator() +
                "5 4" + System.lineSeparator();
        setFakeStdInput(fakeInput);

        Game game = new Game();
        Board board = new Board();
        game.loadGame(board, 0);

        assertTrue(board.getTile(2, 3).isNotEmpty());
        assertTrue(board.getTile(3, 4).isEmpty());
        assertEquals(Color.WHITE, game.getCurrentPlayer().getColor());
        assertEquals(0, game.getRound());

        game.playRound();

        assertTrue(board.getTile(2, 3).isEmpty());
        assertTrue(board.getTile(3, 4).isNotEmpty());
        assertEquals(Color.BLACK, game.getCurrentPlayer().getColor());
        assertEquals(1, game.getRound());
    }

    @Test
    void testInvalidEmptyTileInput() {
        String fakeInput = "1 3" + System.lineSeparator() +
                "5 4" + System.lineSeparator() +
                "2 3" + System.lineSeparator() +
                "3 4" + System.lineSeparator();
        setFakeStdInput(fakeInput);

        String expectedOut = "Invalid move: The first Tile you selected is empty";

        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        Board board = new Board();
        Game game = new Game();
        game.loadGame(board, 0);
        assertEquals(Color.WHITE, game.getCurrentPlayer().getColor());
        game.playRound();
        String actualOut = fakeStandardOutput.toString();
        String[] actualLines = actualOut.split(System.lineSeparator());

        assertEquals(expectedOut, actualLines[actualLines.length - 3]);
    }

    @Test
    void testInvalidPieceColorInput() {
        String fakeInput = "1 6" + System.lineSeparator() +
                "2 3" + System.lineSeparator() +
                "3 4" + System.lineSeparator();
        setFakeStdInput(fakeInput);

        String expectedOut = "Invalid move: The piece you intend to move belongs to your opponent";

        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        Board board = new Board();
        Game game = new Game();
        game.loadGame(board, 0);
        assertEquals(Color.WHITE, game.getCurrentPlayer().getColor());
        game.playRound();
        String actualOut = fakeStandardOutput.toString();
        String[] actualLines = actualOut.split(System.lineSeparator());

        assertEquals(expectedOut, actualLines[actualLines.length - 3]);
    }

    @Test
    void GameEndForEndOfPieces() {
        Board board = new Board();
        for (int row = 0; row < BoardSpecifications.numberOfRows() / 2; ++row) {
            for (int column = 0; column < BoardSpecifications.numberOfColumns(); ++column) {
                board.getTile(row, column).setPiece(null);
            }
        }

        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        Player whitePlayer = new Player(Color.WHITE);
        Player blackPlayer = new Player(Color.BLACK);

        whitePlayer.setName("Player 1");
        blackPlayer.setName("Player 2");

        Game game = new Game(whitePlayer, blackPlayer);
        game.loadGame(board, 0);
        game.play();
        String expected = "The winner is Player 2" + System.lineSeparator();
        assertEquals(expected, fakeStandardOutput.toString());
    }

    @Test
    void ValidDoubleSkipTest() throws Exception {

        String fakeInput = "1 6 3 4" + System.lineSeparator()
                + "5 2" + System.lineSeparator();
        setFakeStdInput(fakeInput);

        Board board = new Board();
        new Move(board, new Point(2, 3), new Point(3, 4)).moveDecider();
        new Move(board, new Point(5, 4), new Point(4, 5)).moveDecider();
        new Move(board, new Point(1, 4), new Point(2, 3)).moveDecider();
        new Move(board, new Point(5, 2), new Point(4, 1)).moveDecider();
        new Move(board, new Point(2, 1), new Point(3, 2)).moveDecider();
        new Move(board, new Point(4, 1), new Point(3, 0)).moveDecider();
        new Move(board, new Point(3, 2), new Point(4, 1)).moveDecider();

        Game game = new Game();
        game.loadGame(board, 1);

        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));

        game.playRound();

        String actualOut = fakeStandardOutput.toString();
        String[] actualLines = actualOut.split(System.lineSeparator());

        String expected11 = "What are the coordinates (x, y) of the piece you intend to move? (e.g. 3 4)";
        String expected23 = "You can continue to skip!";
        assertEquals("Player [BLACK]:", actualLines[10]);
        assertEquals(expected11, actualLines[11]);
        assertEquals(expected23, actualLines[23]);
    }


}
