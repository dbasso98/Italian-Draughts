package dssc.exam.draughts;

import dssc.exam.draughts.core.Game;
import dssc.exam.draughts.exceptions.DraughtsException;

public class Main {
    public static void main(String... args) throws DraughtsException {
        System.out.println("ciao!");
        Game game = new Game();
        game.startGame();
    }
}
