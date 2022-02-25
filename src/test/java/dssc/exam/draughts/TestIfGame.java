package dssc.exam.draughts;

import dssc.exam.draughts.exceptions.CannotMoveException;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIfGame {

    void setFakeStdInput(String fakeInput) {
        ByteArrayInputStream fakeStandardInput = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(fakeStandardInput);
    }

    @Test
    void changesPlayer() {
        Player whitePlayer = new Player(Color.WHITE);
        Player blackPlayer = new Player(Color.BLACK);
        Game game = new Game(whitePlayer, blackPlayer);
        assertEquals(whitePlayer, game.getCurrentPlayer());
        game.changePlayer();
        assertEquals(blackPlayer, game.getCurrentPlayer());
        game.changePlayer();
        assertEquals(whitePlayer, game.getCurrentPlayer());
    }

    @Test
    void allowsToMakeAMoveAndUpdatesRoundNumber() throws CannotMoveException {
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
    void doesntAllowToStartMovingFromAnEmptyTile() throws CannotMoveException {
        String fakeInput = "1 3" + System.lineSeparator() +
                "5 4" + System.lineSeparator() +
                "2 3" + System.lineSeparator() +
                "3 4" + System.lineSeparator();
        setFakeStdInput(fakeInput);

        String expectedOut = "Invalid move: The first Tile you selected is empty";

        ByteArrayOutputStream fakeStandardOutput = getFakeStandardOutput();

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
    void doesntAllowToMoveAnOpponentPiece() throws CannotMoveException {
        String fakeInput = "1 6" + System.lineSeparator() +
                "2 3" + System.lineSeparator() +
                "3 4" + System.lineSeparator();
        setFakeStdInput(fakeInput);

        String expectedOut = "Invalid move: The piece you intend to move belongs to your opponent";

        ByteArrayOutputStream fakeStandardOutput = getFakeStandardOutput();

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
    void endsWhenOnePlayerHasNoPiecesLeft() {
        CustomizableBoard board = new CustomizableBoard();

        board.popPiecesAt(Stream.iterate(1, n -> n + 1)
                .limit(board.getSize() / 2)
                .collect(Collectors.toList()));

        ByteArrayOutputStream fakeStandardOutput = getFakeStandardOutput();

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
    void informsThePlayerThatCanContinueToSkip() throws Exception {
        List<Point> input = Arrays.asList(
                new Point(5, 0),
                new Point(3, 2),
                new Point(1, 4)
        );

        CustomizableBoard board = new CustomizableBoard()
                .popPiecesAt(Arrays.asList(12, 17, 33, 42, 44))
                .setMultipleManAt(Arrays.asList(28, 33), Color.WHITE)
                .setMultipleManAt(Arrays.asList(24, 37), Color.BLACK);

        Game game = new Game(
                new PlayerStub(Color.WHITE, input),
                new PlayerStub(Color.BLACK, input));

        game.loadGame(board, 1);
        ByteArrayOutputStream fakeStandardOutput = getFakeStandardOutput();
        game.playRound();

        String[] actualLines = fakeStandardOutput.toString()
                .split(System.lineSeparator());

        assertEquals("Player [BLACK]:", actualLines[10]);
        assertEquals("You can continue to skip!", actualLines[21]);
    }

    private ByteArrayOutputStream getFakeStandardOutput() {
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        return fakeStandardOutput;
    }

    @Test
    void HandlingCannotMoveException() {

    }

    class PlayerStub extends Player {
        private static int nextPointToReadIndex = 0;
        private final List<Point> fakeReadPoints;

        PlayerStub(Color color, List<Point> points) {
            super(color);
            fakeReadPoints = points;
        }

        @Override
        Point readPosition(String message) {
            return fakeReadPoints.get(nextPointToReadIndex++);
        }

    }
}
