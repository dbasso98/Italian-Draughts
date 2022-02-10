package dssc.exam.draughts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIfGame {

    void setFakeStdInput(String fakeInput) {
        ByteArrayInputStream fakeStandardInput = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(fakeStandardInput);
    }

    @Test
    void testChangePlayer() {
        Game game = new Game();
        assertEquals(game.whitePlayer, game.currentPlayer);
        game.changePlayer();
        assertEquals(game.blackPlayer, game.currentPlayer);
        game.changePlayer();
        assertEquals(game.whitePlayer, game.currentPlayer);
    }

    @Test
    void testTurnBehaviour() {
        String move = "4 3" + System.lineSeparator() + "5 4" + System.lineSeparator();

        String fakeInput = move;
        setFakeStdInput(fakeInput);

        Game game = new Game();
        Board board = new Board();
        game.loadGame(board, 0);

        assertTrue(board.getTile(2, 3).isTileNotEmpty());
        assertTrue(board.getTile(3, 4).isTileEmpty());
        assertEquals(Color.WHITE, game.getCurrentPlayer().getColor());
        assertEquals(0, game.getRound());

        game.playRound();

        assertTrue(board.getTile(2, 3).isTileEmpty());
        assertTrue(board.getTile(3, 4).isTileNotEmpty());
        assertEquals(Color.BLACK, game.getCurrentPlayer().getColor());
        assertEquals(1, game.getRound());
    }
}
