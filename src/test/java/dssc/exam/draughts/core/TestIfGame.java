package dssc.exam.draughts.core;

import dssc.exam.draughts.exceptions.CannotMoveException;
import dssc.exam.draughts.utilities.Color;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIfGame {

    @Test
    void changesPlayer() {
        Player whitePlayer = new Player(dssc.exam.draughts.utilities.Color.WHITE);
        Player blackPlayer = new Player(dssc.exam.draughts.utilities.Color.BLACK);
        Game game = new Game(whitePlayer, blackPlayer);
        assertEquals(whitePlayer, game.getCurrentPlayer());
        game.changePlayer();
        assertEquals(blackPlayer, game.getCurrentPlayer());
        game.changePlayer();
        assertEquals(whitePlayer, game.getCurrentPlayer());
    }

    @Test
    void performAMoveAndUpdatesRoundNumber() throws CannotMoveException {
        List<Point> fakeInputList = Arrays.asList(
                new Point(2, 3),
                new Point(3, 4));

        Game game = new Game(
                new PlayerStub(dssc.exam.draughts.utilities.Color.WHITE, fakeInputList),
                new PlayerStub(dssc.exam.draughts.utilities.Color.BLACK, fakeInputList));

        Board board = new Board();
        game.loadGame(board, 0);

        assertTrue(board.getTile(2, 3).isNotEmpty());
        assertTrue(board.getTile(3, 4).isEmpty());
        assertEquals(dssc.exam.draughts.utilities.Color.WHITE, game.getCurrentPlayer().getColor());
        assertEquals(0, game.getRound());

        game.playRound();

        assertTrue(board.getTile(2, 3).isEmpty());
        assertTrue(board.getTile(3, 4).isNotEmpty());
        assertEquals(dssc.exam.draughts.utilities.Color.BLACK, game.getCurrentPlayer().getColor());
        assertEquals(1, game.getRound());
    }

    @Test
    void doesntAllowToStartMovingFromAnEmptyTile() throws CannotMoveException {
        List<Point> fakeInputList = Arrays.asList(
                new Point(0, 2),
                new Point(2, 1),
                new Point(3, 2));

        ByteArrayOutputStream fakeStandardOutput = getFakeStandardOutput();
        Board board = new Board();
        Game game = new Game(
                new PlayerStub(dssc.exam.draughts.utilities.Color.WHITE, fakeInputList),
                new PlayerStub(dssc.exam.draughts.utilities.Color.BLACK, fakeInputList));

        game.loadGame(board, 0);
        assertEquals(dssc.exam.draughts.utilities.Color.WHITE, game.getCurrentPlayer().getColor());
        game.playRound();
        String[] actualLines = fakeStandardOutput.toString().split(System.lineSeparator());

        String expectedOut = "Invalid move: The first Tile you selected is empty";
        assertEquals(expectedOut, actualLines[actualLines.length - 1]);
    }

    @Test
    void doesntAllowToMoveAnOpponentPiece() throws CannotMoveException {
        List<Point> fakeInputList = Arrays.asList(
                new Point(5, 0),
                new Point(2, 1),
                new Point(3, 2));
        ByteArrayOutputStream fakeStandardOutput = getFakeStandardOutput();

        Board board = new Board();
        Game game = new Game(
                new PlayerStub(dssc.exam.draughts.utilities.Color.WHITE, fakeInputList),
                new PlayerStub(dssc.exam.draughts.utilities.Color.BLACK, fakeInputList));

        game.loadGame(board, 0);
        assertEquals(dssc.exam.draughts.utilities.Color.WHITE, game.getCurrentPlayer().getColor());

        game.playRound();

        String[] actualLines = fakeStandardOutput
                .toString()
                .split(System.lineSeparator());

        String expectedOut = "Invalid move: The piece you intend to move belongs to your opponent";
        assertEquals(expectedOut, actualLines[actualLines.length - 1]);
    }

    @Test
    void endsWhenOnePlayerHasNoPiecesLeft() {
        CustomizableBoard board = new CustomizableBoard();

        board.popPiecesAt(Stream.iterate(1, n -> n + 1)
                .limit(board.getSize() / 2)
                .collect(Collectors.toList()));

        ByteArrayOutputStream fakeStandardOutput = getFakeStandardOutput();

        Player whitePlayer = new Player(dssc.exam.draughts.utilities.Color.WHITE);
        Player blackPlayer = new Player(dssc.exam.draughts.utilities.Color.BLACK);

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
        List<Point> fakeInputList = Arrays.asList(
                new Point(5, 0),
                new Point(3, 2),
                new Point(1, 4)
        );

        CustomizableBoard board = new CustomizableBoard()
                .popPiecesAt(Arrays.asList(12, 17, 33, 42, 44))
                .setMultipleManAt(Arrays.asList(28, 33), dssc.exam.draughts.utilities.Color.WHITE)
                .setMultipleManAt(Arrays.asList(24, 37), dssc.exam.draughts.utilities.Color.BLACK);

        Game game = new Game(
                new PlayerStub(dssc.exam.draughts.utilities.Color.WHITE, fakeInputList),
                new PlayerStub(dssc.exam.draughts.utilities.Color.BLACK, fakeInputList));

        game.loadGame(board, 1);
        ByteArrayOutputStream fakeStandardOutput = getFakeStandardOutput();
        game.playRound();

        String[] actualLines = fakeStandardOutput.toString()
                .split(System.lineSeparator());

        assertEquals("Player [BLACK]:", actualLines[10]);
        assertEquals("You can continue to skip!", actualLines[21]);
    }

    @Test
    void EndGameWhenAPlayerCannotMove() {
        CustomizableBoard board = new CustomizableBoard();

        board.popPiecesAt(Stream.iterate(1, n -> n + 1)
                .limit(board.getSize() - 1)
                .collect(Collectors.toList()));
        board.setManAtTile(0,1, dssc.exam.draughts.utilities.Color.WHITE);
        board.setManAtTile(1,0, dssc.exam.draughts.utilities.Color.BLACK);
        board.setKingAtTile(1,2, dssc.exam.draughts.utilities.Color.BLACK);

        ByteArrayOutputStream fakeStandardOutput = getFakeStandardOutput();

        Player whitePlayer = new Player(dssc.exam.draughts.utilities.Color.WHITE);
        Player blackPlayer = new Player(dssc.exam.draughts.utilities.Color.BLACK);

        whitePlayer.setName("Player 1");
        blackPlayer.setName("Player 2");

        Game game = new Game(whitePlayer, blackPlayer);
        game.loadGame(board,0);
        game.play();

        String[] actualLines = fakeStandardOutput.toString().split(System.lineSeparator());
        assertEquals("Cannot perform any move!", actualLines[11]);
        assertEquals("*******GAME OVER*******", actualLines[12]);
        assertEquals("The winner is Player 2", actualLines[13]);
    }

    private ByteArrayOutputStream getFakeStandardOutput() {
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        return fakeStandardOutput;
    }

    static class PlayerStub extends Player {

        private static int nextPointToReadIndex = 0;
        private final List<Point> fakeReadPoints;

        PlayerStub(Color color, List<Point> points) {
            super(color);
            fakeReadPoints = points;
            nextPointToReadIndex = 0;
        }

        @Override
        public Point readPosition(String message) {
            return fakeReadPoints.get(nextPointToReadIndex++);
        }
    }
}
