package dssc.exam.draughts;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestIfGame {

    @Test
    void testChangePlayer() {
        Game game = new Game();
        assertEquals(game.whitePlayer, game.currentPlayer);
        game.changePlayer();
        assertEquals(game.blackPlayer, game.currentPlayer);
        game.changePlayer();
        assertEquals(game.whitePlayer, game.currentPlayer);
    }
}
