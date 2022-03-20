package dssc.exam.draughts.core;

import dssc.exam.draughts.IOInterfaces.ScannerPlayerInput;
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
        Player whitePlayer = new Player(Color.WHITE);
        Player blackPlayer = new Player(Color.BLACK);
        Game game = new Game(whitePlayer, blackPlayer);
        assertEquals(whitePlayer, game.getCurrentPlayer());
        game.changePlayer();
        assertEquals(blackPlayer, game.getCurrentPlayer());
    }

    @Test
    void performsAMove() throws CannotMoveException {
        Game game = instantiateGameWithFakeInput(Arrays.asList(new Point(2, 3),
                                                               new Point(3, 4)));
        Board board = new Board();
        game.loadGame(board, 0);
        assertTrue(board.getTile(2, 3).isNotEmpty());
        assertTrue(board.getTile(3, 4).isEmpty());
        game.playRound();
        assertTrue(board.getTile(2, 3).isEmpty());
        assertTrue(board.getTile(3, 4).isNotEmpty());
    }

    @Test
    void updatesRoundNumber() throws CannotMoveException {
        Game game = instantiateGameWithFakeInput(Arrays.asList(new Point(2, 3),
                                                               new Point(3, 4)));
        game.loadGame(new Board(), 0);
        assertEquals(0, game.getRound());
        game.playRound();
        assertEquals(1, game.getRound());
    }

    @Test
    void doesntAllowToStartMovingFromAnEmptyTile() throws CannotMoveException {
        Game game = instantiateGameWithFakeInput(Arrays.asList(new Point(0, 2),
                                                               new Point(2, 1),
                                                               new Point(3, 2)));
        game.loadGame(new Board(), 0);
        ByteArrayOutputStream fakeStandardOutput = changeStdOutputToFakeOutput();
        game.playRound();
        String[] actualLines = fakeStandardOutput.toString().split(System.lineSeparator());
        String expectedOut = "Invalid move: The first Tile you selected is empty";
        assertEquals(expectedOut, actualLines[actualLines.length - 1]);
    }

    @Test
    void doesntAllowToMoveAnOpponentPiece() throws CannotMoveException {
        Game game = instantiateGameWithFakeInput(Arrays.asList(new Point(5, 0),
                                                               new Point(2, 1),
                                                               new Point(3, 2)));
        game.loadGame(new Board(), 0);
        ByteArrayOutputStream fakeStandardOutput = changeStdOutputToFakeOutput();
        game.playRound();
        String[] actualLines = fakeStandardOutput.toString().split(System.lineSeparator());
        String expectedOut = "Invalid move: The piece you intend to move belongs to your opponent";
        assertEquals(expectedOut, actualLines[actualLines.length - 1]);
    }

    @Test
    void endsWhenOnePlayerHasNoPiecesLeft() {
        CustomizableBoard board = new CustomizableBoard();
        removeAllWhitePiecesFromBoard(board);
        ByteArrayOutputStream fakeStandardOutput = changeStdOutputToFakeOutput();
        Player whitePlayer = new Player(Color.WHITE);
        whitePlayer.setName("Player 1");
        Player blackPlayer = new Player(Color.BLACK);
        blackPlayer.setName("Player 2");
        Game game = new Game(whitePlayer, blackPlayer);
        game.loadGame(board, 0);
        game.play();
        String expected = "The winner is Player 2" + System.lineSeparator();
        assertEquals(expected, fakeStandardOutput.toString());
    }

    private void removeAllWhitePiecesFromBoard(CustomizableBoard board) {
        board.popPiecesAt(Stream.iterate(1, n -> n + 1)
                                .limit(board.getSize() / 2)
                                .collect(Collectors.toList()));
    }

    @Test
    void informsThePlayerThatCanContinueToSkip() throws Exception {
        CustomizableBoard board = new CustomizableBoard()
                .popPiecesAt(Arrays.asList(12, 17, 33, 42, 44))
                .setMultipleManAt(Arrays.asList(28, 33), Color.WHITE)
                .setMultipleManAt(Arrays.asList(24, 37), Color.BLACK);
        Game game = instantiateGameWithFakeInput(Arrays.asList(new Point(5, 0),
                                                               new Point(3, 2),
                                                               new Point(1, 4)));
        game.loadGame(board, 1);
        ByteArrayOutputStream fakeStandardOutput = changeStdOutputToFakeOutput();
        game.playRound();
        String[] actualLines = fakeStandardOutput.toString().split(System.lineSeparator());
        assertEquals("Player [BLACK]:", actualLines[10]);
        assertEquals("You can continue to skip!", actualLines[21]);
    }

    @Test
    void EndGameWhenAPlayerCannotMove() {
        CustomizableBoard board = setBoardSoThatPlayerCannotMove();
        Player whitePlayer = new Player(Color.WHITE);
        whitePlayer.setName("Player 1");
        Player blackPlayer = new Player(Color.BLACK);
        blackPlayer.setName("Player 2");
        ByteArrayOutputStream fakeStandardOutput = changeStdOutputToFakeOutput();
        Game game = new Game(whitePlayer, blackPlayer);
        game.loadGame(board,0);
        game.play();
        String[] actualLines = fakeStandardOutput.toString().split(System.lineSeparator());
        assertEquals("Cannot perform any move!", actualLines[11]);
        assertEquals("*******GAME OVER*******", actualLines[12]);
        assertEquals("The winner is Player 2", actualLines[13]);
    }

    private CustomizableBoard setBoardSoThatPlayerCannotMove() {
        CustomizableBoard board = new CustomizableBoard();
        board.removeAllPieces();
        board.setManAtTile(0,1, Color.WHITE);
        board.setManAtTile(1,0, Color.BLACK);
        board.setKingAtTile(1,2, Color.BLACK);
        return board;
    }

    private ByteArrayOutputStream changeStdOutputToFakeOutput() {
        ByteArrayOutputStream fakeStandardOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(fakeStandardOutput));
        return fakeStandardOutput;
    }

    private Game instantiateGameWithFakeInput(List<Point> fakeInputList) {
        Game game = new Game(new FakeScannerPlayerInput(fakeInputList),
                             new PlayerStub(Color.WHITE, fakeInputList),
                             new PlayerStub(Color.BLACK, fakeInputList));
        return game;
    }

    static class PlayerStub extends Player {
        private static int nextPointToReadIndex = 0;
        private final List<Point> fakeReadPoints;

        PlayerStub(Color color, List<Point> points) {
            super(color);
            fakeReadPoints = points;
            nextPointToReadIndex = 0;
        }
    }

    private class FakeScannerPlayerInput extends ScannerPlayerInput {
        private static int nextPointToReadIndex = 0;
        private final List<Point> fakeReadPoints;

        FakeScannerPlayerInput(List<Point> points) {
            fakeReadPoints = points;
            nextPointToReadIndex = 0;
        }

        @Override
        public Point readPosition(String message) {
            return fakeReadPoints.get(nextPointToReadIndex++);
        }
    }
}
